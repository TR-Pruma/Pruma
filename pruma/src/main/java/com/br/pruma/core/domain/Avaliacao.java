package com.br.pruma.core.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;

@Entity
@Table(
        name = "avaliacao",
        indexes = {
                @Index(name = "idx_avaliacao_projeto",   columnList = "projeto_id"),
                @Index(name = "idx_avaliacao_avaliador", columnList = "avaliador_id"),
                @Index(name = "idx_avaliacao_avaliado",  columnList = "avaliado_id")
        }
)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@ToString(onlyExplicitlyIncluded = true)
@Schema(description = "Avaliacao de um usuario sobre outro dentro de um projeto")
public class Avaliacao extends AuditableEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "avaliacao_id", updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    @ToString.Include
    @Schema(description = "Identificador unico da avaliacao", example = "1")
    private Integer id;

    @NotNull(message = "Projeto e obrigatorio")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "projeto_id", nullable = false)
    @Schema(description = "Projeto relacionado")
    private Projeto projeto;

    @NotNull(message = "Avaliador e obrigatorio")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "avaliador_id", nullable = false)
    @Schema(description = "Usuario que realizou a avaliacao")
    private Usuario avaliador;

    @NotNull(message = "Avaliado e obrigatorio")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "avaliado_id", nullable = false)
    @Schema(description = "Usuario que foi avaliado")
    private Usuario avaliado;

    @NotNull(message = "Nota e obrigatoria")
    @Min(value = 0, message = "Nota minima e 0")
    @Max(value = 10, message = "Nota maxima e 10")
    @Column(name = "nota", nullable = false)
    @Schema(description = "Nota de 0 a 10", example = "8")
    private Integer nota;

    @Column(name = "comentario", columnDefinition = "TEXT")
    @Schema(description = "Comentario opcional", example = "Otimo profissional!")
    private String comentario;
}
