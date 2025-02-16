package com.br.pruma.modelos;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Table(name = "insumo_fornecedor")
@Data
public class InsumoFornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "insumo_id", referencedColumnName = "insumo_id")
    private Insumo insumo;

    @ManyToOne
    @JoinColumn(name = "fornecedor_id", referencedColumnName = "fornecedor_id")
    private Fornecedor fornecedor;

    @Column(name = "preco", precision = 10, scale = 2)
    private BigDecimal preco;
}
