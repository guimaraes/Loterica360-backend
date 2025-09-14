package com.loteria360.service;

import com.loteria360.domain.dto.BolaoResponse;
import com.loteria360.domain.dto.CriarBolaoRequest;
import com.loteria360.domain.model.Bolao;
import com.loteria360.domain.model.Jogo;
import com.loteria360.domain.model.StatusBolao;
import com.loteria360.mapper.BolaoMapper;
import com.loteria360.repository.BolaoRepository;
import com.loteria360.repository.JogoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BolaoService {

    private final BolaoRepository bolaoRepository;
    private final JogoRepository jogoRepository;
    private final BolaoMapper bolaoMapper;

    public BolaoResponse criarBolao(CriarBolaoRequest request) {
        log.info("Criando bolão para jogo: {}", request.getJogoId());

        Jogo jogo = jogoRepository.findById(request.getJogoId())
                .orElseThrow(() -> new IllegalArgumentException("Jogo não encontrado"));

        if (!jogo.getAtivo()) {
            throw new IllegalArgumentException("Não é possível criar bolão para jogo inativo");
        }

        if (bolaoRepository.findByJogoIdAndConcurso(request.getJogoId(), request.getConcurso()).isPresent()) {
            throw new IllegalArgumentException("Já existe um bolão para este jogo e concurso");
        }

        if (request.getDataSorteio().isBefore(LocalDate.now().plusDays(1))) {
            throw new IllegalArgumentException("Data do sorteio deve ser pelo menos amanhã");
        }

        Bolao bolao = bolaoMapper.toEntity(request);
        bolao.setId(UUID.randomUUID().toString());

        Bolao bolaoSalvo = bolaoRepository.save(bolao);
        log.info("Bolão criado com sucesso: {}", bolaoSalvo.getId());

        return bolaoMapper.toResponse(bolaoSalvo);
    }

    @Transactional(readOnly = true)
    public Page<BolaoResponse> listarBoloes(Pageable pageable) {
        log.info("Listando bolões");
        return bolaoRepository.findAll(pageable)
                .map(bolaoMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<BolaoResponse> listarBoloesAbertos(Pageable pageable) {
        log.info("Listando bolões abertos");
        return bolaoRepository.findByStatus(StatusBolao.ABERTO, pageable)
                .map(bolaoMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public BolaoResponse buscarPorId(String id) {
        log.info("Buscando bolão por ID: {}", id);
        Bolao bolao = bolaoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Bolão não encontrado"));
        return bolaoMapper.toResponse(bolao);
    }

    public BolaoResponse encerrarBolao(String id) {
        log.info("Encerrando bolão: {}", id);
        Bolao bolao = bolaoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Bolão não encontrado"));

        if (bolao.getStatus() != StatusBolao.ABERTO) {
            throw new IllegalArgumentException("Apenas bolões abertos podem ser encerrados");
        }

        bolao.setStatus(StatusBolao.ENCERRADO);
        Bolao bolaoSalvo = bolaoRepository.save(bolao);

        log.info("Bolão {} encerrado com sucesso", id);
        return bolaoMapper.toResponse(bolaoSalvo);
    }

    public BolaoResponse cancelarBolao(String id) {
        log.info("Cancelando bolão: {}", id);
        Bolao bolao = bolaoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Bolão não encontrado"));

        if (bolao.getStatus() != StatusBolao.ABERTO) {
            throw new IllegalArgumentException("Apenas bolões abertos podem ser cancelados");
        }

        bolao.setStatus(StatusBolao.CANCELADO);
        Bolao bolaoSalvo = bolaoRepository.save(bolao);

        log.info("Bolão {} cancelado com sucesso", id);
        return bolaoMapper.toResponse(bolaoSalvo);
    }

    @Transactional(readOnly = true)
    public Page<BolaoResponse> listarBoloesPorJogo(String jogoId, Pageable pageable) {
        log.info("Listando bolões por jogo: {}", jogoId);
        return bolaoRepository.findByJogoIdAndStatus(jogoId, StatusBolao.ABERTO, pageable)
                .map(bolaoMapper::toResponse);
    }
}
