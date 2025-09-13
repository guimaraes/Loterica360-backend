package com.loteria360.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "auditoria")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Auditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 50)
    private String tabela;

    @Column(name = "registro_id", nullable = false)
    private UUID registroId;

    @Enumerated(EnumType.STRING)
    @Column(name = "acao", nullable = false)
    private Acao acao;

    @Column(name = "antes_json", columnDefinition = "TEXT")
    private String antesJson;

    @Column(name = "depois_json", columnDefinition = "TEXT")
    private String depoisJson;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @CreationTimestamp
    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    public enum Acao {
        INSERT, UPDATE, DELETE
    }
}
