package com.loteria360.service;

import com.loteria360.domain.dto.BolaoResponse;
import com.loteria360.domain.dto.CriarBolaoRequest;
import com.loteria360.domain.dto.AtualizarBolaoRequest;
import com.loteria360.domain.mapper.BolaoMapper;
import com.loteria360.domain.model.Bolao;
import com.loteria360.domain.model.Jogo;
import com.loteria360.domain.model.StatusBolao;
import com.loteria360.repository.BolaoRepository;
import com.loteria360.repository.JogoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BolaoService {

    private final BolaoRepository bolaoRepository;
    private final JogoRepository jogoRepository;
    private final BolaoMapper bolaoMapper;

    public Page<BolaoResponse> listarBoloes(Pageable pageable, String search) {
        log.info("Listando bolões com paginação: page={}, size={}, search={}", 
                pageable.getPageNumber(), pageable.getPageSize(), search);
        
        Page<Bolao> boloes;
        if (search != null && !search.trim().isEmpty()) {
            boloes = bolaoRepository.findBySearchTerm(search.trim(), pageable);
        } else {
            boloes = bolaoRepository.findAll(pageable);
        }
        
        return boloes.map(bolaoMapper::toResponse);
    }

    public List<BolaoResponse> listarTodosBoloesAtivos() {
        log.info("Listando todos os bolões ativos");
        List<Bolao> boloes = bolaoRepository.findBoloesAtivos();
        return boloes.stream()
                .map(bolaoMapper::toResponse)
                .toList();
    }

    public BolaoResponse buscarBolaoPorId(String id) {
        log.info("Buscando bolão por ID: {}", id);
        Bolao bolao = bolaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bolão não encontrado"));
        return bolaoMapper.toResponse(bolao);
    }

    public BolaoResponse criarBolao(CriarBolaoRequest request) {
        log.info("Criando novo bolão para jogo: {}, concurso: {}", request.getJogoId(), request.getConcurso());
        
        // Verificar se o jogo existe
        Jogo jogo = jogoRepository.findById(request.getJogoId())
                .orElseThrow(() -> new RuntimeException("Jogo não encontrado"));
        
        // Verificar se já existe bolão para este jogo e concurso
        bolaoRepository.findByJogoIdAndConcurso(request.getJogoId(), request.getConcurso())
                .ifPresent(bolao -> {
                    throw new RuntimeException("Já existe um bolão para este jogo e concurso");
                });
        
        // Verificar se a data do sorteio é futura
        if (request.getDataSorteio().isBefore(LocalDate.now())) {
            throw new RuntimeException("Data do sorteio deve ser futura");
        }
        
        Bolao bolao = Bolao.builder()
                .id(UUID.randomUUID().toString())
                .jogo(jogo)
                .concurso(request.getConcurso())
                .descricao(request.getDescricao())
                .cotasTotais(request.getCotasTotais())
                .cotasVendidas(0)
                .cotasDisponiveis(request.getCotasTotais())
                .valorCota(request.getValorCota())
                .dataSorteio(request.getDataSorteio())
                .status(StatusBolao.ABERTO)
                .build();
        
        bolao = bolaoRepository.save(bolao);
        log.info("Bolão criado com sucesso: {}", bolao.getId());
        
        return bolaoMapper.toResponse(bolao);
    }

    public BolaoResponse atualizarBolao(String id, AtualizarBolaoRequest request) {
        log.info("Atualizando bolão: {}", id);
        
        Bolao bolao = bolaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bolão não encontrado"));
        
        // Verificar se o jogo existe
        Jogo jogo = jogoRepository.findById(request.getJogoId())
                .orElseThrow(() -> new RuntimeException("Jogo não encontrado"));
        
        // Verificar se já existe outro bolão para este jogo e concurso (excluindo o atual)
        bolaoRepository.findByJogoIdAndConcurso(request.getJogoId(), request.getConcurso())
                .ifPresent(existingBolao -> {
                    if (!existingBolao.getId().equals(id)) {
                        throw new RuntimeException("Já existe um bolão para este jogo e concurso");
                    }
                });
        
        // Verificar se a data do sorteio é futura
        if (request.getDataSorteio().isBefore(LocalDate.now())) {
            throw new RuntimeException("Data do sorteio deve ser futura");
        }
        
        bolao.setJogo(jogo);
        bolao.setConcurso(request.getConcurso());
        bolao.setDescricao(request.getDescricao());
        bolao.setCotasTotais(request.getCotasTotais());
        bolao.setCotasDisponiveis(request.getCotasTotais() - bolao.getCotasVendidas());
        bolao.setValorCota(request.getValorCota());
        bolao.setDataSorteio(request.getDataSorteio());
        
        bolao = bolaoRepository.save(bolao);
        log.info("Bolão atualizado com sucesso: {}", bolao.getId());
        
        return bolaoMapper.toResponse(bolao);
    }

    public BolaoResponse alterarStatusBolao(String id) {
        log.info("Alterando status do bolão: {}", id);
        
        Bolao bolao = bolaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bolão não encontrado"));
        
        StatusBolao novoStatus;
        if (bolao.getStatus() == StatusBolao.ABERTO) {
            novoStatus = StatusBolao.ENCERRADO;
        } else {
            novoStatus = StatusBolao.ABERTO;
        }
        
        bolao.setStatus(novoStatus);
        bolao = bolaoRepository.save(bolao);
        
        log.info("Status do bolão alterado para: {}", novoStatus);
        return bolaoMapper.toResponse(bolao);
    }

    public void deletarBolao(String id) {
        log.info("Deletando bolão: {}", id);
        
        Bolao bolao = bolaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bolão não encontrado"));
        
        // Verificar se o bolão tem vendas
        if (bolao.getCotasVendidas() > 0) {
            throw new RuntimeException("Não é possível deletar um bolão com vendas realizadas");
        }
        
        bolaoRepository.delete(bolao);
        log.info("Bolão deletado com sucesso: {}", id);
    }
}
