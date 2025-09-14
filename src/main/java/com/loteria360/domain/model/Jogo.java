package com.loteria360.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "jogo")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Jogo {

    @Id
    @Column(name = "id", length = 36)
    private String id;

    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @Column(name = "codigo", length = 20, nullable = false, unique = true)
    private String codigo;

    @Column(name = "preco_base", precision = 10, scale = 2, nullable = false)
    private BigDecimal precoBase;

    @Column(name = "regras_json", columnDefinition = "JSON")
    private String regrasJson;

    @Column(name = "ativo", nullable = false)
    @Builder.Default
    private Boolean ativo = true;

    @PrePersist
    protected void onCreate() {
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
    }
}
