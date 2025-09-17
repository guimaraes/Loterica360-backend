package com.loteria360.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CaixaRequest {

    @NotNull(message = "Número da caixa é obrigatório")
    @Min(value = 1, message = "Número da caixa deve ser maior que zero")
    private Integer numero;

    @Size(max = 100, message = "Descrição deve ter no máximo 100 caracteres")
    private String descricao;

    @NotNull(message = "Status ativo é obrigatório")
    private Boolean ativo;
}
