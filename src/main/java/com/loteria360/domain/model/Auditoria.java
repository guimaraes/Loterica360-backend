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
    @Column(name = "id", length = 36)
    private String id;

    @Column(name = "tabela", length = 50, nullable = false)
    private String tabela;

    @Column(name = "registro_id", length = 36, nullable = false)
    private String registroId;

    @Enumerated(EnumType.STRING)
    @Column(name = "acao", nullable = false)
    private AcaoAuditoria acao;

    @Column(name = "antes_json", columnDefinition = "JSON")
    private String antesJson;

    @Column(name = "depois_json", columnDefinition = "JSON")
    private String depoisJson;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @CreationTimestamp
    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @PrePersist
    protected void onCreate() {
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
    }
}
