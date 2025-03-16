package com.br.pruma.modelos;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "equipamento")
public class Equipamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "equipamento_id")
    private Integer id;

    @Column(name = "nome", length = 255)
    private String nome;

    @Column(name = "descricao", length = 255)
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "status_id")
    private Integer status;
}
