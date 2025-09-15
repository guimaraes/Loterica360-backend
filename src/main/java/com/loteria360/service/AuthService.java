package com.loteria360.service;

import com.loteria360.domain.dto.LoginRequest;
import com.loteria360.domain.dto.LoginResponse;
import com.loteria360.domain.model.Usuario;
import com.loteria360.mapper.UsuarioMapper;
import com.loteria360.repository.UsuarioRepository;
import com.loteria360.security.JwtService;
import com.loteria360.security.UsuarioDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UsuarioMapper usuarioMapper;

    public LoginResponse login(LoginRequest request) {
        log.info("Tentativa de login para email: {}", request.getEmail());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getSenha())
        );

        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        String token = jwtService.generateToken((UserDetails) authentication.getPrincipal());

        log.info("Login realizado com sucesso para usuário: {}", usuario.getEmail());

        return LoginResponse.builder()
                .token(token)
                .tipo("Bearer")
                .expiresAt(LocalDateTime.now().plusHours(24))
                .usuario(LoginResponse.UsuarioResponse.builder()
                        .id(usuario.getId())
                        .nome(usuario.getNome())
                        .email(usuario.getEmail())
                        .papel(usuario.getPapel())
                        .ativo(usuario.getAtivo())
                        .criadoEm(usuario.getCriadoEm())
                        .build())
                .build();
    }

    public LoginResponse.UsuarioResponse getCurrentUser() {
        log.info("Obtendo dados do usuário atual");
        
        // Obter o usuário atual do contexto de segurança
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UsuarioDetailsService.UsuarioPrincipal) {
            Usuario usuario = ((UsuarioDetailsService.UsuarioPrincipal) authentication.getPrincipal()).getUsuario();
            
            return LoginResponse.UsuarioResponse.builder()
                    .id(usuario.getId())
                    .nome(usuario.getNome())
                    .email(usuario.getEmail())
                    .papel(usuario.getPapel())
                    .ativo(usuario.getAtivo())
                    .criadoEm(usuario.getCriadoEm())
                    .build();
        }
        
        throw new UsernameNotFoundException("Usuário não autenticado");
    }
}
