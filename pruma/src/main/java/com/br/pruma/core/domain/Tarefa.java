package com.br.pruma.core.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "tarefa")
@Data
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
