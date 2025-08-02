package com.br.pruma.core.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@ApiModel(description = "Representa uma categoria associada a projetos ou itens")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Entity
@Table(name = "categoria")
public class Categoria implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categoria_id")
    @ApiModelProperty(value = "Identificador único da categoria", example = "1")
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(name = "nome", length = 255, nullable = false)
    @ApiModelProperty(value = "Nome da categoria", example = "Elétrica")
    private String nome;

    @Column(name = "descricao", length = 255)
    @ApiModelProperty(value = "Descrição da categoria", example = "Serviços relacionados à instalação elétrica")
    private String descricao;
}
