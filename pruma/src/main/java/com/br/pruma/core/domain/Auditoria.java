package com.br.pruma.core.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@ApiModel(description = "Representa um registro de auditoria do sistema")
@Data
@Entity
@Table(name = "auditoria")
public class Auditoria {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auditoria_id")
    @ApiModelProperty(value = "Identificador único da auditoria", example = "1")
    private Integer id;

    @Column(name = "cliente_cpf", columnDefinition = "TEXT")
    @ApiModelProperty(value = "CPF do cliente associado à auditoria", example = "12345678900")
    private String clienteCpf;

    @ManyToOne
    @JoinColumn(name = "tipo_usuario", referencedColumnName = "tipo_usuario")
    @ApiModelProperty(value = "Tipo de usuário que realizou a ação", example = "2")
    private Integer tipoUsuario;

    @Column(name = "acao", columnDefinition = "TEXT")
    @ApiModelProperty(value = "Descrição da ação realizada", example = "Usuário alterou os dados do perfil")
    private String acao;

    @Column(name = "data_hora")
    @ApiModelProperty(value = "Data e hora da ação registrada", example = "2024-03-16T14:30:00")
    private LocalDateTime dataHora;
}
