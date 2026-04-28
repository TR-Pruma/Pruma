package com.br.pruma.core.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa um pedido de compra roteado pelo MOR para um lojista parceiro.
 * O take rate (taxa da plataforma) é calculado como: valorBruto * fornecedor.taxaComissao.
 */
@Entity
@Table(
        name = "pedido_marketplace",
        indexes = {
                @Index(name = "idx_pedido_mkt_profissional", columnList = "profissional_id"),
                @Index(name = "idx_pedido_mkt_obra",         columnList = "obra_id"),
                @Index(name = "idx_pedido_mkt_fornecedor",   columnList = "fornecedor_id"),
                @Index(name = "idx_pedido_mkt_status",       columnList = "status")
        }
)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class PedidoMarketplace implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pedido_marketplace_id", updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    private Long id;

    @NotNull(message = "Profissional é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "profissional_id", nullable = false)
    private ProfissionalDeBase profissional;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "obra_id")
    private Obra obra;

    @NotNull(message = "Fornecedor parceiro é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fornecedor_id", nullable = false)
    private Fornecedor fornecedor;

    /** GMV da transação — soma dos itens antes de qualquer dedução */
    @NotNull
    @DecimalMin(value = "0.00")
    @Column(name = "valor_bruto", nullable = false, precision = 18, scale = 2)
    private BigDecimal valorBruto;

    /** Taxa retida pela plataforma = valorBruto * fornecedor.taxaComissao */
    @DecimalMin(value = "0.00")
    @Column(name = "taxa_plataforma", precision = 18, scale = 2)
    private BigDecimal taxaPlataforma;

    /** PENDENTE | CONFIRMADO | ENTREGUE | CANCELADO */
    @Column(name = "status", length = 20, nullable = false)
    @Builder.Default
    private String status = "PENDENTE";

    @Column(name = "data_entrega")
    private LocalDate dataEntrega;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ItemPedidoMarketplace> itens = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
