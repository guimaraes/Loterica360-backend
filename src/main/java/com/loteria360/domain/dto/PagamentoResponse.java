package com.loteria360.domain.dto;

import com.loteria360.domain.model.MetodoPagamento;
import com.loteria360.domain.model.StatusPagamento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagamentoResponse {
    
    private String id;
    private MetodoPagamento metodo;
    private BigDecimal valor;
    private String nsu;
    private String tid;
    private String referencia;
    private StatusPagamento status;
}
