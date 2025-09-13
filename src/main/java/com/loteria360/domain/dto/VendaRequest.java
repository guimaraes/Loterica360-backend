package com.loteria360.domain.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public record VendaRequest(
        UUID jogoId,
        UUID bolaoId,
        @Positive(message = "Quantidade deve ser positiva")
        Integer quantidade,
        @Positive(message = "Cotas deve ser positiva")
        Integer cotas,
        UUID clienteId,
        @Valid
        @NotNull(message = "Pagamentos são obrigatórios")
        @Size(min = 1, message = "Deve ter pelo menos um pagamento")
        List<PagamentoRequest> pagamentos
) {}
