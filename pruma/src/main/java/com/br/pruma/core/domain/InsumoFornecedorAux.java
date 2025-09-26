package com.br.pruma.core.domain;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "insumo_fornecedor_aux")
@Getter
@Setter
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class InsumoFornecedorAux {

    @EmbeddedId
    @EqualsAndHashCode.Include
    private InsumoFornecedorAuxId id;

    /**
     * Usa o record Insumo como entidade JPA.
     * É necessário Hibernate 6.2+ ou outro provedor que suporte JPA 3.1+.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("insumoId")
    @JoinColumn(name = "insumo_id", nullable = false)
    @ToString.Include(name = "insumoId")
    private Insumo insumo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("fornecedorId")
    @JoinColumn(name = "fornecedor_id", nullable = false)
    @ToString.Include(name = "fornecedorId")
    private Fornecedor fornecedor;

    @Column(name = "preco", nullable = false, precision = 13, scale = 2)
    private BigDecimal preco;


    public InsumoFornecedorAux(Integer insumoId, Integer fornecedorId) {
        this.insumo.setId(insumoId);
        this.fornecedor.setId(fornecedorId);
    }
}