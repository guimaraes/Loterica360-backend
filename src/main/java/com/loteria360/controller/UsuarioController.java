package com.loteria360.controller;

import com.loteria360.domain.dto.AlterarSenhaRequest;
import com.loteria360.domain.dto.AtualizarUsuarioRequest;
import com.loteria360.domain.dto.CriarUsuarioRequest;
import com.loteria360.domain.dto.UsuarioResponse;
import com.loteria360.domain.model.Usuario;
import com.loteria360.security.CurrentUser;
import com.loteria360.service.UsuarioService;
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
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Usuários", description = "Gerenciamento de usuários do sistema")
@SecurityRequirement(name = "bearerAuth")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    @Operation(summary = "Criar usuário", description = "Cria um novo usuário no sistema")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioResponse> criarUsuario(@Valid @RequestBody CriarUsuarioRequest request) {
        log.info("Criando usuário: {}", request.getEmail());
        UsuarioResponse response = usuarioService.criarUsuario(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar usuário", description = "Atualiza os dados de um usuário existente")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioResponse> atualizarUsuario(
            @PathVariable String id, 
            @Valid @RequestBody AtualizarUsuarioRequest request) {
        log.info("Atualizando usuário: {}", id);
        UsuarioResponse response = usuarioService.atualizarUsuario(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Listar usuários", description = "Lista todos os usuários com paginação")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public ResponseEntity<Page<UsuarioResponse>> listarUsuarios(
            @PageableDefault(size = 20) Pageable pageable) {
        log.info("Listando usuários");
        Page<UsuarioResponse> response = usuarioService.listarUsuarios(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/ativos")
    @Operation(summary = "Listar usuários ativos", description = "Lista apenas usuários ativos")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public ResponseEntity<Page<UsuarioResponse>> listarUsuariosAtivos(
            @PageableDefault(size = 20) Pageable pageable) {
        log.info("Listando usuários ativos");
        Page<UsuarioResponse> response = usuarioService.listarUsuariosAtivos(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID", description = "Retorna os dados de um usuário específico")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public ResponseEntity<UsuarioResponse> buscarPorId(@PathVariable String id) {
        log.info("Buscando usuário por ID: {}", id);
        UsuarioResponse response = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/toggle-status")
    @Operation(summary = "Ativar/Desativar usuário", description = "Alterna o status ativo/inativo de um usuário")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioResponse> ativarDesativarUsuario(@PathVariable String id) {
        log.info("Alterando status do usuário: {}", id);
        UsuarioResponse response = usuarioService.ativarDesativarUsuario(id);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Ativar/Desativar usuário", description = "Alterna o status ativo/inativo de um usuário (endpoint alternativo)")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioResponse> alterarStatusUsuario(@PathVariable String id) {
        log.info("Alterando status do usuário via endpoint /status: {}", id);
        UsuarioResponse response = usuarioService.ativarDesativarUsuario(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    @Operation(summary = "Dados do usuário logado", description = "Retorna os dados do usuário autenticado")
    public ResponseEntity<UsuarioResponse> dadosUsuarioLogado(@CurrentUser Usuario usuario) {
        log.info("Retornando dados do usuário logado: {}", usuario.getEmail());
        UsuarioResponse response = usuarioService.buscarPorId(usuario.getId());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/senha")
    @Operation(summary = "Alterar senha", description = "Altera a senha de um usuário")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> alterarSenha(
            @PathVariable String id, 
            @Valid @RequestBody AlterarSenhaRequest request) {
        log.info("Alterando senha do usuário: {}", id);
        usuarioService.alterarSenha(id, request);
        return ResponseEntity.ok().body("{\"message\": \"Senha alterada com sucesso\"}");
    }

}
