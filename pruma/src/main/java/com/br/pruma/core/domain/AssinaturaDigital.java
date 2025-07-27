package com.br.pruma.core.domain;

import jakarta.persistence.*;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
@ApiModel(description = "Representa uma assinatura digital vinculada a um cliente e um documento")
@Data
@Entity
@Table(name = "assinatura_digital")
public class AssinaturaDigital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assinatura_id")
    @ApiModelProperty(value = "Identificador único da assinatura digital", example = "1")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "cliente_cpf", referencedColumnName = "cliente_cpf")
    @ApiModelProperty(value = "CPF do cliente que realizou a assinatura", example = "12345678900")
    private Cliente cliente;  // É um objeto, não Long!

    @ManyToOne
    @JoinColumn(name = "tipo_usuario", referencedColumnName = "tipo_usuario")
    @ApiModelProperty(value = "Tipo de usuário que realizou a assinatura", example = "2")
    private TipoUsuario tipoUsuario; // Também objeto

    @ManyToOne
    @JoinColumn(name = "documento_id", referencedColumnName = "documento_id")
    @ApiModelProperty(value = "Identificador do documento assinado", example = "10")
    private Documento documento; // Outro objeto

    @Column(name = "data_hora")
    @ApiModelProperty(value = "Data e hora da assinatura", example = "2024-03-16T14:30:00")
    private LocalDateTime dataHora;
}

