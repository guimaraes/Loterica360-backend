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
@Table(name = "bolao", 
       uniqueConstraints = @UniqueConstraint(name = "uk_bolao_jogo_concurso", columnNames = {"jogo_id", "concurso"}))
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bolao {

    @Id
    @Column(name = "id", length = 36)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jogo_id", nullable = false)
    private Jogo jogo;

    @Column(name = "concurso", length = 20, nullable = false)
    private String concurso;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "cotas_totais", nullable = false)
    private Integer cotasTotais;

    @Column(name = "cotas_vendidas", nullable = false)
    @Builder.Default
    private Integer cotasVendidas = 0;

    @Column(name = "cotas_disponiveis", nullable = false)
    private Integer cotasDisponiveis;

    @Column(name = "valor_cota", precision = 10, scale = 2, nullable = false)
    private BigDecimal valorCota;

    @Column(name = "data_sorteio", nullable = false)
    private LocalDate dataSorteio;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Builder.Default
    private StatusBolao status = StatusBolao.ABERTO;

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
        if (cotasDisponiveis == null) {
            cotasDisponiveis = cotasTotais - cotasVendidas;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        if (cotasDisponiveis == null) {
            cotasDisponiveis = cotasTotais - cotasVendidas;
        }
    }
}
