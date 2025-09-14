package com.loteria360.controller;

import com.loteria360.domain.dto.MovimentoCaixaRequest;
import com.loteria360.domain.dto.MovimentoCaixaResponse;
import com.loteria360.domain.model.Usuario;
import com.loteria360.security.CurrentUser;
import com.loteria360.service.MovimentoCaixaService;
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
@RequestMapping("/api/v1/movimentos")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Movimentos de Caixa", description = "Gerenciamento de sangrias e suprimentos")
@SecurityRequirement(name = "bearerAuth")
public class MovimentoCaixaController {

    private final MovimentoCaixaService movimentoCaixaService;

    @PostMapping
    @Operation(summary = "Criar movimento de caixa", description = "Registra uma sangria ou suprimento de caixa")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
    public ResponseEntity<MovimentoCaixaResponse> criarMovimento(
            @Valid @RequestBody MovimentoCaixaRequest request,
            @CurrentUser Usuario usuario) {
        log.info("Criando movimento de caixa - tipo: {} - valor: {}", request.getTipo(), request.getValor());
        MovimentoCaixaResponse response = movimentoCaixaService.criarMovimento(request, usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar movimentos", description = "Lista todos os movimentos de caixa com paginação")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'AUDITOR')")
    public ResponseEntity<Page<MovimentoCaixaResponse>> listarMovimentos(
            @PageableDefault(size = 20) Pageable pageable) {
        log.info("Listando movimentos de caixa");
        Page<MovimentoCaixaResponse> response = movimentoCaixaService.listarMovimentos(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/turno/{turnoId}")
    @Operation(summary = "Listar movimentos por turno", description = "Lista movimentos de um turno específico")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR', 'AUDITOR')")
    public ResponseEntity<Page<MovimentoCaixaResponse>> listarMovimentosPorTurno(
            @PathVariable String turnoId,
            @PageableDefault(size = 20) Pageable pageable) {
        log.info("Listando movimentos do turno: {}", turnoId);
        Page<MovimentoCaixaResponse> response = movimentoCaixaService.listarMovimentosPorTurno(turnoId, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar movimento por ID", description = "Retorna os dados de um movimento específico")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR', 'AUDITOR')")
    public ResponseEntity<MovimentoCaixaResponse> buscarPorId(@PathVariable String id) {
        log.info("Buscando movimento por ID: {}", id);
        MovimentoCaixaResponse response = movimentoCaixaService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }
}
