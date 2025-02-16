package com.br.pruma.modelos;

import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
public class Tarefa {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tarefaId;

    @ManyToOne
    private Atividade atividade;

    private String descricao;
    private String status;
    private LocalDate dataCriacao;
    private LocalDate dataConclusao;
}
