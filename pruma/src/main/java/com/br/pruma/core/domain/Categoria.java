package com.br.pruma.core.domain;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(
        name = "categoria",
        indexes = @Index(name = "idx_categoria_nome", columnList = "nome")
)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Schema(description = "Representa uma categoria associada a projetos ou itens")
public class Categoria implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categoria_id", updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    @ToString.Include
    @Schema(description = "Identificador único da categoria", example = "1", required = true)
    private Integer id;

    @NotBlank(message = "O nome da categoria é obrigatório")
    @Size(max = 255, message = "O nome da categoria deve ter no máximo {max} caracteres")
    @Column(name = "nome", length = 255, nullable = false)
    @Schema(description = "Nome da categoria", example = "Elétrica", required = true)
    private String nome;

    @Size(max = 255, message = "A descrição deve ter no máximo {max} caracteres")
    @Column(name = "descricao", length = 255)
    @Schema(description = "Descrição da categoria", example = "Serviços relacionados à instalação elétrica")
    private String descricao;
}
