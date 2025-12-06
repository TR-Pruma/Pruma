package com.br.pruma.core.domain;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(
        name = "projeto_profissional",
        indexes = {
                @Index(name = "idx_projeto_profissional_projeto", columnList = "projeto_id"),
                @Index(name = "idx_projeto_profissional_profissional", columnList = "profissional_id")
        }
)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class ProjetoProfissional implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Integer id;

    /**
     * Associação para Projeto (many-to-one).
     * Fetch LAZY para evitar carregamento desnecessário; não cascade para não propagar operações de persistência inadvertidamente.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "projeto_id", referencedColumnName = "projeto_id", nullable = false)
    @ToString.Exclude
    private Projeto projeto;

    /**
     * Associação para ProfissionalDeBase (many-to-one).
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "profissional_id", referencedColumnName = "profissional_id", nullable = false)
    @ToString.Exclude
    private ProfissionalDeBase profissional;

    /**
     * Conveniência para mappers quando for necessário criar uma referência apenas com o identificador.
     */
    public static ProjetoProfissional of(Integer id, Integer projetoId, Integer profissionalId) {
        return ProjetoProfissional.builder()
                .id(id)
                .projeto(Projeto.ofId(projetoId))
                .profissional(ProfissionalDeBase.ofId(profissionalId))
                .build();
    }
}

