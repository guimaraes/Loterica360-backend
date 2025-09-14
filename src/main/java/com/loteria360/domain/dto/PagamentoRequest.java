package com.loteria360.domain.dto;

import com.loteria360.domain.model.MetodoPagamento;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PagamentoRequest {
    
    @NotNull(message = "Método de pagamento é obrigatório")
    private MetodoPagamento metodo;
    
    @NotNull(message = "Valor é obrigatório")
    @DecimalMin(value = "0.01", message = "Valor deve ser maior que zero")
    private BigDecimal valor;
    
    @Size(max = 60, message = "NSU deve ter no máximo 60 caracteres")
    private String nsu;
    
    @Size(max = 60, message = "TID deve ter no máximo 60 caracteres")
    private String tid;
    
    @Size(max = 100, message = "Referência deve ter no máximo 100 caracteres")
    private String referencia;
}
