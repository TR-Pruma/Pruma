package com.br.pruma.core.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.time.LocalDateTime;

@ApiModel(description = "Representa uma assinatura digital vinculada a um cliente e um documento")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"cliente", "documento", "tipoUsuario"})
@Entity
@Table(name = "assinatura_digital")
public class AssinaturaDigital implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assinatura_id")
    @ApiModelProperty(value = "Identificador único da assinatura digital", example = "1")
    @EqualsAndHashCode.Include
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_cpf", referencedColumnName = "cliente_cpf")
    @ApiModelProperty(value = "CPF do cliente que realizou a assinatura", example = "12345678900")
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_usuario", referencedColumnName = "tipo_usuario")
    @ApiModelProperty(value = "Tipo de usuário que realizou a assinatura", example = "2")
    private TipoUsuario tipoUsuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "documento_id", referencedColumnName = "documento_id")
    @ApiModelProperty(value = "Identificador do documento assinado", example = "10")
    private Documento documento;

    @Column(name = "data_hora", nullable = false)
    @ApiModelProperty(value = "Data e hora da assinatura", example = "2024-03-16T14:30:00")
    private LocalDateTime dataHora;
}
