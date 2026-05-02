package com.br.pruma.core.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;

@Entity
@Table(
        name = "especialidade",
        indexes = @Index(name = "idx_especialidade_nome", columnList = "nome", unique = true)
)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@ToString(onlyExplicitlyIncluded = true)
@Schema(description = "Especialidade profissional")
public class Especialidade extends AuditableEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "especialidade_id", updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Integer id;

    @NotBlank
    @Size(max = 100)
    @Column(name = "nome", length = 100, nullable = false, unique = true)
    @Schema(description = "Nome da especialidade", example = "Eletrica")
    private String nome;

    @Size(max = 255)
    @Column(name = "descricao", length = 255)
    @Schema(description = "Descricao da especialidade")
    private String descricao;
}
