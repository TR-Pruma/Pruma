package com.br.pruma.modelos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@ApiModel(description = "Representa uma avaliação de um cliente sobre um projeto")
@Data
@Entity
@Table(name = "avaliacao")
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "avaliacao_id")
    @ApiModelProperty(value = "Identificador único da avaliação", example = "1")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "projeto_id", referencedColumnName = "projeto_id")
    @ApiModelProperty(value = "Identificador do projeto avaliado", example = "101")
    private Integer projeto;

    @ManyToOne
    @JoinColumn(name = "cliente_cpf", referencedColumnName = "cliente_cpf")
    @ApiModelProperty(value = "CPF do cliente que fez a avaliação", example = "12345678900")
    private Long cliente;

    @Column(name = "nota", precision = 2, scale = 1)
    @ApiModelProperty(value = "Nota dada pelo cliente ao projeto (0.0 a 10.0)", example = "8.5")
    private BigDecimal nota;

    @Column(name = "comentario", columnDefinition = "TEXT")
    @ApiModelProperty(value = "Comentário opcional sobre a avaliação", example = "Ótima experiência, recomendo!")
    private String comentario;
}
