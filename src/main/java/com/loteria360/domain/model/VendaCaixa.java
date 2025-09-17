package com.loteria360.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "venda_caixa", 
       uniqueConstraints = @UniqueConstraint(columnNames = {"caixa_id", "jogo_id", "data_venda"}))
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VendaCaixa {

    @Id
    @Column(name = "id", length = 36)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caixa_id", nullable = false)
    private Caixa caixa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jogo_id", nullable = false)
    private Jogo jogo;

    @Column(name = "quantidade", nullable = false)
    @Builder.Default
    private Integer quantidade = 0;

    @Column(name = "valor_total", precision = 12, scale = 2, nullable = false)
    @Builder.Default
    private BigDecimal valorTotal = BigDecimal.ZERO;

    @Column(name = "data_venda", nullable = false)
    private LocalDate dataVenda;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "criado_em", nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime criadoEm = LocalDateTime.now();

    @PrePersist
    protected void onCreate() {
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
        if (criadoEm == null) {
            criadoEm = LocalDateTime.now();
        }
    }

    // Método para calcular o valor total baseado na quantidade e preço do jogo
    public void calcularValorTotal() {
        if (jogo != null && quantidade != null) {
            this.valorTotal = jogo.getPreco().multiply(BigDecimal.valueOf(quantidade));
        }
    }
}
