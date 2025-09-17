package com.loteria360.service;

import com.loteria360.domain.dto.CriarJogoRequest;
import com.loteria360.domain.dto.JogoResponse;
import com.loteria360.domain.dto.AtualizarJogoRequest;
import com.loteria360.domain.model.Jogo;
import com.loteria360.mapper.JogoMapper;
import com.loteria360.repository.JogoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class JogoService {

    private final JogoRepository jogoRepository;
    private final JogoMapper jogoMapper;

    public JogoResponse criarJogo(CriarJogoRequest request) {
        log.info("Criando jogo: {}", request.getNome());

        if (jogoRepository.existsByNome(request.getNome())) {
            throw new IllegalArgumentException("Nome do jogo já está em uso");
        }

        Jogo jogo = jogoMapper.toEntity(request);
        jogo.setId(UUID.randomUUID().toString());

        Jogo jogoSalvo = jogoRepository.save(jogo);
        log.info("Jogo criado com sucesso: {}", jogoSalvo.getId());

        return jogoMapper.toResponse(jogoSalvo);
    }

    @Transactional(readOnly = true)
    public Page<JogoResponse> listarJogos(Pageable pageable) {
        log.info("Listando jogos");
        return jogoRepository.findAll(pageable)
                .map(jogoMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<JogoResponse> listarJogosAtivos(Pageable pageable) {
        log.info("Listando jogos ativos");
        return jogoRepository.findByAtivo(true, pageable)
                .map(jogoMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public JogoResponse buscarPorId(String id) {
        log.info("Buscando jogo por ID: {}", id);
        Jogo jogo = jogoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Jogo não encontrado"));
        return jogoMapper.toResponse(jogo);
    }

    @Transactional(readOnly = true)
    public JogoResponse buscarPorNome(String nome) {
        log.info("Buscando jogo por nome: {}", nome);
        Jogo jogo = jogoRepository.findByNome(nome)
                .orElseThrow(() -> new IllegalArgumentException("Jogo não encontrado"));
        return jogoMapper.toResponse(jogo);
    }

    public JogoResponse atualizarJogo(String id, AtualizarJogoRequest request) {
        log.info("Atualizando jogo: {}", id);
        
        Jogo jogo = jogoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Jogo não encontrado"));

        // Verificar se o nome já está em uso por outro jogo
        if (request.getNome() != null && !request.getNome().equals(jogo.getNome())) {
            if (jogoRepository.existsByNome(request.getNome())) {
                throw new IllegalArgumentException("Nome do jogo já está em uso");
            }
            jogo.setNome(request.getNome());
        }

        if (request.getDescricao() != null) {
            jogo.setDescricao(request.getDescricao());
        }

        if (request.getPreco() != null) {
            jogo.setPreco(request.getPreco());
        }

        if (request.getAtivo() != null) {
            jogo.setAtivo(request.getAtivo());
        }

        Jogo jogoSalvo = jogoRepository.save(jogo);
        log.info("Jogo atualizado com sucesso: {}", jogoSalvo.getId());

        return jogoMapper.toResponse(jogoSalvo);
    }

    public JogoResponse ativarDesativarJogo(String id) {
        log.info("Ativando/desativando jogo: {}", id);
        Jogo jogo = jogoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Jogo não encontrado"));

        jogo.setAtivo(!jogo.getAtivo());
        Jogo jogoSalvo = jogoRepository.save(jogo);

        log.info("Jogo {} {} com sucesso", id, jogoSalvo.getAtivo() ? "ativado" : "desativado");
        return jogoMapper.toResponse(jogoSalvo);
    }

    @Transactional(readOnly = true)
    public List<JogoResponse> listarTodosJogosAtivos() {
        log.info("Listando todos os jogos ativos");
        List<Jogo> jogos = jogoRepository.findByAtivoTrue();
        return jogos.stream()
                .map(jogoMapper::toResponse)
                .collect(Collectors.toList());
    }
}
