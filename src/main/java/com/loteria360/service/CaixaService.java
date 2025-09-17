package com.loteria360.service;

import com.loteria360.domain.dto.CaixaRequest;
import com.loteria360.domain.dto.CaixaResponse;
import com.loteria360.domain.model.Caixa;
import com.loteria360.mapper.CaixaMapper;
import com.loteria360.repository.CaixaRepository;
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
public class CaixaService {

    private final CaixaRepository caixaRepository;
    private final CaixaMapper caixaMapper;

    public CaixaResponse criarCaixa(CaixaRequest request) {
        log.info("Criando caixa com número: {}", request.getNumero());

        if (caixaRepository.existsByNumero(request.getNumero())) {
            throw new IllegalArgumentException("Já existe uma caixa com o número " + request.getNumero());
        }

        Caixa caixa = caixaMapper.toEntity(request);
        caixa.setId(UUID.randomUUID().toString());

        Caixa caixaSalva = caixaRepository.save(caixa);
        log.info("Caixa criada com sucesso: {}", caixaSalva.getId());

        return caixaMapper.toResponse(caixaSalva);
    }

    @Transactional(readOnly = true)
    public Page<CaixaResponse> listarCaixas(Pageable pageable) {
        log.info("Listando caixas");
        Page<Caixa> caixas = caixaRepository.findAll(pageable);
        return caixas.map(caixaMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public CaixaResponse buscarPorId(String id) {
        log.info("Buscando caixa por ID: {}", id);
        Caixa caixa = caixaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Caixa não encontrada"));
        return caixaMapper.toResponse(caixa);
    }

    public CaixaResponse atualizarCaixa(String id, CaixaRequest request) {
        log.info("Atualizando caixa: {}", id);

        Caixa caixa = caixaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Caixa não encontrada"));

        if (!caixa.getNumero().equals(request.getNumero())) {
            if (caixaRepository.existsByNumero(request.getNumero())) {
                throw new IllegalArgumentException("Já existe uma caixa com o número " + request.getNumero());
            }
        }

        caixa.setNumero(request.getNumero());
        caixa.setDescricao(request.getDescricao());
        caixa.setAtivo(request.getAtivo());

        Caixa caixaSalva = caixaRepository.save(caixa);
        log.info("Caixa atualizada com sucesso: {}", caixaSalva.getId());

        return caixaMapper.toResponse(caixaSalva);
    }

    @Transactional(readOnly = true)
    public List<CaixaResponse> listarTodasCaixasAtivas() {
        log.info("Listando todas as caixas ativas");
        List<Caixa> caixas = caixaRepository.findAll().stream()
                .filter(Caixa::getAtivo)
                .collect(Collectors.toList());
        return caixas.stream()
                .map(caixaMapper::toResponse)
                .collect(Collectors.toList());
    }
}
