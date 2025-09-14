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
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoVenda tipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jogo_id")
    private Jogo jogo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bolao_id")
    private Bolao bolao;

    @Column(name = "quantidade_apostas")
    private Integer quantidadeApostas;

    @Column(name = "cotas_vendidas")
    private Integer cotasVendidas;

    @Column(name = "valor_bruto", precision = 12, scale = 2, nullable = false)
    private BigDecimal valorBruto;

    @Column(name = "desconto", precision = 12, scale = 2, nullable = false)
    @Builder.Default
    private BigDecimal desconto = BigDecimal.ZERO;

    @Column(name = "acrescimo", precision = 12, scale = 2, nullable = false)
    @Builder.Default
    private BigDecimal acrescimo = BigDecimal.ZERO;

    @Column(name = "valor_liquido", precision = 12, scale = 2, nullable = false)
    private BigDecimal valorLiquido;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Builder.Default
    private StatusVenda status = StatusVenda.ATIVA;

    @CreationTimestamp
    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @Column(name = "motivo_cancelamento", length = 200)
    private String motivoCancelamento;

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
        this.motivoCancelamento = motivo;
    }

    public BigDecimal calcularValorLiquido() {
        return valorBruto.subtract(desconto).add(acrescimo);
    }

    public boolean isAtiva() {
        return status == StatusVenda.ATIVA;
    }

    public boolean isCancelada() {
        return status == StatusVenda.CANCELADA;
    }
}
