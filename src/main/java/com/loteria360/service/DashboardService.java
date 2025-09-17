package com.loteria360.service;

import com.loteria360.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

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

    /**
     * Análise de desempenho por período customizado
     */
    public Map<String, Object> getPerformanceAnalysis(LocalDate dataInicio, LocalDate dataFim, String tipoComparacao) {
        log.info("Analisando desempenho de {} a {}", dataInicio, dataFim);
        
        Map<String, Object> analysis = new HashMap<>();
        
        // Vendas do período atual
        List<com.loteria360.domain.model.VendaCaixa> vendasPeriodo = vendaCaixaRepository.findByDataVendaBetween(dataInicio, dataFim);
        BigDecimal totalPeriodo = vendasPeriodo.stream()
                .map(com.loteria360.domain.model.VendaCaixa::getValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        // Vendas por dia no período
        Map<LocalDate, BigDecimal> vendasPorDia = vendasPeriodo.stream()
                .collect(Collectors.groupingBy(
                    com.loteria360.domain.model.VendaCaixa::getDataVenda,
                    Collectors.reducing(BigDecimal.ZERO, 
                        com.loteria360.domain.model.VendaCaixa::getValorTotal, 
                        BigDecimal::add)
                ));
        
        // Vendas por jogo no período
        Map<String, BigDecimal> vendasPorJogo = vendasPeriodo.stream()
                .collect(Collectors.groupingBy(
                    venda -> venda.getJogo().getNome(),
                    Collectors.reducing(BigDecimal.ZERO,
                        com.loteria360.domain.model.VendaCaixa::getValorTotal,
                        BigDecimal::add)
                ));
        
        // Vendas por caixa no período
        Map<String, BigDecimal> vendasPorCaixa = vendasPeriodo.stream()
                .collect(Collectors.groupingBy(
                    venda -> "Caixa " + venda.getCaixa().getNumero(),
                    Collectors.reducing(BigDecimal.ZERO,
                        com.loteria360.domain.model.VendaCaixa::getValorTotal,
                        BigDecimal::add)
                ));
        
        analysis.put("periodo", Map.of(
            "inicio", dataInicio.toString(),
            "fim", dataFim.toString(),
            "dias", (int) ChronoUnit.DAYS.between(dataInicio, dataFim) + 1
        ));
        
        analysis.put("resumo", Map.of(
            "totalVendas", vendasPeriodo.size(),
            "totalValor", totalPeriodo,
            "mediaDiaria", vendasPeriodo.size() > 0 ? 
                totalPeriodo.divide(BigDecimal.valueOf(vendasPorDia.size()), 2, RoundingMode.HALF_UP) : 
                BigDecimal.ZERO
        ));
        
        analysis.put("vendasPorDia", vendasPorDia.entrySet().stream()
                .map(entry -> Map.of(
                    "data", entry.getKey().toString(),
                    "valor", entry.getValue(),
                    "diaSemana", entry.getKey().getDayOfWeek().toString()
                ))
                .sorted((a, b) -> ((String) a.get("data")).compareTo((String) b.get("data")))
                .collect(Collectors.toList()));
        
        analysis.put("vendasPorJogo", vendasPorJogo);
        analysis.put("vendasPorCaixa", vendasPorCaixa);
        
        // Comparação baseada no tipo
        if ("mesAnterior".equals(tipoComparacao)) {
            LocalDate inicioMesAnterior = dataInicio.minusMonths(1);
            LocalDate fimMesAnterior = dataFim.minusMonths(1);
            analysis.put("comparacao", getComparacaoMesAnterior(vendasPeriodo, inicioMesAnterior, fimMesAnterior));
        } else if ("anoAnterior".equals(tipoComparacao)) {
            LocalDate inicioAnoAnterior = dataInicio.minusYears(1);
            LocalDate fimAnoAnterior = dataFim.minusYears(1);
            analysis.put("comparacao", getComparacaoAnoAnterior(vendasPeriodo, inicioAnoAnterior, fimAnoAnterior));
        }
        
        return analysis;
    }
    
    /**
     * Comparação mês a mês dos últimos 12 meses
     */
    public Map<String, Object> getMonthlyComparison() {
        log.info("Gerando comparação mensal");
        
        Map<String, Object> comparison = new HashMap<>();
        LocalDate hoje = LocalDate.now();
        
        List<Map<String, Object>> meses = new ArrayList<>();
        
        for (int i = 11; i >= 0; i--) {
            LocalDate inicioMes = hoje.minusMonths(i).withDayOfMonth(1);
            LocalDate fimMes = inicioMes.withDayOfMonth(inicioMes.lengthOfMonth());
            
            // Vendas do mês atual
            List<com.loteria360.domain.model.VendaCaixa> vendasMes = vendaCaixaRepository.findByDataVendaBetween(inicioMes, fimMes);
            BigDecimal totalMes = vendasMes.stream()
                    .map(com.loteria360.domain.model.VendaCaixa::getValorTotal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            
            // Vendas do mês anterior (se não for o primeiro mês)
            BigDecimal totalMesAnterior = BigDecimal.ZERO;
            BigDecimal variacaoPercentual = BigDecimal.ZERO;
            
            if (i > 0) {
                LocalDate inicioMesAnterior = hoje.minusMonths(i + 1).withDayOfMonth(1);
                LocalDate fimMesAnterior = inicioMesAnterior.withDayOfMonth(inicioMesAnterior.lengthOfMonth());
                
                List<com.loteria360.domain.model.VendaCaixa> vendasMesAnterior = vendaCaixaRepository.findByDataVendaBetween(inicioMesAnterior, fimMesAnterior);
                totalMesAnterior = vendasMesAnterior.stream()
                        .map(com.loteria360.domain.model.VendaCaixa::getValorTotal)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                
                if (totalMesAnterior.compareTo(BigDecimal.ZERO) > 0) {
                    variacaoPercentual = totalMes.subtract(totalMesAnterior)
                            .divide(totalMesAnterior, 4, RoundingMode.HALF_UP)
                            .multiply(BigDecimal.valueOf(100));
                }
            }
            
            Map<String, Object> mesData = new HashMap<>();
            mesData.put("mes", inicioMes.format(DateTimeFormatter.ofPattern("MM/yyyy")));
            mesData.put("inicio", inicioMes.toString());
            mesData.put("fim", fimMes.toString());
            mesData.put("totalVendas", vendasMes.size());
            mesData.put("totalValor", totalMes);
            mesData.put("mesAnterior", totalMesAnterior);
            mesData.put("variacaoPercentual", variacaoPercentual);
            mesData.put("crescimento", totalMes.compareTo(totalMesAnterior) > 0);
            
            meses.add(mesData);
        }
        
        comparison.put("meses", meses);
        comparison.put("resumo", Map.of(
            "melhorMes", meses.stream()
                    .max((a, b) -> ((BigDecimal) a.get("totalValor")).compareTo((BigDecimal) b.get("totalValor")))
                    .orElse(Map.of("mes", "N/A", "totalValor", BigDecimal.ZERO)),
            "piorMes", meses.stream()
                    .filter(m -> ((BigDecimal) m.get("totalValor")).compareTo(BigDecimal.ZERO) > 0)
                    .min((a, b) -> ((BigDecimal) a.get("totalValor")).compareTo((BigDecimal) b.get("totalValor")))
                    .orElse(Map.of("mes", "N/A", "totalValor", BigDecimal.ZERO)),
            "mediaMensal", meses.stream()
                    .map(m -> (BigDecimal) m.get("totalValor"))
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    .divide(BigDecimal.valueOf(meses.size()), 2, RoundingMode.HALF_UP)
        ));
        
        return comparison;
    }
    
    /**
     * Comparação ano a ano
     */
    public Map<String, Object> getYearlyComparison() {
        log.info("Gerando comparação anual");
        
        Map<String, Object> comparison = new HashMap<>();
        LocalDate hoje = LocalDate.now();
        int anoAtual = hoje.getYear();
        
        List<Map<String, Object>> anos = new ArrayList<>();
        
        // Últimos 3 anos
        for (int i = 2; i >= 0; i--) {
            int ano = anoAtual - i;
            LocalDate inicioAno = LocalDate.of(ano, 1, 1);
            LocalDate fimAno = LocalDate.of(ano, 12, 31);
            
            List<com.loteria360.domain.model.VendaCaixa> vendasAno = vendaCaixaRepository.findByDataVendaBetween(inicioAno, fimAno);
            BigDecimal totalAno = vendasAno.stream()
                    .map(com.loteria360.domain.model.VendaCaixa::getValorTotal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            
            // Vendas por mês do ano
            List<Map<String, Object>> vendasPorMes = new ArrayList<>();
            for (int mes = 1; mes <= 12; mes++) {
                LocalDate inicioMes = LocalDate.of(ano, mes, 1);
                LocalDate fimMes = inicioMes.withDayOfMonth(inicioMes.lengthOfMonth());
                
                List<com.loteria360.domain.model.VendaCaixa> vendasMes = vendaCaixaRepository.findByDataVendaBetween(inicioMes, fimMes);
                BigDecimal totalMes = vendasMes.stream()
                        .map(com.loteria360.domain.model.VendaCaixa::getValorTotal)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                
                vendasPorMes.add(Map.of(
                    "mes", String.format("%02d", mes),
                    "nomeMes", inicioMes.format(DateTimeFormatter.ofPattern("MMM")),
                    "totalValor", totalMes,
                    "totalVendas", vendasMes.size()
                ));
            }
            
            Map<String, Object> anoData = new HashMap<>();
            anoData.put("ano", String.valueOf(ano));
            anoData.put("totalVendas", vendasAno.size());
            anoData.put("totalValor", totalAno);
            anoData.put("vendasPorMes", vendasPorMes);
            
            anos.add(anoData);
        }
        
        // Comparação com ano anterior
        if (anos.size() >= 2) {
            Map<String, Object> anoAtualData = anos.get(anos.size() - 1);
            Map<String, Object> anoAnteriorData = anos.get(anos.size() - 2);
            
            BigDecimal totalAtual = (BigDecimal) anoAtualData.get("totalValor");
            BigDecimal totalAnterior = (BigDecimal) anoAnteriorData.get("totalValor");
            
            BigDecimal variacaoAnual = BigDecimal.ZERO;
            if (totalAnterior.compareTo(BigDecimal.ZERO) > 0) {
                variacaoAnual = totalAtual.subtract(totalAnterior)
                        .divide(totalAnterior, 4, RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(100));
            }
            
            comparison.put("comparacaoAnual", Map.of(
                "anoAtual", anoAtualData,
                "anoAnterior", anoAnteriorData,
                "variacaoPercentual", variacaoAnual,
                "crescimento", totalAtual.compareTo(totalAnterior) > 0
            ));
        }
        
        comparison.put("anos", anos);
        
        return comparison;
    }
    
    /**
     * Métricas de tendência e sazonalidade
     */
    public Map<String, Object> getTrendAnalysis() {
        log.info("Analisando tendências");
        
        Map<String, Object> trends = new HashMap<>();
        LocalDate hoje = LocalDate.now();
        
        // Vendas dos últimos 30 dias para tendência
        LocalDate inicio30Dias = hoje.minusDays(29);
        List<com.loteria360.domain.model.VendaCaixa> vendas30Dias = vendaCaixaRepository.findByDataVendaBetween(inicio30Dias, hoje);
        
        // Vendas dos 30 dias anteriores para comparação
        LocalDate inicio60Dias = hoje.minusDays(59);
        LocalDate fim60Dias = hoje.minusDays(30);
        List<com.loteria360.domain.model.VendaCaixa> vendas60Dias = vendaCaixaRepository.findByDataVendaBetween(inicio60Dias, fim60Dias);
        
        BigDecimal total30Dias = vendas30Dias.stream()
                .map(com.loteria360.domain.model.VendaCaixa::getValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal total60Dias = vendas60Dias.stream()
                .map(com.loteria360.domain.model.VendaCaixa::getValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal tendencia = BigDecimal.ZERO;
        if (total60Dias.compareTo(BigDecimal.ZERO) > 0) {
            tendencia = total30Dias.subtract(total60Dias)
                    .divide(total60Dias, 4, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
        }
        
        // Análise por dia da semana
        Map<String, BigDecimal> vendasPorDiaSemana = vendas30Dias.stream()
                .collect(Collectors.groupingBy(
                    venda -> venda.getDataVenda().getDayOfWeek().toString(),
                    Collectors.reducing(BigDecimal.ZERO,
                        com.loteria360.domain.model.VendaCaixa::getValorTotal,
                        BigDecimal::add)
                ));
        
        trends.put("tendencia", Map.of(
            "periodo30Dias", total30Dias,
            "periodoAnterior", total60Dias,
            "variacaoPercentual", tendencia,
            "direcao", tendencia.compareTo(BigDecimal.ZERO) > 0 ? "CRESCIMENTO" : "DECLÍNIO"
        ));
        
        trends.put("sazonalidade", Map.of(
            "vendasPorDiaSemana", vendasPorDiaSemana,
            "melhorDiaSemana", vendasPorDiaSemana.entrySet().stream()
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey)
                    .orElse("N/A"),
            "piorDiaSemana", vendasPorDiaSemana.entrySet().stream()
                    .min(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey)
                    .orElse("N/A")
        ));
        
        return trends;
    }
    
    private Map<String, Object> getComparacaoMesAnterior(List<com.loteria360.domain.model.VendaCaixa> vendasAtuais, 
                                                         LocalDate inicioAnterior, LocalDate fimAnterior) {
        List<com.loteria360.domain.model.VendaCaixa> vendasAnteriores = vendaCaixaRepository.findByDataVendaBetween(inicioAnterior, fimAnterior);
        
        BigDecimal totalAtual = vendasAtuais.stream()
                .map(com.loteria360.domain.model.VendaCaixa::getValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal totalAnterior = vendasAnteriores.stream()
                .map(com.loteria360.domain.model.VendaCaixa::getValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal variacao = totalAtual.subtract(totalAnterior);
        BigDecimal variacaoPercentual = BigDecimal.ZERO;
        
        if (totalAnterior.compareTo(BigDecimal.ZERO) > 0) {
            variacaoPercentual = variacao.divide(totalAnterior, 4, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
        }
        
        return Map.of(
            "periodoAtual", Map.of("total", totalAtual, "vendas", vendasAtuais.size()),
            "periodoAnterior", Map.of("total", totalAnterior, "vendas", vendasAnteriores.size()),
            "variacao", variacao,
            "variacaoPercentual", variacaoPercentual,
            "crescimento", variacao.compareTo(BigDecimal.ZERO) > 0
        );
    }
    
    private Map<String, Object> getComparacaoAnoAnterior(List<com.loteria360.domain.model.VendaCaixa> vendasAtuais, 
                                                         LocalDate inicioAnterior, LocalDate fimAnterior) {
        List<com.loteria360.domain.model.VendaCaixa> vendasAnteriores = vendaCaixaRepository.findByDataVendaBetween(inicioAnterior, fimAnterior);
        
        BigDecimal totalAtual = vendasAtuais.stream()
                .map(com.loteria360.domain.model.VendaCaixa::getValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal totalAnterior = vendasAnteriores.stream()
                .map(com.loteria360.domain.model.VendaCaixa::getValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal variacao = totalAtual.subtract(totalAnterior);
        BigDecimal variacaoPercentual = BigDecimal.ZERO;
        
        if (totalAnterior.compareTo(BigDecimal.ZERO) > 0) {
            variacaoPercentual = variacao.divide(totalAnterior, 4, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
        }
        
        return Map.of(
            "periodoAtual", Map.of("total", totalAtual, "vendas", vendasAtuais.size()),
            "periodoAnterior", Map.of("total", totalAnterior, "vendas", vendasAnteriores.size()),
            "variacao", variacao,
            "variacaoPercentual", variacaoPercentual,
            "crescimento", variacao.compareTo(BigDecimal.ZERO) > 0
        );
    }
}
