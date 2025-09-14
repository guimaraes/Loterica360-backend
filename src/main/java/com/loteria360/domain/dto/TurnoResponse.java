package com.loteria360.domain.dto;

import com.loteria360.domain.model.StatusTurno;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TurnoResponse {
    
    private String id;
    private UsuarioResponse usuario;
    private String caixaId;
    private LocalDateTime abertoEm;
    private LocalDateTime fechadoEm;
    private BigDecimal valorInicial;
    private BigDecimal valorFechamento;
    private StatusTurno status;
}
