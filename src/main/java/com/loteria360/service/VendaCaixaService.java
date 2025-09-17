package com.loteria360.service;

import com.loteria360.domain.dto.VendaCaixaRequest;
import com.loteria360.domain.dto.VendaCaixaResponse;
import com.loteria360.domain.model.Caixa;
import com.loteria360.domain.model.Jogo;
import com.loteria360.domain.model.Usuario;
import com.loteria360.domain.model.VendaCaixa;
import com.loteria360.mapper.VendaCaixaMapper;
import com.loteria360.repository.CaixaRepository;
import com.loteria360.repository.JogoRepository;
import com.loteria360.repository.UsuarioRepository;
import com.loteria360.repository.VendaCaixaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class VendaCaixaService {

    private final VendaCaixaRepository vendaCaixaRepository;
    private final CaixaRepository caixaRepository;
    private final JogoRepository jogoRepository;
    private final UsuarioRepository usuarioRepository;
    private final VendaCaixaMapper vendaCaixaMapper;

    public VendaCaixaResponse registrarVenda(VendaCaixaRequest request, Usuario usuario) {
        log.info("Registrando venda para caixa: {} e jogo: {}", request.getCaixaId(), request.getJogoId());

        Caixa caixa = caixaRepository.findById(request.getCaixaId())
                .orElseThrow(() -> new IllegalArgumentException("Caixa não encontrada"));

        Jogo jogo = jogoRepository.findById(request.getJogoId())
                .orElseThrow(() -> new IllegalArgumentException("Jogo não encontrado"));

        LocalDate dataVenda = LocalDate.parse(request.getDataVenda());

        // Verificar se já existe uma venda para esta caixa, jogo e data
        VendaCaixa vendaExistente = vendaCaixaRepository
                .findByCaixaIdAndJogoIdAndDataVenda(request.getCaixaId(), request.getJogoId(), dataVenda)
                .orElse(null);

        VendaCaixa vendaCaixa;
        if (vendaExistente != null) {
            // Atualizar venda existente
            vendaCaixa = vendaExistente;
            vendaCaixa.setQuantidade(request.getQuantidade());
            vendaCaixa.setUsuario(usuario);
            log.info("Atualizando venda existente: {}", vendaCaixa.getId());
        } else {
            // Criar nova venda
            vendaCaixa = VendaCaixa.builder()
                    .id(UUID.randomUUID().toString())
                    .caixa(caixa)
                    .jogo(jogo)
                    .quantidade(request.getQuantidade())
                    .dataVenda(dataVenda)
                    .usuario(usuario)
                    .build();
            log.info("Criando nova venda");
        }

        // Calcular valor total
        vendaCaixa.calcularValorTotal();

        VendaCaixa vendaSalva = vendaCaixaRepository.save(vendaCaixa);
        log.info("Venda registrada com sucesso: {}", vendaSalva.getId());

        return vendaCaixaMapper.toResponse(vendaSalva);
    }

    @Transactional(readOnly = true)
    public Page<VendaCaixaResponse> listarVendas(Pageable pageable) {
        log.info("Listando vendas");
        Page<VendaCaixa> vendas = vendaCaixaRepository.findAll(pageable);
        return vendas.map(vendaCaixaMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<VendaCaixaResponse> listarVendasPorData(LocalDate dataInicio, LocalDate dataFim, Pageable pageable) {
        log.info("Listando vendas entre {} e {}", dataInicio, dataFim);
        Page<VendaCaixa> vendas = vendaCaixaRepository.findByDataVendaBetween(dataInicio, dataFim, pageable);
        return vendas.map(vendaCaixaMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public VendaCaixaResponse buscarPorId(String id) {
        log.info("Buscando venda por ID: {}", id);
        VendaCaixa vendaCaixa = vendaCaixaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Venda não encontrada"));
        return vendaCaixaMapper.toResponse(vendaCaixa);
    }

    public void excluirVenda(String id) {
        log.info("Excluindo venda: {}", id);
        if (!vendaCaixaRepository.existsById(id)) {
            throw new IllegalArgumentException("Venda não encontrada");
        }
        vendaCaixaRepository.deleteById(id);
        log.info("Venda excluída com sucesso: {}", id);
    }
}
