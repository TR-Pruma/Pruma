package com.br.pruma.core.domain;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "relatorio")
@Data
public class Relatorio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "relatorio_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "obra_id", referencedColumnName = "obra_id")
    private Integer obra;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "data_criacao")
    @Temporal(TemporalType.DATE)
    private Date dataCriacao;
}
