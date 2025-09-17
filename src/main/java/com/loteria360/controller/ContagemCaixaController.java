package com.loteria360.controller;

import com.loteria360.domain.dto.ContagemCaixaRequest;
import com.loteria360.domain.dto.ContagemCaixaResponse;
import com.loteria360.security.CurrentUser;
import com.loteria360.service.ContagemCaixaService;
import com.loteria360.domain.model.Usuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/contagem-caixa")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Contagem de Caixa", description = "Gerenciamento de contagem de cédulas e moedas")
@SecurityRequirement(name = "bearerAuth")
public class ContagemCaixaController {

    private final ContagemCaixaService contagemCaixaService;

    @PostMapping
    @Operation(summary = "Registrar contagem", description = "Registra a contagem de cédulas e moedas de uma caixa")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
    public ResponseEntity<ContagemCaixaResponse> registrarContagem(
            @Valid @RequestBody ContagemCaixaRequest request,
            @CurrentUser Usuario usuario) {
        log.info("Registrando contagem para caixa: {}", request.getCaixaId());
        ContagemCaixaResponse response = contagemCaixaService.registrarContagem(request, usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar contagens", description = "Lista todas as contagens com paginação")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
    public ResponseEntity<Page<ContagemCaixaResponse>> listarContagens(@PageableDefault(size = 20) Pageable pageable) {
        log.info("Listando contagens");
        Page<ContagemCaixaResponse> response = contagemCaixaService.listarContagens(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/periodo")
    @Operation(summary = "Listar contagens por período", description = "Lista contagens em um período específico")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
    public ResponseEntity<Page<ContagemCaixaResponse>> listarContagensPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim,
            @PageableDefault(size = 20) Pageable pageable) {
        log.info("Listando contagens entre {} e {}", dataInicio, dataFim);
        Page<ContagemCaixaResponse> response = contagemCaixaService.listarContagensPorData(dataInicio, dataFim, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar contagem por ID", description = "Retorna os dados de uma contagem específica")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
    public ResponseEntity<ContagemCaixaResponse> buscarPorId(@PathVariable String id) {
        log.info("Buscando contagem por ID: {}", id);
        ContagemCaixaResponse response = contagemCaixaService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir contagem", description = "Exclui uma contagem específica")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public ResponseEntity<?> excluirContagem(@PathVariable String id) {
        log.info("Excluindo contagem: {}", id);
        contagemCaixaService.excluirContagem(id);
        return ResponseEntity.ok().body("{\"message\": \"Contagem excluída com sucesso\"}");
    }
}
