package com.loteria360.domain.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CriarBolaoRequest {
    
    @NotBlank(message = "ID do jogo é obrigatório")
    private String jogoId;
    
    @NotBlank(message = "Concurso é obrigatório")
    @Size(max = 20, message = "Concurso deve ter no máximo 20 caracteres")
    private String concurso;
    
    @Size(max = 500, message = "Descrição deve ter no máximo 500 caracteres")
    private String descricao;
    
    @NotNull(message = "Total de cotas é obrigatório")
    @Min(value = 1, message = "Total de cotas deve ser pelo menos 1")
    private Integer cotasTotais;
    
    @NotNull(message = "Valor da cota é obrigatório")
    @DecimalMin(value = "0.01", message = "Valor da cota deve ser maior que zero")
    private BigDecimal valorCota;
    
    @NotNull(message = "Data do sorteio é obrigatória")
    @Future(message = "Data do sorteio deve ser futura")
    private LocalDate dataSorteio;
}
