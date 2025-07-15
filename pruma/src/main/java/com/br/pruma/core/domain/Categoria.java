package com.br.pruma.core.domain;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "categoria")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categoria_id")
    private Integer id;

    @Column(name = "nome", length = 255)
    private String nome;

    @Column(name = "descricao", length = 255)
    private String descricao;
}
