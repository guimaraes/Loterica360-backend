package com.loteria360.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
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
    @Column(name = "metodo", nullable = false)
    private MetodoPagamento metodo;

    @Column(name = "valor", precision = 12, scale = 2, nullable = false)
    private BigDecimal valor;

    @Column(name = "nsu", length = 60)
    private String nsu;

    @Column(name = "tid", length = 60)
    private String tid;

    @Column(name = "referencia", length = 100)
    private String referencia;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Builder.Default
    private StatusPagamento status = StatusPagamento.APROVADO;

    @PrePersist
    protected void onCreate() {
        if (id == null) {
            id = UUID.randomUUID().toString();
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
