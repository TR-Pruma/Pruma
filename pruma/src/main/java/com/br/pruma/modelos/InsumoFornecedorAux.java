package com.br.pruma.modelos;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "insumo_fornecedor_aux")
@Data
public class InsumoFornecedorAux {

    @Id
    @Column(name = "insumo_id")
    private Integer insumoId;

    @ManyToOne
    @JoinColumn(name = "fornecedor_id", referencedColumnName = "fornecedor_id")
    private Fornecedor fornecedor;

    @Column(name = "preco")
    private Float preco;
}