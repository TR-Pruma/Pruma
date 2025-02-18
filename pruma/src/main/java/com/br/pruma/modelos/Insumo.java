package com.br.pruma.modelos;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "insumo")
@Data
public class Insumo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "insumo_id")
    private Integer id;

    @Column(name = "nome", length = 255)
    private String nome;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "unidade_medida", length = 15)
    private String unidadeMedida;

    @Column(name = "custo")
    private Float custo;
}
