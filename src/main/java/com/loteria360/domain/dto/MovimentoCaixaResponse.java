package com.loteria360.domain.dto;

import com.loteria360.domain.model.TipoMovimentoCaixa;
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
public class MovimentoCaixaResponse {
    
    private String id;
    private String turnoId;
    private TipoMovimentoCaixa tipo;
    private BigDecimal valor;
    private String descricao;
    private LocalDateTime criadoEm;
}
