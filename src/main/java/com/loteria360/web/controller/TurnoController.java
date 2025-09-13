package com.loteria360.web.controller;

import com.loteria360.domain.model.Turno;
import com.loteria360.domain.service.TurnoService;
import com.loteria360.security.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/turnos")
@RequiredArgsConstructor
@Tag(name = "Turnos", description = "Gerenciamento de turnos de caixa")
public class TurnoController {
    
    private final TurnoService turnoService;
    
    @PostMapping("/abrir")
    @Operation(summary = "Abrir turno", description = "Abre um novo turno de caixa")
    @PreAuthorize("hasRole('VENDEDOR') or hasRole('GERENTE')")
    public ResponseEntity<Turno> abrirTurno(
            @RequestParam String caixaId,
            @RequestParam BigDecimal valorInicial) {
        Turno turno = turnoService.abrirTurno(CurrentUser.getCurrentUser(), caixaId, valorInicial);
        return ResponseEntity.status(HttpStatus.CREATED).body(turno);
    }
    
    @PostMapping("/{id}/fechar")
    @Operation(summary = "Fechar turno", description = "Fecha um turno e gera resumo X/Z")
    @PreAuthorize("hasRole('VENDEDOR') or hasRole('GERENTE')")
    public ResponseEntity<Map<String, Object>> fecharTurno(@PathVariable String id) {
        Map<String, Object> resumo = turnoService.fecharTurno(java.util.UUID.fromString(id));
        return ResponseEntity.ok(resumo);
    }
}
