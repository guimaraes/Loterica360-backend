package com.loteria360.controller;

import com.loteria360.domain.dto.VendaCaixaRequest;
import com.loteria360.domain.dto.VendaCaixaResponse;
import com.loteria360.security.CurrentUser;
import com.loteria360.service.VendaCaixaService;
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
@RequestMapping("/api/v1/vendas-caixa")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Vendas por Caixa", description = "Gerenciamento de vendas por caixa")
@SecurityRequirement(name = "bearerAuth")
public class VendaCaixaController {

    private final VendaCaixaService vendaCaixaService;

    @PostMapping
    @Operation(summary = "Registrar venda", description = "Registra uma venda para uma caixa específica")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
    public ResponseEntity<VendaCaixaResponse> registrarVenda(
            @Valid @RequestBody VendaCaixaRequest request,
            @CurrentUser Usuario usuario) {
        log.info("Registrando venda para caixa: {}", request.getCaixaId());
        VendaCaixaResponse response = vendaCaixaService.registrarVenda(request, usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar vendas", description = "Lista todas as vendas com paginação")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
    public ResponseEntity<Page<VendaCaixaResponse>> listarVendas(@PageableDefault(size = 20) Pageable pageable) {
        log.info("Listando vendas");
        Page<VendaCaixaResponse> response = vendaCaixaService.listarVendas(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/periodo")
    @Operation(summary = "Listar vendas por período", description = "Lista vendas em um período específico")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
    public ResponseEntity<Page<VendaCaixaResponse>> listarVendasPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim,
            @PageableDefault(size = 20) Pageable pageable) {
        log.info("Listando vendas entre {} e {}", dataInicio, dataFim);
        Page<VendaCaixaResponse> response = vendaCaixaService.listarVendasPorData(dataInicio, dataFim, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar venda por ID", description = "Retorna os dados de uma venda específica")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
    public ResponseEntity<VendaCaixaResponse> buscarPorId(@PathVariable String id) {
        log.info("Buscando venda por ID: {}", id);
        VendaCaixaResponse response = vendaCaixaService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir venda", description = "Exclui uma venda específica")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public ResponseEntity<?> excluirVenda(@PathVariable String id) {
        log.info("Excluindo venda: {}", id);
        vendaCaixaService.excluirVenda(id);
        return ResponseEntity.ok().body("{\"message\": \"Venda excluída com sucesso\"}");
    }
}
