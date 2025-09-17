package com.loteria360.controller;

import com.loteria360.domain.dto.CaixaRequest;
import com.loteria360.domain.dto.CaixaResponse;
import com.loteria360.service.CaixaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/caixas")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Caixas", description = "Gerenciamento de caixas do sistema")
@SecurityRequirement(name = "bearerAuth")
public class CaixaController {

    private final CaixaService caixaService;

    @PostMapping
    @Operation(summary = "Criar caixa", description = "Cria uma nova caixa no sistema")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CaixaResponse> criarCaixa(@Valid @RequestBody CaixaRequest request) {
        log.info("Criando caixa: {}", request.getNumero());
        CaixaResponse response = caixaService.criarCaixa(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar caixas", description = "Lista todas as caixas com paginação")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
    public ResponseEntity<Page<CaixaResponse>> listarCaixas(@PageableDefault(size = 20) Pageable pageable) {
        log.info("Listando caixas");
        Page<CaixaResponse> response = caixaService.listarCaixas(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar caixa por ID", description = "Retorna os dados de uma caixa específica")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
    public ResponseEntity<CaixaResponse> buscarPorId(@PathVariable String id) {
        log.info("Buscando caixa por ID: {}", id);
        CaixaResponse response = caixaService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar caixa", description = "Atualiza os dados de uma caixa existente")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CaixaResponse> atualizarCaixa(
            @PathVariable String id,
            @Valid @RequestBody CaixaRequest request) {
        log.info("Atualizando caixa: {}", id);
        CaixaResponse response = caixaService.atualizarCaixa(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/ativas")
    @Operation(summary = "Listar caixas ativas", description = "Lista todas as caixas ativas sem paginação")
    public ResponseEntity<List<CaixaResponse>> listarCaixasAtivas() {
        log.info("Listando caixas ativas");
        List<CaixaResponse> response = caixaService.listarTodasCaixasAtivas();
        return ResponseEntity.ok(response);
    }
}
