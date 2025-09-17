package com.loteria360.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VendaCaixaResponse {

    private String id;
    private String caixaId;
    private Integer numeroCaixa;
    private String descricaoCaixa;
    private String jogoId;
    private String nomeJogo;
    private BigDecimal precoJogo;
    private Integer quantidade;
    private BigDecimal valorTotal;
    private LocalDate dataVenda;
    private String usuarioId;
    private String nomeUsuario;
    private LocalDateTime criadoEm;
}
