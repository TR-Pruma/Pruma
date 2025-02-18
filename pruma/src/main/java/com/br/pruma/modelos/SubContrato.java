package com.br.pruma.modelos;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Entity
@Table(name = "subcontrato")
@Data
public class SubContrato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subcontrato_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "cliente_cpf", referencedColumnName = "cliente_cpf")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "projeto_id", referencedColumnName = "projeto_id")
    private Projeto projeto;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "valor")
    private Float valor;

    @Column(name = "data_inicio")
    @Temporal(TemporalType.DATE)
    private Date dataInicio;

    @Column(name = "data_fim")
    @Temporal(TemporalType.DATE)
    private Date dataFim;
}