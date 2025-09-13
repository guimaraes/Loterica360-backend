package com.loteria360.domain.service;

import com.loteria360.domain.model.Turno;
import com.loteria360.domain.model.Usuario;
import com.loteria360.domain.repository.TurnoRepository;
import com.loteria360.domain.repository.VendaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TurnoService {
    
    private final TurnoRepository turnoRepository;
    private final VendaRepository vendaRepository;
    
    @Transactional
    public Turno abrirTurno(Usuario usuario, String caixaId, BigDecimal valorInicial) {
        // Verificar se já existe turno aberto para o usuário
        if (turnoRepository.findTurnoAbertoByUsuario(usuario.getId()).isPresent()) {
            throw new IllegalStateException("Usuário já possui turno aberto");
        }
        
        // Verificar se já existe turno aberto para o caixa
        if (turnoRepository.findTurnoAbertoByCaixa(caixaId).isPresent()) {
            throw new IllegalStateException("Caixa já possui turno aberto");
        }
        
        Turno turno = Turno.builder()
                .usuario(usuario)
                .caixaId(caixaId)
                .abertoEm(LocalDateTime.now())
                .valorInicial(valorInicial)
                .status(Turno.Status.ABERTO)
                .build();
        
        return turnoRepository.save(turno);
    }
    
    @Transactional
    public Map<String, Object> fecharTurno(UUID turnoId) {
        Turno turno = turnoRepository.findById(turnoId)
                .orElseThrow(() -> new IllegalArgumentException("Turno não encontrado"));
        
        if (turno.getStatus() == Turno.Status.FECHADO) {
            throw new IllegalStateException("Turno já está fechado");
        }
        
        // Calcular totais por método de pagamento
        Map<String, BigDecimal> totaisPorMetodo = calcularTotaisPorMetodo(turnoId);
        BigDecimal totalVendas = totaisPorMetodo.values().stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        // Fechar turno
        turno.setFechadoEm(LocalDateTime.now());
        turno.setValorFechamento(totalVendas);
        turno.setStatus(Turno.Status.FECHADO);
        turnoRepository.save(turno);
        
        // Preparar resumo X/Z
        Map<String, Object> resumo = new HashMap<>();
        resumo.put("turnoId", turnoId);
        resumo.put("caixaId", turno.getCaixaId());
        resumo.put("abertoEm", turno.getAbertoEm());
        resumo.put("fechadoEm", turno.getFechadoEm());
        resumo.put("valorInicial", turno.getValorInicial());
        resumo.put("valorFechamento", turno.getValorFechamento());
        resumo.put("totalVendas", totalVendas);
        resumo.put("totaisPorMetodo", totaisPorMetodo);
        
        return resumo;
    }
    
    private Map<String, BigDecimal> calcularTotaisPorMetodo(UUID turnoId) {
        Map<String, BigDecimal> totais = new HashMap<>();
        
        // DINHEIRO
        BigDecimal totalDinheiro = vendaRepository.getTotalVendasByTurnoAndMetodo(turnoId, "DINHEIRO");
        totais.put("DINHEIRO", totalDinheiro);
        
        // PIX
        BigDecimal totalPix = vendaRepository.getTotalVendasByTurnoAndMetodo(turnoId, "PIX");
        totais.put("PIX", totalPix);
        
        // CARTAO_DEBITO
        BigDecimal totalDebito = vendaRepository.getTotalVendasByTurnoAndMetodo(turnoId, "CARTAO_DEBITO");
        totais.put("CARTAO_DEBITO", totalDebito);
        
        // CARTAO_CREDITO
        BigDecimal totalCredito = vendaRepository.getTotalVendasByTurnoAndMetodo(turnoId, "CARTAO_CREDITO");
        totais.put("CARTAO_CREDITO", totalCredito);
        
        return totais;
    }
}
