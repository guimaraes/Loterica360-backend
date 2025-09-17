package com.loteria360.service;

import com.loteria360.domain.dto.VendaCaixaResponse;
import com.loteria360.domain.dto.ContagemCaixaResponse;
import com.loteria360.repository.VendaCaixaRepository;
import com.loteria360.repository.ContagemCaixaRepository;
import com.loteria360.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class RelatorioService {

    private final VendaCaixaRepository vendaCaixaRepository;
    private final ContagemCaixaRepository contagemCaixaRepository;
    private final UsuarioRepository usuarioRepository;

    public Map<String, Object> gerarRelatorioVendas(LocalDate dataInicio, LocalDate dataFim) {
        log.info("Gerando relatório de vendas de {} até {}", dataInicio, dataFim);

        Map<String, Object> relatorio = new HashMap<>();

        // Total de vendas por jogo no período
        Map<String, Long> vendasPorJogo = new HashMap<>();
        Map<String, BigDecimal> valorPorJogo = new HashMap<>();

        Pageable pageable = PageRequest.of(0, 1000); // Buscar todas as vendas
        Page<VendaCaixaResponse> vendas = vendaCaixaRepository.findByDataVendaBetween(dataInicio, dataFim, pageable)
                .map(venda -> VendaCaixaResponse.builder()
                        .id(venda.getId())
                        .caixaId(venda.getCaixa().getId())
                        .numeroCaixa(venda.getCaixa().getNumero())
                        .jogoId(venda.getJogo().getId())
                        .nomeJogo(venda.getJogo().getNome())
                        .quantidade(venda.getQuantidade())
                        .valorTotal(venda.getValorTotal())
                        .dataVenda(venda.getDataVenda())
                        .build());

        for (VendaCaixaResponse venda : vendas.getContent()) {
            vendasPorJogo.merge(venda.getNomeJogo(), (long) venda.getQuantidade(), Long::sum);
            valorPorJogo.merge(venda.getNomeJogo(), venda.getValorTotal(), BigDecimal::add);
        }

        relatorio.put("periodo", Map.of(
                "dataInicio", dataInicio.toString(),
                "dataFim", dataFim.toString()
        ));
        relatorio.put("vendasPorJogo", vendasPorJogo);
        relatorio.put("valorPorJogo", valorPorJogo);
        relatorio.put("totalVendas", vendas.getTotalElements());
        relatorio.put("valorTotal", valorPorJogo.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add));

        return relatorio;
    }

    public Map<String, Object> gerarRelatorioContagem(LocalDate dataInicio, LocalDate dataFim) {
        log.info("Gerando relatório de contagem de {} até {}", dataInicio, dataFim);

        Map<String, Object> relatorio = new HashMap<>();

        Pageable pageable = PageRequest.of(0, 1000);
        Page<ContagemCaixaResponse> contagens = contagemCaixaRepository.findByDataContagemBetween(dataInicio, dataFim, pageable)
                .map(contagem -> ContagemCaixaResponse.builder()
                        .id(contagem.getId())
                        .caixaId(contagem.getCaixa().getId())
                        .numeroCaixa(contagem.getCaixa().getNumero())
                        .dataContagem(contagem.getDataContagem())
                        .totalNotas(contagem.getTotalNotas())
                        .totalMoedas(contagem.getTotalMoedas())
                        .totalGeral(contagem.getTotalGeral())
                        .build());

        BigDecimal totalNotas = BigDecimal.ZERO;
        BigDecimal totalMoedas = BigDecimal.ZERO;
        BigDecimal totalGeral = BigDecimal.ZERO;

        for (ContagemCaixaResponse contagem : contagens.getContent()) {
            totalNotas = totalNotas.add(contagem.getTotalNotas());
            totalMoedas = totalMoedas.add(contagem.getTotalMoedas());
            totalGeral = totalGeral.add(contagem.getTotalGeral());
        }

        relatorio.put("periodo", Map.of(
                "dataInicio", dataInicio.toString(),
                "dataFim", dataFim.toString()
        ));
        relatorio.put("totalContagens", contagens.getTotalElements());
        relatorio.put("totalNotas", totalNotas);
        relatorio.put("totalMoedas", totalMoedas);
        relatorio.put("totalGeral", totalGeral);

        return relatorio;
    }

    public Map<String, Object> gerarRelatorioConsolidado(LocalDate data) {
        log.info("Gerando relatório consolidado para {}", data);

        Map<String, Object> relatorio = new HashMap<>();

        // Relatório de vendas do dia
        Map<String, Object> vendas = gerarRelatorioVendas(data, data);
        
        // Relatório de contagem do dia
        Map<String, Object> contagem = gerarRelatorioContagem(data, data);

        relatorio.put("data", data.toString());
        relatorio.put("vendas", vendas);
        relatorio.put("contagem", contagem);

        return relatorio;
    }
}