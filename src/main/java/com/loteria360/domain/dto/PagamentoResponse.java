package com.loteria360.domain.dto;

import com.loteria360.domain.model.Pagamento;
import java.math.BigDecimal;
import java.util.UUID;

public record PagamentoResponse(
        UUID id,
        Pagamento.Metodo metodo,
        BigDecimal valor,
        String nsu,
        String tid,
        String referencia,
        Pagamento.Status status
) {}
