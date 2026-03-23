package com.br.pruma.core.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * Técnico responsável pela execução de obras.
 * Herda auditoria (createdAt, updatedAt, ativo, version) de {@link AuditableEntity}.
 */
@Entity
@Table(
        name = "tecnico_de_obras",
        indexes = {
                @Index(name = "idx_tecnico_nome", columnList = "nome")
        }
)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class TecnicoDeObras extends AuditableEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tecnico_id", updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Integer id;

    @Column(name = "nome", length = 100, nullable = false)
    @NotBlank(message = "Nome do técnico é obrigatório")
    @Size(max = 100)
    private String nome;

    @Column(name = "especialidade", length = 50)
    @Size(max = 50)
    private String especialidade;

    @Column(name = "telefone", length = 20)
    @Pattern(regexp = "^[\\d\\s()+-]{7,20}$", message = "Telefone inválido")
    private String telefone;
}
