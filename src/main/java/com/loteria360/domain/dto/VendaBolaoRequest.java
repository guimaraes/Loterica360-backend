package com.loteria360.domain.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class VendaBolaoRequest {
    
    @NotBlank(message = "ID do bolão é obrigatório")
    private String bolaoId;
    
    @NotNull(message = "Quantidade de cotas é obrigatória")
    @Positive(message = "Quantidade de cotas deve ser maior que zero")
    private Integer cotas;
    
    @Valid
    @NotEmpty(message = "Pelo menos um pagamento é obrigatório")
    private List<PagamentoRequest> pagamentos;
    
    private BigDecimal desconto;
    private BigDecimal acrescimo;
    private ClienteRequest cliente;
}
