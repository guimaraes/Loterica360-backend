package com.loteria360.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "boloes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bolao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jogo_id", nullable = false)
    private Jogo jogo;

    @Column(nullable = false, length = 20)
    private String concurso;

    @Column(length = 255)
    private String descricao;

    @Column(name = "cotas_totais", nullable = false)
    private Integer cotasTotais;

    @Column(name = "cotas_vendidas", nullable = false)
    @Builder.Default
    private Integer cotasVendidas = 0;

    @Column(name = "valor_cota", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorCota;

    @Column(name = "data_sorteio")
    private LocalDateTime dataSorteio;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private Status status = Status.ABERTO;

    @CreationTimestamp
    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @UpdateTimestamp
    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;

    public enum Status {
        ABERTO, ENCERRADO, CANCELADO
    }

    public boolean temCotasDisponiveis() {
        return cotasVendidas < cotasTotais;
    }

    public Integer getCotasDisponiveis() {
        return cotasTotais - cotasVendidas;
    }
}
