package com.loteria360.service;

import com.loteria360.domain.dto.AlterarSenhaRequest;
import com.loteria360.domain.dto.AtualizarUsuarioRequest;
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

    public UsuarioResponse atualizarUsuario(String id, AtualizarUsuarioRequest request) {
        log.info("Atualizando usuário: {}", id);

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        // Verificar se o email já está em uso por outro usuário (apenas se o email mudou)
        if (!usuario.getEmail().equals(request.getEmail())) {
            if (usuarioRepository.existsByEmail(request.getEmail())) {
                throw new IllegalArgumentException("Email já está em uso por outro usuário");
            }
        }

        // Atualizar os campos
        usuario.setNome(request.getNome());
        usuario.setEmail(request.getEmail());
        usuario.setPapel(request.getPapel());
        usuario.setAtivo(request.getAtivo());

        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        log.info("Usuário atualizado com sucesso: {}", usuarioSalvo.getId());

        return usuarioMapper.toResponse(usuarioSalvo);
    }

    public void alterarSenha(String id, AlterarSenhaRequest request) {
        log.info("Alterando senha do usuário: {}", id);

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        // Verificar se a senha atual está correta
        if (!passwordEncoder.matches(request.getSenhaAtual(), usuario.getSenhaHash())) {
            throw new IllegalArgumentException("Senha atual incorreta");
        }

        // Atualizar com a nova senha
        usuario.setSenhaHash(passwordEncoder.encode(request.getNovaSenha()));
        usuarioRepository.save(usuario);

        log.info("Senha alterada com sucesso para o usuário: {}", id);
    }

}
