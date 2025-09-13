package com.loteria360.service;

import com.loteria360.domain.dto.UsuarioRequest;
import com.loteria360.domain.dto.UsuarioResponse;
import com.loteria360.domain.model.Usuario;
import com.loteria360.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UsuarioService {
    
    private final UsuarioRepository usuarioRepository;
    
    @Transactional
    public UsuarioResponse criarUsuario(UsuarioRequest request, String senhaHash) {
        if (usuarioRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email já cadastrado");
        }
        
        Usuario usuario = Usuario.builder()
                .nome(request.nome())
                .email(request.email())
                .senhaHash(senhaHash)
                .papel(request.papel())
                .ativo(true)
                .build();
        
        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        return toResponse(usuarioSalvo);
    }
    
    public List<UsuarioResponse> listarUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }
    
    public UsuarioResponse buscarPorId(UUID id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        return toResponse(usuario);
    }
    
    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
    }
    
    private UsuarioResponse toResponse(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getPapel(),
                usuario.getAtivo(),
                usuario.getCriadoEm()
        );
    }
}
