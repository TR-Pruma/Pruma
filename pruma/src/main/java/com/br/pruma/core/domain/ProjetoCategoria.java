package com.br.pruma.core.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(
        name = "projeto_categoria",
        indexes = {
                @Index(name = "idx_projeto_categoria_nome", columnList = "nome")
        }
)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA requires a no-args constructor with at least protected visibility
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class ProjetoCategoria implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categoria_id", updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Integer id;

    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    /**
     * Conveniência para mappers (MapStruct) quando for necessário criar uma referência
     * apenas com o identificador.
     */
    public static ProjetoCategoria ofId(Integer id) {
        if (id == null) return null;
        return ProjetoCategoria.builder().id(id).build();
    }
}