package com.loteria360.web.controller;

import com.loteria360.domain.dto.VendaRequest;
import com.loteria360.domain.dto.VendaResponse;
import com.loteria360.service.VendaService;
import com.loteria360.security.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/vendas")
@RequiredArgsConstructor
@Tag(name = "Vendas", description = "Gerenciamento de vendas")
public class VendaController {
    
    private final VendaService vendaService;
    
    @PostMapping
    @Operation(summary = "Criar venda", description = "Cria uma nova venda (jogo ou bolão)")
    @PreAuthorize("hasRole('VENDEDOR') or hasRole('GERENTE')")
    public ResponseEntity<VendaResponse> criarVenda(@Valid @RequestBody VendaRequest request) {
        VendaResponse response = vendaService.criarVenda(request, CurrentUser.getCurrentUser());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @PostMapping("/{id}/cancelar")
    @Operation(summary = "Cancelar venda", description = "Cancela uma venda existente")
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<VendaResponse> cancelarVenda(
            @PathVariable UUID id,
            @RequestParam String motivo) {
        VendaResponse response = vendaService.cancelarVenda(id, motivo);
        return ResponseEntity.ok(response);
    }
}
