package com.loteria360.controller;

import com.loteria360.domain.dto.BolaoResponse;
import com.loteria360.domain.dto.CriarBolaoRequest;
import com.loteria360.service.BolaoService;
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

@RestController
@RequestMapping("/api/v1/boloes")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Bolões", description = "Gerenciamento de bolões")
@SecurityRequirement(name = "bearerAuth")
public class BolaoController {

    private final BolaoService bolaoService;

    @PostMapping
    @Operation(summary = "Criar bolão", description = "Cria um novo bolão")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public ResponseEntity<BolaoResponse> criarBolao(@Valid @RequestBody CriarBolaoRequest request) {
        log.info("Criando bolão para jogo: {}", request.getJogoId());
        BolaoResponse response = bolaoService.criarBolao(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar bolões", description = "Lista todos os bolões com paginação")
    public ResponseEntity<Page<BolaoResponse>> listarBoloes(
            @PageableDefault(size = 20) Pageable pageable) {
        log.info("Listando bolões");
        Page<BolaoResponse> response = bolaoService.listarBoloes(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/abertos")
    @Operation(summary = "Listar bolões abertos", description = "Lista apenas bolões abertos para vendas")
    public ResponseEntity<Page<BolaoResponse>> listarBoloesAbertos(
            @PageableDefault(size = 20) Pageable pageable) {
        log.info("Listando bolões abertos");
        Page<BolaoResponse> response = bolaoService.listarBoloesAbertos(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/jogo/{jogoId}")
    @Operation(summary = "Listar bolões por jogo", description = "Lista bolões de um jogo específico")
    public ResponseEntity<Page<BolaoResponse>> listarBoloesPorJogo(
            @PathVariable String jogoId,
            @PageableDefault(size = 20) Pageable pageable) {
        log.info("Listando bolões do jogo: {}", jogoId);
        Page<BolaoResponse> response = bolaoService.listarBoloesPorJogo(jogoId, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar bolão por ID", description = "Retorna os dados de um bolão específico")
    public ResponseEntity<BolaoResponse> buscarPorId(@PathVariable String id) {
        log.info("Buscando bolão por ID: {}", id);
        BolaoResponse response = bolaoService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/encerrar")
    @Operation(summary = "Encerrar bolão", description = "Encerra um bolão (não permite mais vendas)")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public ResponseEntity<BolaoResponse> encerrarBolao(@PathVariable String id) {
        log.info("Encerrando bolão: {}", id);
        BolaoResponse response = bolaoService.encerrarBolao(id);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/cancelar")
    @Operation(summary = "Cancelar bolão", description = "Cancela um bolão")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public ResponseEntity<BolaoResponse> cancelarBolao(@PathVariable String id) {
        log.info("Cancelando bolão: {}", id);
        BolaoResponse response = bolaoService.cancelarBolao(id);
        return ResponseEntity.ok(response);
    }
}
