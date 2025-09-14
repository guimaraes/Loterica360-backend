package com.loteria360.security;

import com.loteria360.domain.model.PapelUsuario;
import com.loteria360.domain.model.Usuario;
import com.loteria360.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UsuarioDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com email: " + email));

        return new UsuarioPrincipal(usuario);
    }

    public static class UsuarioPrincipal implements UserDetails {
        private final Usuario usuario;

        public UsuarioPrincipal(Usuario usuario) {
            this.usuario = usuario;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + usuario.getPapel().name()));
        }

        @Override
        public String getPassword() {
            return usuario.getSenhaHash();
        }

        @Override
        public String getUsername() {
            return usuario.getEmail();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return usuario.getAtivo();
        }

        public Usuario getUsuario() {
            return usuario;
        }

        public PapelUsuario getPapel() {
            return usuario.getPapel();
        }
    }
}
