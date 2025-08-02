package com.br.pruma.core.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "pagamento")
@Data
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pagamento_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "orcamento_id", referencedColumnName = "orcamento_id")
    private Integer orcamento;

    @Column(name = "valor", precision = 18, scale = 2)
    private BigDecimal valor;

    @Column(name = "data_pagamento")
    @Temporal(TemporalType.DATE)
    private Date dataPagamento;

    @Column(name = "forma_pagamento", length = 15)
    private String formaPagamento;
}
