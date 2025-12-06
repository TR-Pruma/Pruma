package com.br.pruma.core.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "profissional_de_base",
        indexes = {
                @Index(name = "idx_profissional_nome", columnList = "nome"),
                @Index(name = "idx_profissional_especialidade", columnList = "especialidade")
        }
)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA requires a no-args constructor with at least protected visibility
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class ProfissionalDeBase implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profissional_id", updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Integer id;

    @Column(name = "nome", length = 100, nullable = false)
    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100)
    private String nome;

    @Column(name = "especialidade", length = 50)
    @Size(max = 50)
    private String especialidade;

    @Column(name = "telefone", length = 20)
    @Size(max = 20)
    @ToString.Exclude
    private String telefone;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    /**
     * Atualiza apenas os campos não-nulos/válidos do DTO/entidade fonte.
     */
    public void applyPatch(ProfissionalDeBase patch) {
        if (patch == null) return;
        if (patch.getNome() != null && !patch.getNome().isBlank()) this.setNome(patch.getNome());
        if (patch.getEspecialidade() != null) this.setEspecialidade(patch.getEspecialidade());
        if (patch.getTelefone() != null) this.setTelefone(patch.getTelefone());
    }

    /**
     * Conveniência para mappers (MapStruct) quando for necessário criar uma referência
     * apenas com o identificador (por exemplo @MapsId ou associações).
     */
    public static ProfissionalDeBase ofId(Integer id) {
        if (id == null) return null;
        return ProfissionalDeBase.builder().id(id).build();
    }
}