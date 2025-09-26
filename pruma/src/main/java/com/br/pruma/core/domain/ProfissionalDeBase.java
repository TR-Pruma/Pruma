package com.br.pruma.core.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100)
    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @Size(max = 50)
    @Column(name = "especialidade", length = 50)
    private String especialidade;

    @Size(max = 20)
    @Column(name = "telefone", length = 20)
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

}