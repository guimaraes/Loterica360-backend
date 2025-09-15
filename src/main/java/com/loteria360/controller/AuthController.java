package com.loteria360.controller;

import com.loteria360.domain.dto.LoginRequest;
import com.loteria360.domain.dto.LoginResponse;
import com.loteria360.domain.model.Usuario;
import com.loteria360.security.CurrentUser;
import com.loteria360.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Autenticação", description = "Endpoints para autenticação e autorização")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Realizar login", description = "Autentica um usuário e retorna um token JWT")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        log.info("Tentativa de login para email: {}", request.getEmail());
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    @Operation(summary = "Obter usuário atual", description = "Retorna os dados do usuário autenticado")
    public ResponseEntity<LoginResponse.UsuarioResponse> getCurrentUser() {
        log.info("Obtendo dados do usuário atual");
        LoginResponse.UsuarioResponse user = authService.getCurrentUser();
        return ResponseEntity.ok(user);
    }

    @PostMapping("/logout")
    @Operation(summary = "Realizar logout", description = "Realiza logout do usuário autenticado")
    public ResponseEntity<?> logout() {
        log.info("Logout realizado com sucesso");
        // Para JWT, o logout é principalmente do lado do cliente (remover o token)
        // Aqui podemos adicionar lógica adicional se necessário (blacklist de tokens, etc.)
        return ResponseEntity.ok().body("{\"message\": \"Logout realizado com sucesso\"}");
    }
}
