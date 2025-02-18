package com.br.pruma.modelos;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "checklist")
public class Checklist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "checklist_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "projeto_id", referencedColumnName = "projeto_id")
    private Projeto projeto;

    @Column(name = "nome", length = 50)
    private String nome;
}
