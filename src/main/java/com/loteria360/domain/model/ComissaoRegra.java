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
@Table(name = "comissao_regras")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComissaoRegra {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "escopo", nullable = false)
    private Escopo escopo;

    @Column(name = "referencia_id")
    private UUID referenciaId;

    @Column(nullable = false, precision = 5, scale = 4)
    private BigDecimal percentual;

    @Column(name = "vigente_de", nullable = false)
    private LocalDateTime vigenteDe;

    @Column(name = "vigente_ate")
    private LocalDateTime vigenteAte;

    @CreationTimestamp
    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @UpdateTimestamp
    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;

    public enum Escopo {
        JOGO, BOLAO, VENDEDOR
    }
}
