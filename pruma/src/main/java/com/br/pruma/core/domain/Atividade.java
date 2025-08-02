package com.br.pruma.core.domain;

import com.br.pruma.core.enums.StatusAtividade;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;

@ApiModel(description = "Representa uma atividade dentro de um projeto")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = "projeto")
@Entity
@Table(name = "atividade")
public class Atividade implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "atividade_id")
    @ApiModelProperty(value = "Identificador único da atividade", example = "1")
    @EqualsAndHashCode.Include
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projeto_id", referencedColumnName = "projeto_id")
    @ApiModelProperty(value = "Projeto ao qual a atividade pertence")
    private Projeto projeto;

    @Column(name = "descricao", columnDefinition = "TEXT")
    @ApiModelProperty(value = "Descrição detalhada da atividade", example = "Desenvolvimento do módulo de autenticação")
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 15)
    @ApiModelProperty(value = "Status atual da atividade", example = "EM_ANDAMENTO")
    private StatusAtividade status;

    @Column(name = "data_inicio")
    @ApiModelProperty(value = "Data de início da atividade", example = "2024-03-01")
    private LocalDate dataInicio;

    @Column(name = "data_fim")
    @ApiModelProperty(value = "Data prevista ou real de conclusão da atividade", example = "2024-04-01")
    private LocalDate dataFim;
}
