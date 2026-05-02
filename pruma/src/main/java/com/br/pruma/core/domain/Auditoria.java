package com.br.pruma.core.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;

@Entity
@Table(
        name = "auditoria",
        indexes = {
                @Index(name = "idx_auditoria_usuario", columnList = "usuario_id"),
                @Index(name = "idx_auditoria_created", columnList = "created_at")
        }
)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@ToString(onlyExplicitlyIncluded = true)
@Schema(description = "Representa um registro de auditoria do sistema")
public class Auditoria extends AuditableEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auditoria_id", updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    @ToString.Include
    @Schema(description = "Identificador unico da auditoria", example = "1")
    private Integer id;

    @NotNull(message = "Usuario e obrigatorio")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    @Schema(description = "Usuario que realizou a acao")
    private Usuario usuario;

    @NotBlank(message = "Acao e obrigatoria")
    @Size(max = 100, message = "Acao deve ter no maximo 100 caracteres")
    @Column(name = "acao", length = 100, nullable = false)
    @Schema(description = "Acao realizada", example = "UPDATE")
    private String acao;

    @NotBlank(message = "Entidade e obrigatoria")
    @Size(max = 100, message = "Entidade deve ter no maximo 100 caracteres")
    @Column(name = "entidade", length = 100, nullable = false)
    @Schema(description = "Nome da entidade afetada", example = "Projeto")
    private String entidade;

    @Column(name = "entidade_id")
    @Schema(description = "ID do registro afetado", example = "42")
    private Integer entidadeId;

    @Column(name = "detalhe", columnDefinition = "TEXT")
    @Schema(description = "Detalhe da alteracao", example = "{\"valor_anterior\": \"A\", \"valor_novo\": \"B\"}")
    private String detalhe;
}
