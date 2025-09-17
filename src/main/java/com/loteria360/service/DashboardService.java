package com.loteria360.service;

import com.loteria360.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class DashboardService {

    private final UsuarioRepository usuarioRepository;
    private final JogoRepository jogoRepository;
    private final ClienteRepository clienteRepository;
    private final CaixaRepository caixaRepository;
    private final VendaCaixaRepository vendaCaixaRepository;
    private final ContagemCaixaRepository contagemCaixaRepository;
    private final BolaoRepository bolaoRepository;

    public Map<String, Object> getDashboardMetrics() {
        log.info("Calculando métricas do dashboard");

        Map<String, Object> metrics = new HashMap<>();

        // Métricas básicas
        long totalUsuarios = usuarioRepository.count();
        long totalJogos = jogoRepository.count();
        long jogosAtivos = jogoRepository.countByAtivoTrue();
        long totalClientes = clienteRepository.count();
        long totalCaixas = caixaRepository.count();
        long caixasAtivas = caixaRepository.countByAtivoTrue();
        long totalBoloes = bolaoRepository.count();
        long boloesAbertos = bolaoRepository.countByStatus(com.loteria360.domain.model.StatusBolao.ABERTO);

        // Vendas do dia
        LocalDate hoje = LocalDate.now();
        List<com.loteria360.domain.model.VendaCaixa> vendasHoje = vendaCaixaRepository.findByDataVenda(hoje);
        BigDecimal totalVendasHoje = vendasHoje.stream()
                .map(com.loteria360.domain.model.VendaCaixa::getValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Vendas da semana
        LocalDate inicioSemana = hoje.minusDays(7);
        List<com.loteria360.domain.model.VendaCaixa> vendasSemana = vendaCaixaRepository.findByDataVendaBetween(inicioSemana, hoje);
        BigDecimal totalVendasSemana = vendasSemana.stream()
                .map(com.loteria360.domain.model.VendaCaixa::getValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Contagens do dia
        List<com.loteria360.domain.model.ContagemCaixa> contagensHoje = contagemCaixaRepository.findByDataContagem(hoje);
        BigDecimal totalContagensHoje = contagensHoje.stream()
                .map(com.loteria360.domain.model.ContagemCaixa::getTotalGeral)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        metrics.put("usuarios", Map.of(
                "total", totalUsuarios,
                "ativos", usuarioRepository.countByAtivoTrue()
        ));

        metrics.put("jogos", Map.of(
                "total", totalJogos,
                "ativos", jogosAtivos
        ));

        metrics.put("clientes", Map.of(
                "total", totalClientes
        ));

        metrics.put("caixas", Map.of(
                "total", totalCaixas,
                "ativas", caixasAtivas
        ));

        metrics.put("boloes", Map.of(
                "total", totalBoloes,
                "abertos", boloesAbertos
        ));

        metrics.put("vendas", Map.of(
                "hoje", totalVendasHoje,
                "semana", totalVendasSemana,
                "totalHoje", vendasHoje.size()
        ));

        metrics.put("contagens", Map.of(
                "hoje", totalContagensHoje,
                "totalHoje", contagensHoje.size()
        ));

        return metrics;
    }

    public Map<String, Object> getSalesSummary(int days) {
        log.info("Calculando resumo de vendas dos últimos {} dias", days);

        LocalDate fim = LocalDate.now();
        LocalDate inicio = fim.minusDays(days);

        List<com.loteria360.domain.model.VendaCaixa> vendas = vendaCaixaRepository.findByDataVendaBetween(inicio, fim);

        // Agrupar por data
        Map<String, List<com.loteria360.domain.model.VendaCaixa>> vendasPorData = new LinkedHashMap<>();
        for (int i = 0; i < days; i++) {
            LocalDate data = inicio.plusDays(i);
            String dataStr = data.format(DateTimeFormatter.ofPattern("dd/MM"));
            vendasPorData.put(dataStr, new ArrayList<>());
        }

        // Preencher com vendas reais
        for (com.loteria360.domain.model.VendaCaixa venda : vendas) {
            String dataStr = venda.getDataVenda().format(DateTimeFormatter.ofPattern("dd/MM"));
            vendasPorData.getOrDefault(dataStr, new ArrayList<>()).add(venda);
        }

        // Calcular totais por dia
        Map<String, Object> summary = new HashMap<>();
        List<Map<String, Object>> vendasPorDia = new ArrayList<>();

        for (Map.Entry<String, List<com.loteria360.domain.model.VendaCaixa>> entry : vendasPorData.entrySet()) {
            String data = entry.getKey();
            List<com.loteria360.domain.model.VendaCaixa> vendasDoDia = entry.getValue();

            BigDecimal totalDia = vendasDoDia.stream()
                    .map(com.loteria360.domain.model.VendaCaixa::getValorTotal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            Map<String, Object> diaVenda = new HashMap<>();
            diaVenda.put("data", data);
            diaVenda.put("total", totalDia);
            diaVenda.put("quantidade", vendasDoDia.size());
            vendasPorDia.add(diaVenda);
        }

        // Calcular totais gerais
        BigDecimal totalPeriodo = vendas.stream()
                .map(com.loteria360.domain.model.VendaCaixa::getValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal mediaDiaria = days > 0 ? totalPeriodo.divide(BigDecimal.valueOf(days), 2, RoundingMode.HALF_UP) : BigDecimal.ZERO;

        summary.put("periodo", Map.of(
                "inicio", inicio.toString(),
                "fim", fim.toString(),
                "dias", days
        ));

        summary.put("totais", Map.of(
                "totalPeriodo", totalPeriodo,
                "mediaDiaria", mediaDiaria,
                "totalVendas", vendas.size()
        ));

        summary.put("vendasPorDia", vendasPorDia);

        return summary;
    }

    public Map<String, Object> getBoloesSummary() {
        log.info("Calculando resumo de bolões");

        List<com.loteria360.domain.model.Bolao> boloes = bolaoRepository.findAll();

        long totalBoloes = boloes.size();
        long boloesAbertos = bolaoRepository.countByStatus(com.loteria360.domain.model.StatusBolao.ABERTO);
        long boloesEncerrados = bolaoRepository.countByStatus(com.loteria360.domain.model.StatusBolao.ENCERRADO);
        long boloesCancelados = bolaoRepository.countByStatus(com.loteria360.domain.model.StatusBolao.CANCELADO);

        // Calcular totais de cotas
        int totalCotas = boloes.stream().mapToInt(com.loteria360.domain.model.Bolao::getCotasTotais).sum();
        int cotasVendidas = boloes.stream().mapToInt(com.loteria360.domain.model.Bolao::getCotasVendidas).sum();
        int cotasDisponiveis = totalCotas - cotasVendidas;

        // Bolões mais vendidos (top 5)
        List<Map<String, Object>> topBoloes = boloes.stream()
                .filter(bolao -> bolao.getCotasVendidas() > 0)
                .sorted((b1, b2) -> Integer.compare(b2.getCotasVendidas(), b1.getCotasVendidas()))
                .limit(5)
                .map(bolao -> {
                    double percentualVendido = bolao.getCotasTotais() > 0 
                            ? (double) bolao.getCotasVendidas() / bolao.getCotasTotais() * 100 
                            : 0;

                    Map<String, Object> bolaoMap = new HashMap<>();
                    bolaoMap.put("id", bolao.getId());
                    bolaoMap.put("jogoNome", bolao.getJogo().getNome());
                    bolaoMap.put("concurso", bolao.getConcurso());
                    bolaoMap.put("cotasTotais", bolao.getCotasTotais());
                    bolaoMap.put("cotasVendidas", bolao.getCotasVendidas());
                    bolaoMap.put("percentualVendido", String.format("%.1f%%", percentualVendido));
                    bolaoMap.put("status", bolao.getStatus().toString());
                    return bolaoMap;
                })
                .toList();

        Map<String, Object> summary = new HashMap<>();
        summary.put("resumo", Map.of(
                "totalBoloes", totalBoloes,
                "boloesAbertos", boloesAbertos,
                "boloesEncerrados", boloesEncerrados,
                "boloesCancelados", boloesCancelados
        ));

        summary.put("cotas", Map.of(
                "totalCotas", totalCotas,
                "cotasVendidas", cotasVendidas,
                "cotasDisponiveis", cotasDisponiveis,
                "percentualVendido", totalCotas > 0 ? String.format("%.1f%%", (double) cotasVendidas / totalCotas * 100) : "0%"
        ));

        summary.put("topBoloes", topBoloes);

        return summary;
    }

    public Map<String, Object> getRecentActivity() {
        log.info("Obtendo atividades recentes");

        Map<String, Object> activity = new HashMap<>();

        // Vendas recentes (últimas 10)
        List<com.loteria360.domain.model.VendaCaixa> vendasRecentes = vendaCaixaRepository.findAll(
                PageRequest.of(0, 10)
        ).getContent();

        List<Map<String, Object>> vendas = vendasRecentes.stream()
                .map(venda -> {
                    Map<String, Object> vendaMap = new HashMap<>();
                    vendaMap.put("id", venda.getId());
                    vendaMap.put("jogoNome", venda.getJogo().getNome());
                    vendaMap.put("quantidade", venda.getQuantidade());
                    vendaMap.put("valorTotal", venda.getValorTotal());
                    vendaMap.put("dataVenda", venda.getDataVenda().toString());
                    vendaMap.put("usuarioNome", venda.getUsuario().getNome());
                    vendaMap.put("caixaNumero", venda.getCaixa().getNumero());
                    return vendaMap;
                })
                .toList();

        // Contagens recentes (últimas 5)
        List<com.loteria360.domain.model.ContagemCaixa> contagensRecentes = contagemCaixaRepository.findAll(
                PageRequest.of(0, 5)
        ).getContent();

        List<Map<String, Object>> contagens = contagensRecentes.stream()
                .map(contagem -> {
                    Map<String, Object> contagemMap = new HashMap<>();
                    contagemMap.put("id", contagem.getId());
                    contagemMap.put("caixaNumero", contagem.getCaixa().getNumero());
                    contagemMap.put("totalGeral", contagem.getTotalGeral());
                    contagemMap.put("dataContagem", contagem.getDataContagem().toString());
                    contagemMap.put("usuarioNome", contagem.getUsuario().getNome());
                    return contagemMap;
                })
                .toList();

        // Bolões recentes (últimos 5)
        List<com.loteria360.domain.model.Bolao> boloesRecentes = bolaoRepository.findAll(
                PageRequest.of(0, 5)
        ).getContent();

        List<Map<String, Object>> boloes = boloesRecentes.stream()
                .map(bolao -> {
                    Map<String, Object> bolaoMap = new HashMap<>();
                    bolaoMap.put("id", bolao.getId());
                    bolaoMap.put("jogoNome", bolao.getJogo().getNome());
                    bolaoMap.put("concurso", bolao.getConcurso());
                    bolaoMap.put("status", bolao.getStatus().toString());
                    bolaoMap.put("criadoEm", bolao.getCriadoEm().toString());
                    return bolaoMap;
                })
                .toList();

        activity.put("vendas", vendas);
        activity.put("contagens", contagens);
        activity.put("boloes", boloes);

        return activity;
    }
}
