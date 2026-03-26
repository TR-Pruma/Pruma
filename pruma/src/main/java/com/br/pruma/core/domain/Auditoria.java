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
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Schema(description = "Representa um registro de auditoria do sistema")
public class Auditoria implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "auditoria_id", updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    @Schema(
            description = "Identificador único da auditoria",
            example     = "123e4567-e89b-12d3-a456-426614174000",
            required    = true
    )
    private UUID id;

    @NotBlank(message = "CPF do cliente é obrigatório")
    @Pattern(
            regexp  = "^\\d{11}$",
            message = "CPF deve conter 11 dígitos numéricos"
    )
    @Column(name = "cliente_cpf", length = 11, nullable = false)
    @Schema(
            description = "CPF do cliente associado à auditoria",
            example     = "12345678900",
            required    = true
    )
    private String clienteCpf;

    @NotNull(message = "Tipo de usuário é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name               = "tipo_usuario",
            referencedColumnName = "tipo_usuario",
            nullable           = false
    )
    @Schema(
            description = "Tipo de usuário que realizou a ação",
            example     = "ADMINISTRADOR",
            required    = true
    )
    private TipoUsuario tipoUsuario;

    @NotBlank(message = "Descrição da ação é obrigatória")
    @Column(name = "acao", columnDefinition = "TEXT", nullable = false)
    @Schema(
            description = "Descrição da ação realizada",
            example     = "Usuário alterou os dados do perfil",
            required    = true
    )
    private String acao;

    @CreatedDate
    @Column(name = "data_hora", nullable = false, updatable = false)
    @Schema(
            description = "Data e hora em que a ação foi registrada",
            example     = "2024-03-16T14:30:00",
            required    = true
    )
    private LocalDateTime dataHora;

    @Version
    @Column(name = "version", nullable = false)
    @Schema(
            description = "Versão do registro para controle de concorrência",
            example     = "3"
    )
    private Long version;
}
