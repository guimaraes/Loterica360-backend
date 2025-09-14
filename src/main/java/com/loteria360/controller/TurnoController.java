package com.loteria360.controller;

import com.loteria360.domain.dto.AbrirTurnoRequest;
import com.loteria360.domain.dto.TurnoResponse;
import com.loteria360.domain.model.Usuario;
import com.loteria360.security.CurrentUser;
import com.loteria360.service.TurnoService;
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
@RequestMapping("/api/v1/turnos")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Turnos", description = "Gerenciamento de turnos de trabalho")
@SecurityRequirement(name = "bearerAuth")
public class TurnoController {

    private final TurnoService turnoService;

    @PostMapping("/abrir")
    @Operation(summary = "Abrir turno", description = "Abre um novo turno de trabalho")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
    public ResponseEntity<TurnoResponse> abrirTurno(
            @Valid @RequestBody AbrirTurnoRequest request,
            @CurrentUser Usuario usuario) {
        log.info("Abrindo turno para usuário: {} - caixa: {}", usuario.getEmail(), request.getCaixaId());
        TurnoResponse response = turnoService.abrirTurno(request, usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/{id}/fechar")
    @Operation(summary = "Fechar turno", description = "Fecha um turno de trabalho")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
    public ResponseEntity<TurnoResponse> fecharTurno(
            @PathVariable String id,
            @CurrentUser Usuario usuario) {
        log.info("Fechando turno: {} por usuário: {}", id, usuario.getEmail());
        TurnoResponse response = turnoService.fecharTurno(id, usuario);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Listar turnos", description = "Lista todos os turnos com paginação")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'AUDITOR')")
    public ResponseEntity<Page<TurnoResponse>> listarTurnos(
            @PageableDefault(size = 20) Pageable pageable) {
        log.info("Listando turnos");
        Page<TurnoResponse> response = turnoService.listarTurnos(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Listar turnos por usuário", description = "Lista turnos de um usuário específico")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'AUDITOR')")
    public ResponseEntity<Page<TurnoResponse>> listarTurnosPorUsuario(
            @PathVariable String usuarioId,
            @PageableDefault(size = 20) Pageable pageable) {
        log.info("Listando turnos do usuário: {}", usuarioId);
        Page<TurnoResponse> response = turnoService.listarTurnosPorUsuario(usuarioId, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar turno por ID", description = "Retorna os dados de um turno específico")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR', 'AUDITOR')")
    public ResponseEntity<TurnoResponse> buscarPorId(@PathVariable String id) {
        log.info("Buscando turno por ID: {}", id);
        TurnoResponse response = turnoService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me/ativo")
    @Operation(summary = "Turno ativo do usuário", description = "Retorna o turno ativo do usuário logado")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
    public ResponseEntity<TurnoResponse> buscarTurnoAtivo(@CurrentUser Usuario usuario) {
        log.info("Buscando turno ativo do usuário: {}", usuario.getEmail());
        TurnoResponse response = turnoService.buscarTurnoAtivoDoUsuario(usuario);
        return ResponseEntity.ok(response);
    }
}
