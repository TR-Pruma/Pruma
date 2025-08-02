package com.br.pruma.core.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

@ApiModel(description = "Representa uma avaliação de um cliente sobre um projeto")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"projeto", "cliente"})
@Entity
@Table(name = "avaliacao")
public class Avaliacao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "avaliacao_id")
    @ApiModelProperty(value = "Identificador único da avaliação", example = "1")
    @EqualsAndHashCode.Include
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projeto_id", referencedColumnName = "projeto_id")
    @ApiModelProperty(value = "Projeto avaliado")
    private Projeto projeto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_cpf", referencedColumnName = "cliente_cpf")
    @ApiModelProperty(value = "Cliente que fez a avaliação")
    private Cliente cliente;

    @Column(name = "nota", precision = 3, scale = 1, nullable = false)
    @ApiModelProperty(value = "Nota dada pelo cliente ao projeto (0.0 a 10.0)", example = "8.5")
    private BigDecimal nota;

    @Column(name = "comentario", columnDefinition = "TEXT")
    @ApiModelProperty(value = "Comentário opcional sobre a avaliação", example = "Ótima experiência, recomendo!")
    private String comentario;
}
