package com.loteria360.unit;

import com.loteria360.domain.dto.LoginRequest;
import com.loteria360.domain.dto.LoginResponse;
import com.loteria360.domain.model.PapelUsuario;
import com.loteria360.domain.model.Usuario;
import com.loteria360.repository.UsuarioRepository;
import com.loteria360.security.JwtService;
import com.loteria360.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    private Usuario usuario;
    private LoginRequest loginRequest;

    @BeforeEach
    void setUp() {
        usuario = Usuario.builder()
                .id("user-id")
                .nome("Admin")
                .email("admin@teste.com")
                .senhaHash("hash")
                .papel(PapelUsuario.ADMIN)
                .ativo(true)
                .build();

        loginRequest = new LoginRequest();
        loginRequest.setEmail("admin@teste.com");
        loginRequest.setSenha("admin");
    }

    @Test
    void deveRealizarLoginComSucesso() {
        // Given
        UserDetails userDetails = mock(UserDetails.class);
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(usuarioRepository.findByEmail("admin@teste.com"))
                .thenReturn(Optional.of(usuario));
        when(jwtService.generateToken(any(UserDetails.class)))
                .thenReturn("jwt-token");

        // When
        LoginResponse response = authService.login(loginRequest);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getToken()).isEqualTo("jwt-token");
        assertThat(response.getTipo()).isEqualTo("Bearer");
        assertThat(response.getUsuario()).isNotNull();
        assertThat(response.getUsuario().getEmail()).isEqualTo("admin@teste.com");
        assertThat(response.getUsuario().getPapel()).isEqualTo(PapelUsuario.ADMIN);

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(usuarioRepository).findByEmail("admin@teste.com");
        verify(jwtService).generateToken(any(UserDetails.class));
    }
}
