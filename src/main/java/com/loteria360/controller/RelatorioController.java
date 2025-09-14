package com.loteria360.controller;

import com.loteria360.domain.dto.RelatorioBoloesStatusResponse;
import com.loteria360.domain.dto.RelatorioVendasResponse;
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
    @Operation(summary = "Relatório de vendas", description = "Gera relatório de vendas por período")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'AUDITOR')")
    public ResponseEntity<RelatorioVendasResponse> relatorioVendas(
            @Parameter(description = "Data de início (YYYY-MM-DD)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate de,
            @Parameter(description = "Data de fim (YYYY-MM-DD)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate ate,
            @Parameter(description = "ID do vendedor (opcional)")
            @RequestParam(required = false) String vendedorId) {
        
        log.info("Gerando relatório de vendas de {} até {}", de, ate);
        RelatorioVendasResponse response = relatorioService.gerarRelatorioVendas(de, ate, vendedorId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/boloes/status")
    @Operation(summary = "Relatório de status dos bolões", description = "Gera relatório com status atual dos bolões")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'AUDITOR')")
    public ResponseEntity<RelatorioBoloesStatusResponse> relatorioBoloesStatus() {
        log.info("Gerando relatório de status dos bolões");
        RelatorioBoloesStatusResponse response = relatorioService.gerarRelatorioBoloesStatus();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/pagamentos")
    @Operation(summary = "Relatório de pagamentos", description = "Gera relatório de pagamentos por método")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'AUDITOR')")
    public ResponseEntity<Map<String, String>> relatorioPagamentos(
            @Parameter(description = "Data de início (YYYY-MM-DD)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate de,
            @Parameter(description = "Data de fim (YYYY-MM-DD)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate ate) {
        
        log.info("Gerando relatório de pagamentos de {} até {}", de, ate);
        Map<String, String> response = relatorioService.gerarRelatorioPagamentos(de, ate)
                .entrySet().stream()
                .collect(java.util.stream.Collectors.toMap(
                        entry -> entry.getKey().name(),
                        entry -> "R$ " + entry.getValue().toString()
                ));
        return ResponseEntity.ok(response);
    }
}
