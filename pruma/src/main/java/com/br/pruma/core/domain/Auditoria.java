package com.br.pruma.core.domain;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "auditoria",
        indexes = {
                @Index(name = "idx_auditoria_cpf", columnList = "cliente_cpf"),
                @Index(name = "idx_auditoria_data", columnList = "data_hora")
        }
)
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@ToString(onlyExplicitlyIncluded = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Schema(description = "Representa um registro de auditoria do sistema")
public class Auditoria extends AuditableEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "auditoria_id", updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    @Schema(
            description = "Identificador unico da auditoria",
            example     = "123e4567-e89b-12d3-a456-426614174000",
            required    = true
    )
    private UUID id;

    @NotBlank(message = "CPF do cliente e obrigatorio")
    @Pattern(
            regexp  = "^\\d{11}$",
            message = "CPF deve conter 11 digitos numericos"
    )
    @Column(name = "cliente_cpf", length = 11, nullable = false)
    @Schema(
            description = "CPF do cliente associado a auditoria",
            example     = "12345678900",
            required    = true
    )
    private String clienteCpf;

    @NotNull(message = "Tipo de usuario e obrigatorio")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name     = "tipo_usuario_id",
            nullable = false
    )
    @Schema(
            description = "Tipo de usuario que realizou a acao",
            example     = "ADMINISTRADOR",
            required    = true
    )
    private TipoUsuario tipoUsuario;

    @NotBlank(message = "Descricao da acao e obrigatoria")
    @Column(name = "acao", columnDefinition = "TEXT", nullable = false)
    @Schema(
            description = "Descricao da acao realizada",
            example     = "Usuario alterou os dados do perfil",
            required    = true
    )
    private String acao;

    @CreatedDate
    @Column(name = "data_hora", nullable = false, updatable = false)
    @Schema(
            description = "Data e hora em que a acao foi registrada",
            example     = "2024-03-16T14:30:00",
            required    = true
    )
    private LocalDateTime dataHora;
}
