package com.loteria360.domain.dto;

import com.loteria360.domain.model.TipoMovimentoCaixa;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class MovimentoCaixaRequest {
    
    @NotNull(message = "Tipo do movimento é obrigatório")
    private TipoMovimentoCaixa tipo;
    
    @NotNull(message = "Valor é obrigatório")
    @DecimalMin(value = "0.01", message = "Valor deve ser maior que zero")
    private BigDecimal valor;
    
    @Size(max = 200, message = "Descrição deve ter no máximo 200 caracteres")
    private String descricao;
}
