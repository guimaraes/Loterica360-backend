package com.loteria360.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "venda")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Venda {

    @Id
    @Column(name = "id", length = 36)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "turno_id", nullable = false)
    private Turno turno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jogo_id")
    private Jogo jogo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bolao_id")
    private Bolao bolao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_venda", nullable = false)
    private TipoVenda tipoVenda;

    @Column(name = "valor_total", precision = 12, scale = 2, nullable = false)
    private BigDecimal valorTotal;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Builder.Default
    private StatusVenda status = StatusVenda.CONCLUIDA;

    @CreationTimestamp
    @Column(name = "data_venda", nullable = false, updatable = false)
    private LocalDateTime dataVenda;

    @Column(name = "numeros_jogados", length = 255)
    private String numerosJogados;

    @Column(name = "cotas_compradas")
    private Integer cotasCompradas;

    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Pagamento> pagamentos = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
    }

    public void cancelar(String motivo) {
        this.status = StatusVenda.CANCELADA;
    }

    public boolean isConcluida() {
        return status == StatusVenda.CONCLUIDA;
    }

    public boolean isCancelada() {
        return status == StatusVenda.CANCELADA;
    }
}