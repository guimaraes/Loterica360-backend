package com.loteria360.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "pagamento")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pagamento {

    @Id
    @Column(name = "id", length = 36)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venda_id", nullable = false)
    private Venda venda;

    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pagamento", nullable = false)
    private MetodoPagamento metodoPagamento;

    @Column(name = "valor", precision = 12, scale = 2, nullable = false)
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Builder.Default
    private StatusPagamento status = StatusPagamento.APROVADO;

    @Column(name = "data_pagamento", nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime dataPagamento = LocalDateTime.now();

    @PrePersist
    protected void onCreate() {
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
        if (dataPagamento == null) {
            dataPagamento = LocalDateTime.now();
        }
    }

    public boolean isAprovado() {
        return status == StatusPagamento.APROVADO;
    }

    public boolean isPendente() {
        return status == StatusPagamento.PENDENTE;
    }

    public boolean isEstornado() {
        return status == StatusPagamento.ESTORNADO;
    }

    public void estornar() {
        this.status = StatusPagamento.ESTORNADO;
    }
}
