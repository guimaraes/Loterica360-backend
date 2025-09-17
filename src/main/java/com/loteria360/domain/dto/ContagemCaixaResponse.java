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
public class ContagemCaixaResponse {

    private String id;
    private String caixaId;
    private Integer numeroCaixa;
    private String descricaoCaixa;
    private LocalDate dataContagem;
    private String usuarioId;
    private String nomeUsuario;

    // Notas
    private Integer notas200;
    private Integer notas100;
    private Integer notas50;
    private Integer notas20;
    private Integer notas10;
    private Integer notas5;
    private Integer notas2;

    // Moedas
    private Integer moedas1;
    private Integer moedas050;
    private Integer moedas025;
    private Integer moedas010;
    private Integer moedas005;

    // Totais calculados
    private BigDecimal totalNotas;
    private BigDecimal totalMoedas;
    private BigDecimal totalGeral;

    private LocalDateTime criadoEm;
}
