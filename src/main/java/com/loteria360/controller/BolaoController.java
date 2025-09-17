package com.loteria360.controller;

import com.loteria360.domain.dto.BolaoResponse;
import com.loteria360.domain.dto.CriarBolaoRequest;
import com.loteria360.domain.dto.AtualizarBolaoRequest;
import com.loteria360.service.BolaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/boloes")
@RequiredArgsConstructor
@Slf4j
public class BolaoController {

    private final BolaoService bolaoService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
    public ResponseEntity<Page<BolaoResponse>> listarBoloes(
            @PageableDefault(size = 10) Pageable pageable,
            @RequestParam(required = false) String search) {
        log.info("Listando bolões com paginação");
        Page<BolaoResponse> boloes = bolaoService.listarBoloes(pageable, search);
        return ResponseEntity.ok(boloes);
    }

    @GetMapping("/ativos")
    public ResponseEntity<List<BolaoResponse>> listarBoloesAtivos() {
        log.info("Listando todos os bolões ativos");
        List<BolaoResponse> boloes = bolaoService.listarTodosBoloesAtivos();
        return ResponseEntity.ok(boloes);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
    public ResponseEntity<BolaoResponse> buscarBolaoPorId(@PathVariable String id) {
        log.info("Buscando bolão por ID: {}", id);
        BolaoResponse bolao = bolaoService.buscarBolaoPorId(id);
        return ResponseEntity.ok(bolao);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public ResponseEntity<BolaoResponse> criarBolao(@Valid @RequestBody CriarBolaoRequest request) {
        log.info("Criando novo bolão");
        BolaoResponse bolao = bolaoService.criarBolao(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(bolao);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public ResponseEntity<BolaoResponse> atualizarBolao(
            @PathVariable String id,
            @Valid @RequestBody AtualizarBolaoRequest request) {
        log.info("Atualizando bolão: {}", id);
        BolaoResponse bolao = bolaoService.atualizarBolao(id, request);
        return ResponseEntity.ok(bolao);
    }

    @PatchMapping("/{id}/toggle-status")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public ResponseEntity<BolaoResponse> alterarStatusBolao(@PathVariable String id) {
        log.info("Alterando status do bolão: {}", id);
        BolaoResponse bolao = bolaoService.alterarStatusBolao(id);
        return ResponseEntity.ok(bolao);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public ResponseEntity<Void> deletarBolao(@PathVariable String id) {
        log.info("Deletando bolão: {}", id);
        bolaoService.deletarBolao(id);
        return ResponseEntity.noContent().build();
    }
}
