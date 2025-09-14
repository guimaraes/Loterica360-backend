package com.loteria360.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "comissao_regra")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComissaoRegra {

    @Id
    @Column(name = "id", length = 36)
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(name = "escopo", nullable = false)
    private EscopoComissao escopo;

    @Column(name = "referencia_id", length = 36, nullable = false)
    private String referenciaId;

    @Column(name = "percentual", precision = 5, scale = 2, nullable = false)
    private BigDecimal percentual;

    @Column(name = "vigente_de", nullable = false)
    private LocalDate vigenteDe;

    @Column(name = "vigente_ate")
    private LocalDate vigenteAte;

    @PrePersist
    protected void onCreate() {
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
    }

    public boolean isVigente(LocalDate data) {
        return !data.isBefore(vigenteDe) && (vigenteAte == null || !data.isAfter(vigenteAte));
    }

    public boolean isVigente() {
        return isVigente(LocalDate.now());
    }
}
