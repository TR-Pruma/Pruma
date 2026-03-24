package com.br.pruma.core.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Associação N:N entre {@link Insumo} e {@link Fornecedor} com atributo de preço.
 * Usa chave composta via {@link InsumoFornecedorAuxId}.
 */
@Entity
@Table(
        name = "insumo_fornecedor_aux",
        indexes = {
                @Index(name = "idx_ins_forn_insumo", columnList = "insumo_id"),
                @Index(name = "idx_ins_forn_fornecedor", columnList = "fornecedor_id")
        }
)
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class InsumoFornecedorAux extends AuditableEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    @EqualsAndHashCode.Include
    private InsumoFornecedorAuxId id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("insumoId")
    @JoinColumn(name = "insumo_id", nullable = false)
    private Insumo insumo;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("fornecedorId")
    @JoinColumn(name = "fornecedor_id", nullable = false)
    private Fornecedor fornecedor;

    @NotNull(message = "Preço é obrigatório")
    @Positive(message = "Preço deve ser positivo")
    @Column(name = "preco", nullable = false, precision = 13, scale = 2)
    private BigDecimal preco;

    public InsumoFornecedorAux(Integer insumoId, Integer fornecedorId) {
        this.id = new InsumoFornecedorAuxId(insumoId, fornecedorId);
    }
}
