package com.loteria360.service;

import com.loteria360.domain.dto.ContagemCaixaRequest;
import com.loteria360.domain.dto.ContagemCaixaResponse;
import com.loteria360.domain.model.Caixa;
import com.loteria360.domain.model.ContagemCaixa;
import com.loteria360.domain.model.Usuario;
import com.loteria360.mapper.ContagemCaixaMapper;
import com.loteria360.repository.CaixaRepository;
import com.loteria360.repository.ContagemCaixaRepository;
import com.loteria360.repository.UsuarioRepository;
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
public class ContagemCaixaService {

    private final ContagemCaixaRepository contagemCaixaRepository;
    private final CaixaRepository caixaRepository;
    private final UsuarioRepository usuarioRepository;
    private final ContagemCaixaMapper contagemCaixaMapper;

    public ContagemCaixaResponse registrarContagem(ContagemCaixaRequest request, Usuario usuario) {
        log.info("Registrando contagem para caixa: {}", request.getCaixaId());

        Caixa caixa = caixaRepository.findById(request.getCaixaId())
                .orElseThrow(() -> new IllegalArgumentException("Caixa não encontrada"));

        LocalDate dataContagem = LocalDate.parse(request.getDataContagem());

        // Verificar se já existe uma contagem para esta caixa e data
        ContagemCaixa contagemExistente = contagemCaixaRepository
                .findByCaixaIdAndDataContagem(request.getCaixaId(), dataContagem)
                .orElse(null);

        ContagemCaixa contagemCaixa;
        if (contagemExistente != null) {
            // Atualizar contagem existente
            contagemCaixa = contagemExistente;
            contagemCaixa.setUsuario(usuario);
            log.info("Atualizando contagem existente: {}", contagemCaixa.getId());
        } else {
            // Criar nova contagem
            contagemCaixa = contagemCaixaMapper.toEntity(request);
            contagemCaixa.setId(UUID.randomUUID().toString());
            contagemCaixa.setCaixa(caixa);
            contagemCaixa.setUsuario(usuario);
            log.info("Criando nova contagem");
        }

        // Atualizar valores
        contagemCaixa.setNotas200(request.getNotas200());
        contagemCaixa.setNotas100(request.getNotas100());
        contagemCaixa.setNotas50(request.getNotas50());
        contagemCaixa.setNotas20(request.getNotas20());
        contagemCaixa.setNotas10(request.getNotas10());
        contagemCaixa.setNotas5(request.getNotas5());
        contagemCaixa.setNotas2(request.getNotas2());
        contagemCaixa.setMoedas1(request.getMoedas1());
        contagemCaixa.setMoedas050(request.getMoedas050());
        contagemCaixa.setMoedas025(request.getMoedas025());
        contagemCaixa.setMoedas010(request.getMoedas010());
        contagemCaixa.setMoedas005(request.getMoedas005());

        // Calcular totais
        contagemCaixa.calcularTotais();

        ContagemCaixa contagemSalva = contagemCaixaRepository.save(contagemCaixa);
        log.info("Contagem registrada com sucesso: {}", contagemSalva.getId());

        return contagemCaixaMapper.toResponse(contagemSalva);
    }

    @Transactional(readOnly = true)
    public Page<ContagemCaixaResponse> listarContagens(Pageable pageable) {
        log.info("Listando contagens");
        Page<ContagemCaixa> contagens = contagemCaixaRepository.findAll(pageable);
        return contagens.map(contagemCaixaMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<ContagemCaixaResponse> listarContagensPorData(LocalDate dataInicio, LocalDate dataFim, Pageable pageable) {
        log.info("Listando contagens entre {} e {}", dataInicio, dataFim);
        Page<ContagemCaixa> contagens = contagemCaixaRepository.findByDataContagemBetween(dataInicio, dataFim, pageable);
        return contagens.map(contagemCaixaMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public ContagemCaixaResponse buscarPorId(String id) {
        log.info("Buscando contagem por ID: {}", id);
        ContagemCaixa contagemCaixa = contagemCaixaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Contagem não encontrada"));
        return contagemCaixaMapper.toResponse(contagemCaixa);
    }

    public void excluirContagem(String id) {
        log.info("Excluindo contagem: {}", id);
        if (!contagemCaixaRepository.existsById(id)) {
            throw new IllegalArgumentException("Contagem não encontrada");
        }
        contagemCaixaRepository.deleteById(id);
        log.info("Contagem excluída com sucesso: {}", id);
    }
}
