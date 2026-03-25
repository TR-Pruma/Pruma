package com.br.pruma.core.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Associação N:N entre {@link Insumo} e {@link Fornecedor} com atributo de preço.
 * Usa chave composta via {@link InsumoFornecedorAuxId}.
 *
 * Não herda AuditableEntity propositalmente: o @SQLRestriction("ativo = true")
 * da superclasse conflita com @EmbeddedId no Hibernate ao montar queries por chave composta.
 * Auditoria mínima (createdAt/updatedAt) é mantida diretamente.
 */
@Entity
@Table(
        name = "insumo_fornecedor_aux",
        indexes = {
                @Index(name = "idx_ins_forn_insumo",     columnList = "insumo_id"),
                @Index(name = "idx_ins_forn_fornecedor", columnList = "fornecedor_id")
        }
)
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class InsumoFornecedorAux implements Serializable {

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

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public InsumoFornecedorAux(Integer insumoId, Integer fornecedorId) {
        this.id = new InsumoFornecedorAuxId(insumoId, fornecedorId);
    }
}
