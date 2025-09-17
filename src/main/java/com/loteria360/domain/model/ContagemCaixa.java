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
@Table(name = "contagem_caixa", 
       uniqueConstraints = @UniqueConstraint(columnNames = {"caixa_id", "data_contagem"}))
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContagemCaixa {

    @Id
    @Column(name = "id", length = 36)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caixa_id", nullable = false)
    private Caixa caixa;

    @Column(name = "data_contagem", nullable = false)
    private LocalDate dataContagem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // Notas
    @Column(name = "notas_200")
    @Builder.Default
    private Integer notas200 = 0;

    @Column(name = "notas_100")
    @Builder.Default
    private Integer notas100 = 0;

    @Column(name = "notas_50")
    @Builder.Default
    private Integer notas50 = 0;

    @Column(name = "notas_20")
    @Builder.Default
    private Integer notas20 = 0;

    @Column(name = "notas_10")
    @Builder.Default
    private Integer notas10 = 0;

    @Column(name = "notas_5")
    @Builder.Default
    private Integer notas5 = 0;

    @Column(name = "notas_2")
    @Builder.Default
    private Integer notas2 = 0;

    // Moedas
    @Column(name = "moedas_1")
    @Builder.Default
    private Integer moedas1 = 0;

    @Column(name = "moedas_050")
    @Builder.Default
    private Integer moedas050 = 0;

    @Column(name = "moedas_025")
    @Builder.Default
    private Integer moedas025 = 0;

    @Column(name = "moedas_010")
    @Builder.Default
    private Integer moedas010 = 0;

    @Column(name = "moedas_005")
    @Builder.Default
    private Integer moedas005 = 0;

    // Totais calculados
    @Column(name = "total_notas", precision = 12, scale = 2)
    @Builder.Default
    private BigDecimal totalNotas = BigDecimal.ZERO;

    @Column(name = "total_moedas", precision = 12, scale = 2)
    @Builder.Default
    private BigDecimal totalMoedas = BigDecimal.ZERO;

    @Column(name = "total_geral", precision = 12, scale = 2)
    @Builder.Default
    private BigDecimal totalGeral = BigDecimal.ZERO;

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
        calcularTotais();
    }

    @PreUpdate
    protected void onUpdate() {
        calcularTotais();
    }

    // Método para calcular os totais
    public void calcularTotais() {
        // Calcular total das notas
        BigDecimal totalNotas = BigDecimal.ZERO;
        totalNotas = totalNotas.add(BigDecimal.valueOf(notas200).multiply(BigDecimal.valueOf(200)));
        totalNotas = totalNotas.add(BigDecimal.valueOf(notas100).multiply(BigDecimal.valueOf(100)));
        totalNotas = totalNotas.add(BigDecimal.valueOf(notas50).multiply(BigDecimal.valueOf(50)));
        totalNotas = totalNotas.add(BigDecimal.valueOf(notas20).multiply(BigDecimal.valueOf(20)));
        totalNotas = totalNotas.add(BigDecimal.valueOf(notas10).multiply(BigDecimal.valueOf(10)));
        totalNotas = totalNotas.add(BigDecimal.valueOf(notas5).multiply(BigDecimal.valueOf(5)));
        totalNotas = totalNotas.add(BigDecimal.valueOf(notas2).multiply(BigDecimal.valueOf(2)));

        // Calcular total das moedas
        BigDecimal totalMoedas = BigDecimal.ZERO;
        totalMoedas = totalMoedas.add(BigDecimal.valueOf(moedas1).multiply(BigDecimal.valueOf(1)));
        totalMoedas = totalMoedas.add(BigDecimal.valueOf(moedas050).multiply(BigDecimal.valueOf(0.50)));
        totalMoedas = totalMoedas.add(BigDecimal.valueOf(moedas025).multiply(BigDecimal.valueOf(0.25)));
        totalMoedas = totalMoedas.add(BigDecimal.valueOf(moedas010).multiply(BigDecimal.valueOf(0.10)));
        totalMoedas = totalMoedas.add(BigDecimal.valueOf(moedas005).multiply(BigDecimal.valueOf(0.05)));

        this.totalNotas = totalNotas;
        this.totalMoedas = totalMoedas;
        this.totalGeral = totalNotas.add(totalMoedas);
    }
}
