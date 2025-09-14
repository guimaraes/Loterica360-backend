package com.loteria360.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AbrirTurnoRequest {
    
    @NotBlank(message = "ID do caixa é obrigatório")
    private String caixaId;
    
    @NotNull(message = "Valor inicial é obrigatório")
    @DecimalMin(value = "0.00", message = "Valor inicial deve ser maior ou igual a zero")
    private BigDecimal valorInicial;
}
