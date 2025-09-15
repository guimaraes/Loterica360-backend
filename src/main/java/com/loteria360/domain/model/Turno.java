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
@Table(name = "turno")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Turno {

    @Id
    @Column(name = "id", length = 36)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "caixa_id", length = 50, nullable = false)
    private String caixaId;

    @Column(name = "data_abertura", nullable = false)
    private LocalDateTime dataAbertura;

    @Column(name = "data_fechamento")
    private LocalDateTime dataFechamento;

    @Column(name = "valor_inicial", precision = 12, scale = 2, nullable = false)
    @Builder.Default
    private BigDecimal valorInicial = BigDecimal.ZERO;

    @Column(name = "valor_final", precision = 12, scale = 2)
    private BigDecimal valorFinal;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Builder.Default
    private StatusTurno status = StatusTurno.ABERTO;

    @PrePersist
    protected void onCreate() {
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
        if (dataAbertura == null) {
            dataAbertura = LocalDateTime.now();
        }
    }
}
