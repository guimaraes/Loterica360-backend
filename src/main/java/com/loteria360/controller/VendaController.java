package com.loteria360.controller;

import com.loteria360.domain.dto.*;
import com.loteria360.domain.model.Usuario;
import com.loteria360.security.CurrentUser;
import com.loteria360.service.VendaService;
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
@RequestMapping("/api/v1/vendas")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Vendas", description = "Gerenciamento de vendas")
@SecurityRequirement(name = "bearerAuth")
public class VendaController {

    private final VendaService vendaService;

    @PostMapping("/jogo")
    @Operation(summary = "Criar venda de jogo", description = "Realiza a venda de um jogo individual")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
    public ResponseEntity<VendaResponse> criarVendaJogo(
            @Valid @RequestBody VendaJogoRequest request,
            @CurrentUser Usuario usuario) {
        log.info("Criando venda de jogo: {} - quantidade: {}", request.getJogoId(), request.getQuantidade());
        VendaResponse response = vendaService.criarVendaJogo(request, usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/bolao")
    @Operation(summary = "Criar venda de bolão", description = "Realiza a venda de cotas de um bolão")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
    public ResponseEntity<VendaResponse> criarVendaBolao(
            @Valid @RequestBody VendaBolaoRequest request,
            @CurrentUser Usuario usuario) {
        log.info("Criando venda de bolão: {} - cotas: {}", request.getBolaoId(), request.getCotas());
        VendaResponse response = vendaService.criarVendaBolao(request, usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/{id}/cancelar")
    @Operation(summary = "Cancelar venda", description = "Cancela uma venda realizada")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public ResponseEntity<VendaResponse> cancelarVenda(
            @PathVariable String id,
            @Valid @RequestBody CancelarVendaRequest request,
            @CurrentUser Usuario usuario) {
        log.info("Cancelando venda: {} - motivo: {}", id, request.getMotivoCancelamento());
        VendaResponse response = vendaService.cancelarVenda(id, request, usuario);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Listar vendas", description = "Lista todas as vendas com paginação")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'AUDITOR')")
    public ResponseEntity<Page<VendaResponse>> listarVendas(
            @PageableDefault(size = 20) Pageable pageable) {
        log.info("Listando vendas");
        Page<VendaResponse> response = vendaService.listarVendas(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar venda por ID", description = "Retorna os dados de uma venda específica")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR', 'AUDITOR')")
    public ResponseEntity<VendaResponse> buscarPorId(@PathVariable String id) {
        log.info("Buscando venda por ID: {}", id);
        VendaResponse response = vendaService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }
}
