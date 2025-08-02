package com.br.pruma.core.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "material_utilizado")
@Data
public class MaterialUtilizado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "material_id", referencedColumnName = "material_id")
    private Integer material;

    @ManyToOne
    @JoinColumn(name = "atividade_id", referencedColumnName = "atividade_id")
    private Integer atividade;

    @Column(name = "quantidade_utilizada")
    private Integer quantidadeUtilizada;
}
