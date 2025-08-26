package com.br.pruma.core.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(
        name = "insumo_fornecedor",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_insumo_fornecedor",
                columnNames = {"insumo_id", "fornecedor_id"}
        )
)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class InsumoFornecedor {

    @EmbeddedId
    @EqualsAndHashCode.Include
    @ToString.Include
    private InsumoFornecedorAux id;

    @MapsId("insumoId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "insumo_id", nullable = false)
    @ToString.Include(name = "insumo")
    private Insumo insumo;

    @MapsId("fornecedorId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fornecedor_id", nullable = false)
    @ToString.Include(name = "fornecedor")
    private Fornecedor fornecedor;

    @NotNull
    @DecimalMin("0.0")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;

    @Version
    private Long version;
}
