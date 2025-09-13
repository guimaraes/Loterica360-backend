package com.loteria360.service;

import com.loteria360.domain.dto.PagamentoRequest;
import com.loteria360.domain.dto.VendaRequest;
import com.loteria360.domain.dto.VendaResponse;
import com.loteria360.domain.dto.PagamentoResponse;
import com.loteria360.domain.model.*;
import com.loteria360.repository.BolaoRepository;
import com.loteria360.repository.JogoRepository;
import com.loteria360.repository.TurnoRepository;
import com.loteria360.repository.VendaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VendaService {
    
    private final VendaRepository vendaRepository;
    private final TurnoRepository turnoRepository;
    private final JogoRepository jogoRepository;
    private final BolaoRepository bolaoRepository;
    
    @Transactional
    public VendaResponse criarVenda(VendaRequest request, Usuario usuario) {
        // Validar turno aberto
        Turno turno = turnoRepository.findTurnoAbertoByUsuario(usuario.getId())
                .orElseThrow(() -> new IllegalStateException("Usuário não possui turno aberto"));
        
        Venda venda = new Venda();
        venda.setTurno(turno);
        venda.setUsuario(usuario);
        
        // Processar venda de jogo
        if (request.jogoId() != null) {
            processarVendaJogo(venda, request);
        }
        
        // Processar venda de bolão
        if (request.bolaoId() != null) {
            processarVendaBolao(venda, request);
        }
        
        // Validar e processar pagamentos
        processarPagamentos(venda, request);
        
        Venda vendaSalva = vendaRepository.save(venda);
        return toResponse(vendaSalva);
    }
    
    private void processarVendaJogo(Venda venda, VendaRequest request) {
        Jogo jogo = jogoRepository.findById(request.jogoId())
                .orElseThrow(() -> new IllegalArgumentException("Jogo não encontrado"));
        
        if (!jogo.getAtivo()) {
            throw new IllegalStateException("Jogo não está ativo");
        }
        
        venda.setJogo(jogo);
        venda.setTipo(Venda.Tipo.JOGO);
        venda.setQuantidadeApostas(request.quantidade());
        
        BigDecimal valorBruto = jogo.getPrecoBase().multiply(BigDecimal.valueOf(request.quantidade()));
        venda.setValorBruto(valorBruto);
        venda.setValorLiquido(valorBruto);
    }
    
    private void processarVendaBolao(Venda venda, VendaRequest request) {
        Bolao bolao = bolaoRepository.findByIdWithLock(request.bolaoId())
                .orElseThrow(() -> new IllegalArgumentException("Bolão não encontrado"));
        
        if (bolao.getStatus() != Bolao.Status.ABERTO) {
            throw new IllegalStateException("Bolão não está aberto para vendas");
        }
        
        if (!bolao.temCotasDisponiveis()) {
            throw new IllegalStateException("Bolão não possui cotas disponíveis");
        }
        
        if (request.cotas() > bolao.getCotasDisponiveis()) {
            throw new IllegalStateException("Quantidade de cotas solicitada excede as disponíveis");
        }
        
        venda.setBolao(bolao);
        venda.setTipo(Venda.Tipo.BOLAO);
        venda.setCotasVendidas(request.cotas());
        
        BigDecimal valorBruto = bolao.getValorCota().multiply(BigDecimal.valueOf(request.cotas()));
        venda.setValorBruto(valorBruto);
        venda.setValorLiquido(valorBruto);
        
        // Atualizar cotas vendidas do bolão
        bolao.setCotasVendidas(bolao.getCotasVendidas() + request.cotas());
        bolaoRepository.save(bolao);
    }
    
    private void processarPagamentos(Venda venda, VendaRequest request) {
        BigDecimal totalPagamentos = request.pagamentos().stream()
                .map(PagamentoRequest::valor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        if (totalPagamentos.compareTo(venda.getValorLiquido()) != 0) {
            throw new IllegalArgumentException("Soma dos pagamentos deve ser igual ao valor líquido da venda");
        }
        
        for (PagamentoRequest pagamentoRequest : request.pagamentos()) {
            Pagamento pagamento = Pagamento.builder()
                    .metodo(pagamentoRequest.metodo())
                    .valor(pagamentoRequest.valor())
                    .nsu(pagamentoRequest.nsu())
                    .tid(pagamentoRequest.tid())
                    .referencia(pagamentoRequest.referencia())
                    .status(Pagamento.Status.APROVADO)
                    .build();
            venda.adicionarPagamento(pagamento);
        }
    }
    
    @Transactional
    public VendaResponse cancelarVenda(UUID vendaId, String motivo) {
        Venda venda = vendaRepository.findById(vendaId)
                .orElseThrow(() -> new IllegalArgumentException("Venda não encontrada"));
        
        if (venda.getStatus() == Venda.Status.CANCELADA) {
            throw new IllegalStateException("Venda já está cancelada");
        }
        
        venda.setStatus(Venda.Status.CANCELADA);
        venda.setMotivoCancelamento(motivo);
        
        // Se for venda de bolão, devolver as cotas
        if (venda.getTipo() == Venda.Tipo.BOLAO && venda.getBolao() != null) {
            Bolao bolao = venda.getBolao();
            bolao.setCotasVendidas(bolao.getCotasVendidas() - venda.getCotasVendidas());
            bolaoRepository.save(bolao);
        }
        
        Venda vendaSalva = vendaRepository.save(venda);
        return toResponse(vendaSalva);
    }
    
    private VendaResponse toResponse(Venda venda) {
        List<PagamentoResponse> pagamentos = venda.getPagamentos().stream()
                .map(p -> new PagamentoResponse(
                        p.getId(),
                        p.getMetodo(),
                        p.getValor(),
                        p.getNsu(),
                        p.getTid(),
                        p.getReferencia(),
                        p.getStatus()
                ))
                .collect(Collectors.toList());
        
        return new VendaResponse(
                venda.getId(),
                venda.getTurno().getId(),
                venda.getUsuario().getId(),
                venda.getTipo(),
                venda.getJogo() != null ? venda.getJogo().getId() : null,
                venda.getBolao() != null ? venda.getBolao().getId() : null,
                venda.getQuantidadeApostas(),
                venda.getCotasVendidas(),
                venda.getValorBruto(),
                venda.getDesconto(),
                venda.getAcrescimo(),
                venda.getValorLiquido(),
                venda.getStatus(),
                venda.getMotivoCancelamento(),
                pagamentos,
                venda.getCriadoEm()
        );
    }
}
