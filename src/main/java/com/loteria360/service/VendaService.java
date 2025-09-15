package com.loteria360.service;

import com.loteria360.domain.dto.*;
import com.loteria360.domain.model.*;
import com.loteria360.mapper.*;
import com.loteria360.repository.*;
import com.loteria360.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class VendaService {

    private final VendaRepository vendaRepository;
    private final BolaoRepository bolaoRepository;
    private final JogoRepository jogoRepository;
    private final ClienteRepository clienteRepository;
    private final TurnoRepository turnoRepository;
    private final VendaMapper vendaMapper;
    private final ClienteMapper clienteMapper;
    private final PagamentoMapper pagamentoMapper;

    public VendaResponse criarVendaJogo(VendaJogoRequest request, Usuario usuario) {
        log.info("Criando venda de jogo: {} - quantidade: {}", request.getJogoId(), request.getQuantidade());

        Turno turnoAtivo = turnoRepository.findByUsuarioIdAndStatus(usuario.getId(), StatusTurno.ABERTO)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não possui turno ativo"));

        Jogo jogo = jogoRepository.findById(request.getJogoId())
                .orElseThrow(() -> new IllegalArgumentException("Jogo não encontrado"));

        if (!jogo.getAtivo()) {
            throw new IllegalArgumentException("Jogo não está ativo");
        }

        BigDecimal valorBruto = jogo.getPrecoBase().multiply(BigDecimal.valueOf(request.getQuantidade()));
        BigDecimal desconto = request.getDesconto() != null ? request.getDesconto() : BigDecimal.ZERO;
        BigDecimal acrescimo = request.getAcrescimo() != null ? request.getAcrescimo() : BigDecimal.ZERO;
        BigDecimal valorLiquido = valorBruto.subtract(desconto).add(acrescimo);

        validarPagamentos(request.getPagamentos(), valorLiquido);

        Venda venda = vendaMapper.toEntity(request);
        venda.setId(UUID.randomUUID().toString());
        venda.setTurno(turnoAtivo);
        venda.setValorTotal(valorLiquido);

        Cliente cliente = null;
        if (request.getCliente() != null) {
            cliente = clienteMapper.toEntity(request.getCliente());
            cliente.setId(UUID.randomUUID().toString());
            cliente = clienteRepository.save(cliente);
        }

        List<Pagamento> pagamentos = request.getPagamentos().stream()
                .map(pagamentoMapper::toEntity)
                .peek(p -> {
                    p.setId(UUID.randomUUID().toString());
                    p.setVenda(venda);
                })
                .collect(Collectors.toList());

        venda.setPagamentos(pagamentos);

        Venda vendaSalva = vendaRepository.save(venda);
        log.info("Venda de jogo criada com sucesso: {}", vendaSalva.getId());

        return vendaMapper.toResponse(vendaSalva);
    }

    public VendaResponse criarVendaBolao(VendaBolaoRequest request, Usuario usuario) {
        log.info("Criando venda de bolão: {} - cotas: {}", request.getBolaoId(), request.getCotas());

        Turno turnoAtivo = turnoRepository.findByUsuarioIdAndStatus(usuario.getId(), StatusTurno.ABERTO)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não possui turno ativo"));

        Bolao bolao = bolaoRepository.findByIdWithLock(request.getBolaoId())
                .orElseThrow(() -> new IllegalArgumentException("Bolão não encontrado"));

        if (bolao.getStatus() != StatusBolao.ABERTO) {
            throw new IllegalArgumentException("Bolão não está aberto para vendas");
        }

        if (!bolao.temCotasDisponiveis(request.getCotas())) {
            throw new IllegalArgumentException("Não há cotas suficientes disponíveis");
        }

        BigDecimal valorBruto = bolao.getValorCota().multiply(BigDecimal.valueOf(request.getCotas()));
        BigDecimal desconto = request.getDesconto() != null ? request.getDesconto() : BigDecimal.ZERO;
        BigDecimal acrescimo = request.getAcrescimo() != null ? request.getAcrescimo() : BigDecimal.ZERO;
        BigDecimal valorLiquido = valorBruto.subtract(desconto).add(acrescimo);

        validarPagamentos(request.getPagamentos(), valorLiquido);

        Venda venda = vendaMapper.toEntity(request);
        venda.setId(UUID.randomUUID().toString());
        venda.setTurno(turnoAtivo);
        venda.setValorTotal(valorLiquido);

        Cliente cliente = null;
        if (request.getCliente() != null) {
            cliente = clienteMapper.toEntity(request.getCliente());
            cliente.setId(UUID.randomUUID().toString());
            cliente = clienteRepository.save(cliente);
        }

        List<Pagamento> pagamentos = request.getPagamentos().stream()
                .map(pagamentoMapper::toEntity)
                .peek(p -> {
                    p.setId(UUID.randomUUID().toString());
                    p.setVenda(venda);
                })
                .collect(Collectors.toList());

        venda.setPagamentos(pagamentos);

        // Atualizar cotas vendidas do bolão
        bolao.incrementarCotasVendidas(request.getCotas());
        bolaoRepository.save(bolao);

        Venda vendaSalva = vendaRepository.save(venda);
        log.info("Venda de bolão criada com sucesso: {}", vendaSalva.getId());

        return vendaMapper.toResponse(vendaSalva);
    }

    public VendaResponse cancelarVenda(String id, CancelarVendaRequest request, Usuario usuario) {
        log.info("Cancelando venda: {} - motivo: {}", id, request.getMotivoCancelamento());

        Venda venda = vendaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Venda não encontrada"));

        if (venda.isCancelada()) {
            throw new IllegalArgumentException("Venda já está cancelada");
        }

        if (usuario.getPapel() != PapelUsuario.GERENTE && usuario.getPapel() != PapelUsuario.ADMIN) {
            throw new IllegalArgumentException("Apenas gerentes podem cancelar vendas");
        }

        venda.cancelar(request.getMotivoCancelamento());

        // Se for venda de bolão, devolver as cotas
        if (venda.getTipoVenda() == TipoVenda.BOLAO && venda.getBolao() != null) {
            Bolao bolao = bolaoRepository.findByIdWithLock(venda.getBolao().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Bolão não encontrado"));
            bolao.decrementarCotasVendidas(venda.getCotasCompradas());
            bolaoRepository.save(bolao);
        }

        Venda vendaSalva = vendaRepository.save(venda);
        log.info("Venda {} cancelada com sucesso", id);

        return vendaMapper.toResponse(vendaSalva);
    }

    @Transactional(readOnly = true)
    public Page<VendaResponse> listarVendas(Pageable pageable) {
        log.info("Listando vendas");
        return vendaRepository.findAll(pageable)
                .map(vendaMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public VendaResponse buscarPorId(String id) {
        log.info("Buscando venda por ID: {}", id);
        Venda venda = vendaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Venda não encontrada"));
        return vendaMapper.toResponse(venda);
    }

    private void validarPagamentos(List<PagamentoRequest> pagamentos, BigDecimal valorLiquido) {
        BigDecimal totalPagamentos = pagamentos.stream()
                .map(PagamentoRequest::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (totalPagamentos.compareTo(valorLiquido) != 0) {
            throw new IllegalArgumentException("Soma dos pagamentos deve ser igual ao valor líquido da venda");
        }
    }
}
