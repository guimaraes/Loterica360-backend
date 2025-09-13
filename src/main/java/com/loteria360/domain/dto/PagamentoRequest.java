package com.loteria360.domain.dto;

import com.loteria360.domain.model.Pagamento;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record PagamentoRequest(
        @NotNull(message = "Método de pagamento é obrigatório")
        Pagamento.Metodo metodo,
        @NotNull(message = "Valor é obrigatório")
        @Positive(message = "Valor deve ser positivo")
        BigDecimal valor,
        String nsu,
        String tid,
        String referencia
) {}
