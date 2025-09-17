package com.loteria360.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContagemCaixaRequest {

    @NotNull(message = "ID da caixa é obrigatório")
    private String caixaId;

    @NotNull(message = "Data da contagem é obrigatória")
    private String dataContagem; // Formato: YYYY-MM-DD

    // Notas
    @Min(value = 0, message = "Quantidade de notas de R$ 200 deve ser maior ou igual a zero")
    private Integer notas200 = 0;

    @Min(value = 0, message = "Quantidade de notas de R$ 100 deve ser maior ou igual a zero")
    private Integer notas100 = 0;

    @Min(value = 0, message = "Quantidade de notas de R$ 50 deve ser maior ou igual a zero")
    private Integer notas50 = 0;

    @Min(value = 0, message = "Quantidade de notas de R$ 20 deve ser maior ou igual a zero")
    private Integer notas20 = 0;

    @Min(value = 0, message = "Quantidade de notas de R$ 10 deve ser maior ou igual a zero")
    private Integer notas10 = 0;

    @Min(value = 0, message = "Quantidade de notas de R$ 5 deve ser maior ou igual a zero")
    private Integer notas5 = 0;

    @Min(value = 0, message = "Quantidade de notas de R$ 2 deve ser maior ou igual a zero")
    private Integer notas2 = 0;

    // Moedas
    @Min(value = 0, message = "Quantidade de moedas de R$ 1,00 deve ser maior ou igual a zero")
    private Integer moedas1 = 0;

    @Min(value = 0, message = "Quantidade de moedas de R$ 0,50 deve ser maior ou igual a zero")
    private Integer moedas050 = 0;

    @Min(value = 0, message = "Quantidade de moedas de R$ 0,25 deve ser maior ou igual a zero")
    private Integer moedas025 = 0;

    @Min(value = 0, message = "Quantidade de moedas de R$ 0,10 deve ser maior ou igual a zero")
    private Integer moedas010 = 0;

    @Min(value = 0, message = "Quantidade de moedas de R$ 0,05 deve ser maior ou igual a zero")
    private Integer moedas005 = 0;
}
