package com.loteria360.service;

import com.loteria360.domain.dto.CriarUsuarioRequest;
import com.loteria360.domain.dto.UsuarioResponse;
import com.loteria360.domain.model.Usuario;
import com.loteria360.mapper.UsuarioMapper;
import com.loteria360.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;

    public UsuarioResponse criarUsuario(CriarUsuarioRequest request) {
        log.info("Criando usuário com email: {}", request.getEmail());

        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email já está em uso");
        }

        Usuario usuario = usuarioMapper.toEntity(request);
        usuario.setSenhaHash(passwordEncoder.encode(request.getSenha()));
        usuario.setId(UUID.randomUUID().toString());

        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        log.info("Usuário criado com sucesso: {}", usuarioSalvo.getId());

        return usuarioMapper.toResponse(usuarioSalvo);
    }

    @Transactional(readOnly = true)
    public Page<UsuarioResponse> listarUsuarios(Pageable pageable) {
        log.info("Listando usuários");
        return usuarioRepository.findAll(pageable)
                .map(usuarioMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<UsuarioResponse> listarUsuariosAtivos(Pageable pageable) {
        log.info("Listando usuários ativos");
        return usuarioRepository.findByAtivo(true, pageable)
                .map(usuarioMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public UsuarioResponse buscarPorId(String id) {
        log.info("Buscando usuário por ID: {}", id);
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        return usuarioMapper.toResponse(usuario);
    }

    public UsuarioResponse ativarDesativarUsuario(String id) {
        log.info("Ativando/desativando usuário: {}", id);
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        usuario.setAtivo(!usuario.getAtivo());
        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        log.info("Usuário {} {} com sucesso", id, usuarioSalvo.getAtivo() ? "ativado" : "desativado");
        return usuarioMapper.toResponse(usuarioSalvo);
    }
}
