package com.loteria360.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CancelarVendaRequest {
    
    @NotBlank(message = "Motivo do cancelamento é obrigatório")
    @Size(min = 10, max = 200, message = "Motivo deve ter entre 10 e 200 caracteres")
    private String motivoCancelamento;
}
