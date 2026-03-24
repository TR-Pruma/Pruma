package com.br.pruma.core.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(
        name = "fornecedor",
        indexes = {
                @Index(name = "idx_fornecedor_nome", columnList = "nome"),
                @Index(name = "idx_fornecedor_cnpj", columnList = "cnpj", unique = true)
        }
)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class Fornecedor extends AuditableEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fornecedor_id", updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Integer id;

    @NotBlank(message = "Nome do fornecedor é obrigatório")
    @Size(max = 255)
    @Column(name = "nome", length = 255, nullable = false)
    @ToString.Include
    private String nome;

    @NotBlank(message = "CNPJ é obrigatório")
    @Pattern(
            regexp = "^(\\d{14}|\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2})$",
            message = "CNPJ inválido"
    )
    @Column(name = "cnpj", length = 18, nullable = false, unique = true)
    private String cnpj;

    @NotBlank(message = "Contato é obrigatório")
    @Size(max = 100)
    @Column(name = "contato", length = 100, nullable = false)
    private String contato;
}
