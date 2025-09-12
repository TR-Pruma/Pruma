package com.br.pruma.core.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@ApiModel(description = "Representa um registro de auditoria do sistema")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = "tipoUsuario")
@Entity
@Table(
    name = "auditoria",
    indexes = {
        @Index(name = "idx_auditoria_cpf", columnList = "cliente_cpf"),
        @Index(name = "idx_auditoria_data", columnList = "data_hora")
    }
)
public class Auditoria implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "auditoria_id")
    @ApiModelProperty(value = "Identificador único da auditoria", example = "123e4567-e89b-12d3-a456-426614174000")
    @EqualsAndHashCode.Include
    private UUID id;

    @NotBlank(message = "CPF do cliente é obrigatório")
    @Pattern(regexp = "^\\d{11}$", message = "CPF deve conter 11 dígitos numéricos")
    @Column(name = "cliente_cpf", length = 11)
    @ApiModelProperty(value = "CPF do cliente associado à auditoria", example = "12345678900")
    private String clienteCpf;

    @NotNull(message = "Tipo de usuário é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_usuario", referencedColumnName = "tipo_usuario", nullable = false)
    @ApiModelProperty(value = "Tipo de usuário que realizou a ação")
    private TipoUsuario tipoUsuario;

    @NotBlank(message = "Descrição da ação é obrigatória")
    @Column(name = "acao", columnDefinition = "TEXT")
    @ApiModelProperty(value = "Descrição da ação realizada", example = "Usuário alterou os dados do perfil")
    private String acao;

    @CreationTimestamp
    @Column(name = "data_hora", nullable = false, updatable = false)
    @ApiModelProperty(value = "Data e hora da ação registrada", example = "2024-03-16T14:30:00")
    private LocalDateTime dataHora;

    @Version
    @Column(name = "version")
    private Long version;
}
