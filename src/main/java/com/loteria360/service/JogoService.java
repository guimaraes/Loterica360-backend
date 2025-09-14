package com.loteria360.service;

import com.loteria360.domain.dto.CriarJogoRequest;
import com.loteria360.domain.dto.JogoResponse;
import com.loteria360.domain.model.Jogo;
import com.loteria360.mapper.JogoMapper;
import com.loteria360.repository.JogoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class JogoService {

    private final JogoRepository jogoRepository;
    private final JogoMapper jogoMapper;

    public JogoResponse criarJogo(CriarJogoRequest request) {
        log.info("Criando jogo: {}", request.getNome());

        if (jogoRepository.existsByCodigo(request.getCodigo())) {
            throw new IllegalArgumentException("Código do jogo já está em uso");
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
    public JogoResponse buscarPorCodigo(String codigo) {
        log.info("Buscando jogo por código: {}", codigo);
        Jogo jogo = jogoRepository.findByCodigo(codigo)
                .orElseThrow(() -> new IllegalArgumentException("Jogo não encontrado"));
        return jogoMapper.toResponse(jogo);
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
}
