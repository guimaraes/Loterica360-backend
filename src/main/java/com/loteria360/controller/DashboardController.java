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
}
