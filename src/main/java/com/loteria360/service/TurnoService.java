package com.loteria360.service;

import com.loteria360.domain.dto.AbrirTurnoRequest;
import com.loteria360.domain.dto.TurnoResponse;
import com.loteria360.domain.model.*;
import com.loteria360.mapper.TurnoMapper;
import com.loteria360.repository.TurnoRepository;
import com.loteria360.repository.VendaRepository;
import com.loteria360.repository.PagamentoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TurnoService {

    private final TurnoRepository turnoRepository;
    private final VendaRepository vendaRepository;
    private final PagamentoRepository pagamentoRepository;
    private final TurnoMapper turnoMapper;

    public TurnoResponse abrirTurno(AbrirTurnoRequest request, Usuario usuario) {
        log.info("Abrindo turno para usuário: {} - caixa: {}", usuario.getEmail(), request.getCaixaId());

        // Verificar se já existe turno aberto para o usuário
        if (turnoRepository.findByUsuarioIdAndStatus(usuario.getId(), StatusTurno.ABERTO).isPresent()) {
            throw new IllegalArgumentException("Usuário já possui um turno aberto");
        }

        // Verificar se já existe turno aberto para o caixa
        if (turnoRepository.findByCaixaIdAndStatus(request.getCaixaId(), StatusTurno.ABERTO).isPresent()) {
            throw new IllegalArgumentException("Caixa já possui um turno aberto");
        }

        Turno turno = turnoMapper.toEntity(request);
        turno.setId(UUID.randomUUID().toString());
        turno.setUsuario(usuario);
        turno.setDataAbertura(LocalDateTime.now());

        Turno turnoSalvo = turnoRepository.save(turno);
        log.info("Turno aberto com sucesso: {}", turnoSalvo.getId());

        return turnoMapper.toResponse(turnoSalvo);
    }

    public TurnoResponse fecharTurno(String id, Usuario usuario) {
        log.info("Fechando turno: {} por usuário: {}", id, usuario.getEmail());

        Turno turno = turnoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Turno não encontrado"));

        if (turno.getStatus() == StatusTurno.FECHADO) {
            throw new IllegalArgumentException("Turno já está fechado");
        }

        if (!turno.getUsuario().getId().equals(usuario.getId())) {
            throw new IllegalArgumentException("Apenas o usuário que abriu o turno pode fechá-lo");
        }

        // Calcular valor de fechamento baseado nas vendas
        List<Venda> vendasAtivas = vendaRepository.findVendasAtivasByTurnoId(id);
        BigDecimal totalVendas = vendasAtivas.stream()
                .map(Venda::getValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Consolidar por método de pagamento
        List<Pagamento> pagamentos = pagamentoRepository.findByVendaTurnoId(id);
        Map<MetodoPagamento, BigDecimal> consolidado = pagamentos.stream()
                .collect(Collectors.groupingBy(
                        Pagamento::getMetodoPagamento,
                        Collectors.reducing(BigDecimal.ZERO, Pagamento::getValor, BigDecimal::add)
                ));

        log.info("Consolidado do turno {} - Total vendas: {}", id, totalVendas);
        consolidado.forEach((metodo, valor) -> 
                log.info("{}: {}", metodo, valor));

        turno.setStatus(StatusTurno.FECHADO);
        turno.setDataFechamento(LocalDateTime.now());
        turno.setValorFinal(totalVendas.add(turno.getValorInicial()));

        Turno turnoSalvo = turnoRepository.save(turno);
        log.info("Turno fechado com sucesso: {}", turnoSalvo.getId());

        return turnoMapper.toResponse(turnoSalvo);
    }

    @Transactional(readOnly = true)
    public Page<TurnoResponse> listarTurnos(Pageable pageable) {
        log.info("Listando turnos");
        return turnoRepository.findAll(pageable)
                .map(turnoMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<TurnoResponse> listarTurnosPorUsuario(String usuarioId, Pageable pageable) {
        log.info("Listando turnos do usuário: {}", usuarioId);
        return turnoRepository.findByUsuarioId(usuarioId, pageable)
                .map(turnoMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public TurnoResponse buscarPorId(String id) {
        log.info("Buscando turno por ID: {}", id);
        Turno turno = turnoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Turno não encontrado"));
        return turnoMapper.toResponse(turno);
    }

    @Transactional(readOnly = true)
    public TurnoResponse buscarTurnoAtivoDoUsuario(Usuario usuario) {
        log.info("Buscando turno ativo do usuário: {}", usuario.getEmail());
        Turno turno = turnoRepository.findByUsuarioIdAndStatus(usuario.getId(), StatusTurno.ABERTO)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não possui turno ativo"));
        return turnoMapper.toResponse(turno);
    }
}
