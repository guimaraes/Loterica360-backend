package com.loteria360.domain.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CriarJogoRequest {
    
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    private String nome;
    
    @NotBlank(message = "Código é obrigatório")
    @Size(min = 2, max = 20, message = "Código deve ter entre 2 e 20 caracteres")
    private String codigo;
    
    @NotNull(message = "Preço base é obrigatório")
    @DecimalMin(value = "0.01", message = "Preço base deve ser maior que zero")
    private BigDecimal precoBase;
    
    private String regrasJson;
}
