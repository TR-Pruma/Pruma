package com.br.pruma.modelos;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Table(name = "item_orcamento")
@Data
public class ItemOrcamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "orcamento_id", referencedColumnName = "orcamento_id")
    private Orcamento orcamento;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "quantidade")
    private Integer quantidade;

    @Column(name = "valor_unitario")
    private Float valorUnitario;

    @Column(name = "valor_total")
    private Float valorTotal;
}
