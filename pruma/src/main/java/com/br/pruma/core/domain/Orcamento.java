package com.br.pruma.core.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "orcamento")
@Data
public class Orcamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orcamento_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "projeto_id", referencedColumnName = "projeto_id")
    private Integer projeto;

    @ManyToOne
    @JoinColumn(name = "empresa_cnpj", referencedColumnName = "empresa_cnpj")
    private Long empresa;

    @Column(name = "valor", precision = 18, scale = 2)
    private BigDecimal valor;

    @Column(name = "data_envio")
    @Temporal(TemporalType.DATE)
    private Date dataEnvio;

    @Column(name = "status", length = 15)
    private String status;
}