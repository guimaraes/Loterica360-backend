package com.loteria360.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "Dados para abertura de turno")
public class AbrirTurnoRequest {
    
    @NotBlank(message = "ID do caixa é obrigatório")
    @Schema(description = "Identificador do caixa", example = "CAIXA-001")
    private String caixaId;
    
    @NotNull(message = "Valor inicial é obrigatório")
    @DecimalMin(value = "0.00", message = "Valor inicial deve ser maior ou igual a zero")
    @Schema(description = "Valor inicial em dinheiro no caixa", example = "100.00")
    private BigDecimal valorInicial;
}
