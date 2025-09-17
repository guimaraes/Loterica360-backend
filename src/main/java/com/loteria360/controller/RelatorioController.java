package com.loteria360.controller;

import com.loteria360.service.RelatorioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/relatorios")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Relatórios", description = "Geração de relatórios do sistema")
@SecurityRequirement(name = "bearerAuth")
public class RelatorioController {

    private final RelatorioService relatorioService;

    @GetMapping("/vendas")
    @Operation(summary = "Relatório de vendas", description = "Gera relatório de vendas por jogo no período")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'AUDITOR')")
    public ResponseEntity<Map<String, Object>> relatorioVendas(
            @Parameter(description = "Data de início (YYYY-MM-DD)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate de,
            @Parameter(description = "Data de fim (YYYY-MM-DD)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate ate) {
        
        log.info("Gerando relatório de vendas de {} até {}", de, ate);
        Map<String, Object> response = relatorioService.gerarRelatorioVendas(de, ate);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/contagem")
    @Operation(summary = "Relatório de contagem", description = "Gera relatório de contagem de cédulas e moedas")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'AUDITOR')")
    public ResponseEntity<Map<String, Object>> relatorioContagem(
            @Parameter(description = "Data de início (YYYY-MM-DD)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate de,
            @Parameter(description = "Data de fim (YYYY-MM-DD)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate ate) {
        
        log.info("Gerando relatório de contagem de {} até {}", de, ate);
        Map<String, Object> response = relatorioService.gerarRelatorioContagem(de, ate);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/consolidado")
    @Operation(summary = "Relatório consolidado", description = "Gera relatório consolidado de vendas e contagem para um dia específico")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'AUDITOR')")
    public ResponseEntity<Map<String, Object>> relatorioConsolidado(
            @Parameter(description = "Data (YYYY-MM-DD)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        
        log.info("Gerando relatório consolidado para {}", data);
        Map<String, Object> response = relatorioService.gerarRelatorioConsolidado(data);
        return ResponseEntity.ok(response);
    }
}