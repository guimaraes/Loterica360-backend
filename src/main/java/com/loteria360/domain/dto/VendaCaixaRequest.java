package com.loteria360.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class VendaCaixaRequest {

    @NotNull(message = "ID da caixa é obrigatório")
    private String caixaId;

    @NotNull(message = "ID do jogo é obrigatório")
    private String jogoId;

    @NotNull(message = "Quantidade é obrigatória")
    @Min(value = 0, message = "Quantidade deve ser maior ou igual a zero")
    private Integer quantidade;

    @NotNull(message = "Data da venda é obrigatória")
    private String dataVenda; // Formato: YYYY-MM-DD
}
