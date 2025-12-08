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
    private InsumoFornecedorAuxId id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("insumoId")
    @JoinColumn(name = "insumo_id", nullable = false)
    private Insumo insumo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("fornecedorId")
    @JoinColumn(name = "fornecedor_id", nullable = false)
    private Fornecedor fornecedor;

    @Column(name = "preco", nullable = false, precision = 13, scale = 2)
    private BigDecimal preco;

    public InsumoFornecedorAux(Integer insumoId, Integer fornecedorId) {
        this.id = new InsumoFornecedorAuxId(insumoId, fornecedorId);
    }
}
