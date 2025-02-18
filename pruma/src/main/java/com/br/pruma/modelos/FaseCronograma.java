package com.br.pruma.modelos;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "fase_cronograma")
@Data
public class FaseCronograma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fase_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "cronograma_id", referencedColumnName = "cronograma_id")
    private Cronograma cronograma;

    @Column(name = "nome", length = 255)
    private String nome;

    @Column(name = "descricao", length = 255)
    private String descricao;

    @Column(name = "data_inicio")
    @Temporal(TemporalType.DATE)
    private Date dataInicio;

    @Column(name = "data_fim")
    @Temporal(TemporalType.DATE)
    private Date dataFim;
}
