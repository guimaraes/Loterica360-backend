package com.loteria360.domain.dto;

import com.loteria360.domain.model.Venda;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record VendaResponse(
        UUID id,
        UUID turnoId,
        UUID usuarioId,
        Venda.Tipo tipo,
        UUID jogoId,
        UUID bolaoId,
        Integer quantidadeApostas,
        Integer cotasVendidas,
        BigDecimal valorBruto,
        BigDecimal desconto,
        BigDecimal acrescimo,
        BigDecimal valorLiquido,
        Venda.Status status,
        String motivoCancelamento,
        List<PagamentoResponse> pagamentos,
        LocalDateTime criadoEm
) {}
