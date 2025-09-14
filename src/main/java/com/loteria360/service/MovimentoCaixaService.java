package com.loteria360.service;

import com.loteria360.domain.dto.MovimentoCaixaRequest;
import com.loteria360.domain.dto.MovimentoCaixaResponse;
import com.loteria360.domain.model.*;
import com.loteria360.mapper.MovimentoCaixaMapper;
import com.loteria360.repository.MovimentoCaixaRepository;
import com.loteria360.repository.TurnoRepository;
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
public class MovimentoCaixaService {

    private final MovimentoCaixaRepository movimentoCaixaRepository;
    private final TurnoRepository turnoRepository;
    private final MovimentoCaixaMapper movimentoCaixaMapper;

    public MovimentoCaixaResponse criarMovimento(MovimentoCaixaRequest request, Usuario usuario) {
        log.info("Criando movimento de caixa - tipo: {} - valor: {}", request.getTipo(), request.getValor());

        Turno turnoAtivo = turnoRepository.findByUsuarioIdAndStatus(usuario.getId(), StatusTurno.ABERTO)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não possui turno ativo"));

        MovimentoCaixa movimento = movimentoCaixaMapper.toEntity(request);
        movimento.setId(UUID.randomUUID().toString());
        movimento.setTurno(turnoAtivo);

        MovimentoCaixa movimentoSalvo = movimentoCaixaRepository.save(movimento);
        log.info("Movimento de caixa criado com sucesso: {}", movimentoSalvo.getId());

        return movimentoCaixaMapper.toResponse(movimentoSalvo);
    }

    @Transactional(readOnly = true)
    public Page<MovimentoCaixaResponse> listarMovimentos(Pageable pageable) {
        log.info("Listando movimentos de caixa");
        return movimentoCaixaRepository.findAll(pageable)
                .map(movimentoCaixaMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<MovimentoCaixaResponse> listarMovimentosPorTurno(String turnoId, Pageable pageable) {
        log.info("Listando movimentos do turno: {}", turnoId);
        return movimentoCaixaRepository.findByTurnoId(turnoId, pageable)
                .map(movimentoCaixaMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public MovimentoCaixaResponse buscarPorId(String id) {
        log.info("Buscando movimento por ID: {}", id);
        MovimentoCaixa movimento = movimentoCaixaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Movimento não encontrado"));
        return movimentoCaixaMapper.toResponse(movimento);
    }
}
