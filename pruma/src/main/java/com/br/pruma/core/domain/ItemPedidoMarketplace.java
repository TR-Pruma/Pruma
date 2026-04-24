package com.br.pruma.core.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/** Item de linha de um PedidoMarketplace — equivalente a ItemOrcamento no contexto B2B. */
@Entity
@Table(
        name = "item_pedido_marketplace",
        indexes = {
                @Index(name = "idx_item_pedido_mkt_pedido", columnList = "pedido_marketplace_id"),
                @Index(name = "idx_item_pedido_mkt_insumo", columnList = "insumo_id")
        }
)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class ItemPedidoMarketplace implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_pedido_marketplace_id", updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pedido_marketplace_id", nullable = false)
    private PedidoMarketplace pedido;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "insumo_id", nullable = false)
    private Insumo insumo;

    @NotNull
    @Min(value = 1, message = "Quantidade mínima é 1")
    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;

    @NotNull
    @DecimalMin(value = "0.00")
    @Column(name = "preco_unitario", nullable = false, precision = 18, scale = 2)
    private BigDecimal precoUnitario;
}
