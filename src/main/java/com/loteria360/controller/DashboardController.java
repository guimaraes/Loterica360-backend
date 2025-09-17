package com.loteria360.controller;

import com.loteria360.service.DashboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
@Slf4j
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/metrics")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
    public ResponseEntity<Map<String, Object>> getDashboardMetrics() {
        log.info("Obtendo métricas do dashboard");
        Map<String, Object> metrics = dashboardService.getDashboardMetrics();
        return ResponseEntity.ok(metrics);
    }

    @GetMapping("/sales-summary")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
    public ResponseEntity<Map<String, Object>> getSalesSummary(
            @RequestParam(defaultValue = "7") int days) {
        log.info("Obtendo resumo de vendas dos últimos {} dias", days);
        Map<String, Object> summary = dashboardService.getSalesSummary(days);
        return ResponseEntity.ok(summary);
    }

    @GetMapping("/boloes-summary")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
    public ResponseEntity<Map<String, Object>> getBoloesSummary() {
        log.info("Obtendo resumo de bolões");
        Map<String, Object> summary = dashboardService.getBoloesSummary();
        return ResponseEntity.ok(summary);
    }

    @GetMapping("/recent-activity")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
    public ResponseEntity<Map<String, Object>> getRecentActivity() {
        log.info("Obtendo atividades recentes");
        Map<String, Object> activity = dashboardService.getRecentActivity();
        return ResponseEntity.ok(activity);
    }

    @GetMapping("/performance-analysis")
    @Operation(summary = "Análise de desempenho por período", description = "Analisa vendas em um período específico com comparações")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'AUDITOR')")
    public ResponseEntity<Map<String, Object>> getPerformanceAnalysis(
            @RequestParam String dataInicio,
            @RequestParam String dataFim,
            @RequestParam(required = false) String tipoComparacao) {
        log.info("Análise de desempenho de {} a {} com comparação: {}", dataInicio, dataFim, tipoComparacao);
        
        LocalDate inicio = LocalDate.parse(dataInicio);
        LocalDate fim = LocalDate.parse(dataFim);
        
        Map<String, Object> analysis = dashboardService.getPerformanceAnalysis(inicio, fim, tipoComparacao);
        return ResponseEntity.ok(analysis);
    }

    @GetMapping("/monthly-comparison")
    @Operation(summary = "Comparação mês a mês", description = "Compara vendas dos últimos 12 meses")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'AUDITOR')")
    public ResponseEntity<Map<String, Object>> getMonthlyComparison() {
        log.info("Obtendo comparação mensal");
        Map<String, Object> comparison = dashboardService.getMonthlyComparison();
        return ResponseEntity.ok(comparison);
    }

    @GetMapping("/yearly-comparison")
    @Operation(summary = "Comparação ano a ano", description = "Compara vendas dos últimos anos")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'AUDITOR')")
    public ResponseEntity<Map<String, Object>> getYearlyComparison() {
        log.info("Obtendo comparação anual");
        Map<String, Object> comparison = dashboardService.getYearlyComparison();
        return ResponseEntity.ok(comparison);
    }

    @GetMapping("/trend-analysis")
    @Operation(summary = "Análise de tendências", description = "Analisa tendências e sazonalidade das vendas")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'AUDITOR')")
    public ResponseEntity<Map<String, Object>> getTrendAnalysis() {
        log.info("Obtendo análise de tendências");
        Map<String, Object> trends = dashboardService.getTrendAnalysis();
        return ResponseEntity.ok(trends);
    }
}
