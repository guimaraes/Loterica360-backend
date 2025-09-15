package com.loteria360.service;

import com.loteria360.domain.dto.*;
import com.loteria360.domain.model.*;
import com.loteria360.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class RelatorioService {

    private final VendaRepository vendaRepository;
    private final BolaoRepository bolaoRepository;
    private final PagamentoRepository pagamentoRepository;
    private final UsuarioRepository usuarioRepository;

    public RelatorioVendasResponse gerarRelatorioVendas(LocalDate dataInicio, LocalDate dataFim, String vendedorId) {
        log.info("Gerando relatório de vendas de {} até {}", dataInicio, dataFim);

        LocalDateTime inicio = dataInicio.atStartOfDay();
        LocalDateTime fim = dataFim.atTime(LocalTime.MAX);

        Page<Venda> vendas;
        if (vendedorId != null) {
            vendas = vendaRepository.findByUsuarioIdAndDataVendaBetween(vendedorId, inicio, fim, PageRequest.of(0, 1000));
        } else {
            vendas = vendaRepository.findByDataVendaBetween(inicio, fim, PageRequest.of(0, 1000));
        }

        String vendedorNome = null;
        if (vendedorId != null) {
            vendedorNome = usuarioRepository.findById(vendedorId)
                    .map(Usuario::getNome)
                    .orElse("Usuário não encontrado");
        }

        BigDecimal totalVendas = vendas.getContent().stream()
                .filter(v -> v.getStatus() == StatusVenda.CONCLUIDA)
                .map(Venda::getValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalComissoes = BigDecimal.ZERO; // TODO: Implementar cálculo de comissões

        int totalVendasCount = (int) vendas.getContent().stream()
                .filter(v -> v.getStatus() == StatusVenda.CONCLUIDA)
                .count();

        int totalVendasCanceladas = (int) vendas.getContent().stream()
                .filter(v -> v.getStatus() == StatusVenda.CANCELADA)
                .count();

        List<RelatorioVendasResponse.VendaResumoResponse> vendasResumo = vendas.getContent().stream()
                .map(v -> RelatorioVendasResponse.VendaResumoResponse.builder()
                        .id(v.getId())
                        .tipo(v.getTipoVenda().name())
                        .valorLiquido(v.getValorTotal())
                        .status(v.getStatus().name())
                        .criadoEm(v.getDataVenda().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                        .build())
                .collect(Collectors.toList());

        return RelatorioVendasResponse.builder()
                .dataInicio(dataInicio)
                .dataFim(dataFim)
                .vendedorId(vendedorId)
                .vendedorNome(vendedorNome)
                .totalVendas(totalVendas)
                .totalComissoes(totalComissoes)
                .totalVendasCount(totalVendasCount)
                .totalVendasCanceladas(totalVendasCanceladas)
                .vendas(vendasResumo)
                .build();
    }

    public RelatorioBoloesStatusResponse gerarRelatorioBoloesStatus() {
        log.info("Gerando relatório de status dos bolões");

        List<Bolao> boloes = bolaoRepository.findAll();

        int totalBoloes = boloes.size();
        int boloesAbertos = (int) boloes.stream().filter(b -> b.getStatus() == StatusBolao.ABERTO).count();
        int boloesEncerrados = (int) boloes.stream().filter(b -> b.getStatus() == StatusBolao.ENCERRADO).count();
        int boloesCancelados = (int) boloes.stream().filter(b -> b.getStatus() == StatusBolao.CANCELADO).count();

        int totalCotas = boloes.stream().mapToInt(Bolao::getCotasTotais).sum();
        int cotasVendidas = boloes.stream().mapToInt(Bolao::getCotasVendidas).sum();
        int cotasDisponiveis = totalCotas - cotasVendidas;

        List<RelatorioBoloesStatusResponse.BolaoStatusResponse> boloesStatus = boloes.stream()
                .map(b -> {
                    double percentualVendido = b.getCotasTotais() > 0 ? 
                            (double) b.getCotasVendidas() / b.getCotasTotais() * 100 : 0.0;
                    
                    return RelatorioBoloesStatusResponse.BolaoStatusResponse.builder()
                            .id(b.getId())
                            .jogoNome(b.getJogo().getNome())
                            .concurso(b.getConcurso())
                            .cotasTotais(b.getCotasTotais())
                            .cotasVendidas(b.getCotasVendidas())
                            .cotasDisponiveis(b.getCotasDisponiveis())
                            .status(b.getStatus().name())
                            .percentualVendido(String.format("%.1f%%", percentualVendido))
                            .build();
                })
                .collect(Collectors.toList());

        return RelatorioBoloesStatusResponse.builder()
                .totalBoloes(totalBoloes)
                .boloesAbertos(boloesAbertos)
                .boloesEncerrados(boloesEncerrados)
                .boloesCancelados(boloesCancelados)
                .totalCotas(totalCotas)
                .cotasVendidas(cotasVendidas)
                .cotasDisponiveis(cotasDisponiveis)
                .boloes(boloesStatus)
                .build();
    }

    public Map<MetodoPagamento, BigDecimal> gerarRelatorioPagamentos(LocalDate dataInicio, LocalDate dataFim) {
        log.info("Gerando relatório de pagamentos de {} até {}", dataInicio, dataFim);

        LocalDateTime inicio = dataInicio.atStartOfDay();
        LocalDateTime fim = dataFim.atTime(LocalTime.MAX);

        List<Venda> vendas = vendaRepository.findByDataVendaBetween(inicio, fim, PageRequest.of(0, 1000)).getContent();

        return vendas.stream()
                .filter(v -> v.getStatus() == StatusVenda.CONCLUIDA)
                .flatMap(v -> v.getPagamentos().stream())
                .filter(p -> p.getStatus() == StatusPagamento.APROVADO)
                .collect(Collectors.groupingBy(
                        Pagamento::getMetodoPagamento,
                        Collectors.reducing(BigDecimal.ZERO, Pagamento::getValor, BigDecimal::add)
                ));
    }
}
