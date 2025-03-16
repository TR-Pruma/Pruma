package com.br.pruma.modelos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@ApiModel(description = "Representa uma atividade dentro de um projeto")
@Data
@Entity
@Table(name = "atividade")
public class Atividade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "atividade_id")
    @ApiModelProperty(value = "Identificador único da atividade", example = "1")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "projeto_id", referencedColumnName = "projeto_id")
    @ApiModelProperty(value = "Identificador do projeto ao qual a atividade pertence", example = "101")
    private Integer projeto;

    @Column(name = "descricao", columnDefinition = "TEXT")
    @ApiModelProperty(value = "Descrição detalhada da atividade", example = "Desenvolvimento do módulo de autenticação")
    private String descricao;

    @Column(name = "status", length = 15)
    @ApiModelProperty(value = "Status atual da atividade", example = "Em andamento")
    private String status;

    @Column(name = "data_inicio")
    @ApiModelProperty(value = "Data de início da atividade", example = "2024-03-01")
    private LocalDate dataInicio;

    @Column(name = "data_fim")
    @ApiModelProperty(value = "Data prevista ou real de conclusão da atividade", example = "2024-04-01")
    private LocalDate dataFim;
}
