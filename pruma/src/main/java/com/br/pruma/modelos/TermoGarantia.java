package com.br.pruma.modelos;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Entity
@Table(name = "termo_garantia")
@Data
public class TermoGarantia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "termo_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "projeto_id", referencedColumnName = "projeto_id")
    private Projeto projeto;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "data_emissao")
    @Temporal(TemporalType.DATE)
    private Date dataEmissao;

    @Column(name = "validade_meses")
    private Integer validadeMeses;
}