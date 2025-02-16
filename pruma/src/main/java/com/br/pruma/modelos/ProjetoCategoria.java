package com.br.pruma.modelos;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "projeto_categoria")
@Data
public class ProjetoCategoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categoria_id")
    private Integer id;

    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;
}
