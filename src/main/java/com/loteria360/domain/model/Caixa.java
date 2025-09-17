package com.loteria360.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "caixa")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Caixa {

    @Id
    @Column(name = "id", length = 36)
    private String id;

    @Column(name = "numero", unique = true, nullable = false)
    private Integer numero;

    @Column(name = "descricao", length = 100)
    private String descricao;

    @Column(name = "ativo", nullable = false)
    @Builder.Default
    private Boolean ativo = true;

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
}
