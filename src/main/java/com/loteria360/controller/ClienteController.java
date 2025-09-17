package com.loteria360.controller;

import com.loteria360.domain.dto.ClienteRequest;
import com.loteria360.domain.dto.ClienteResponse;
import com.loteria360.service.ClienteService;
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
@RequestMapping("/api/v1/clientes")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Clientes", description = "Gerenciamento de clientes do sistema")
@SecurityRequirement(name = "bearerAuth")
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping
    @Operation(summary = "Criar cliente", description = "Cria um novo cliente no sistema")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
    public ResponseEntity<ClienteResponse> criarCliente(@Valid @RequestBody ClienteRequest request) {
        log.info("Criando cliente: {}", request.getNome());
        ClienteResponse response = clienteService.criarCliente(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar clientes", description = "Lista todos os clientes com paginação")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
    public ResponseEntity<Page<ClienteResponse>> listarClientes(
            @PageableDefault(size = 20) Pageable pageable,
            @RequestParam(required = false) String search) {
        log.info("Listando clientes");
        Page<ClienteResponse> response = clienteService.listarClientes(pageable, search);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar cliente por ID", description = "Retorna os dados de um cliente específico")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
    public ResponseEntity<ClienteResponse> buscarPorId(@PathVariable String id) {
        log.info("Buscando cliente por ID: {}", id);
        ClienteResponse response = clienteService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar cliente", description = "Atualiza os dados de um cliente existente")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
    public ResponseEntity<ClienteResponse> atualizarCliente(
            @PathVariable String id,
            @Valid @RequestBody ClienteRequest request) {
        log.info("Atualizando cliente: {}", id);
        ClienteResponse response = clienteService.atualizarCliente(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    @Operation(summary = "Buscar clientes", description = "Busca clientes por nome, CPF ou email")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
    public ResponseEntity<Page<ClienteResponse>> buscarClientes(
            @RequestParam String q,
            @PageableDefault(size = 20) Pageable pageable) {
        log.info("Buscando clientes com termo: {}", q);
        Page<ClienteResponse> response = clienteService.buscarClientes(q, pageable);
        return ResponseEntity.ok(response);
    }
}
