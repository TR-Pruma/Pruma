package com.br.pruma.core.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "profissional_de_base")
@Data
public class ProfissionalDeBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profissional_id")
    private Integer id;

    @Column(name = "nome", length = 100)
    private String nome;

    @Column(name = "especialidade", length = 50)
    private String especialidade;

    @Column(name = "telefone", length = 20)
    private String telefone;
}
