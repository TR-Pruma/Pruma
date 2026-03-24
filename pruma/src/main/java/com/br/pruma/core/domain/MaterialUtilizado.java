package com.br.pruma.core.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(
        name              = "material_utilizado",
        uniqueConstraints = @UniqueConstraint(
                name        = "uk_material_utilizado_mat_atividade",
                columnNames = {"material_id", "atividade_id"}
        ),
        indexes = {
                @Index(name = "idx_matutilizado_material", columnList = "material_id"),
                @Index(name = "idx_matutilizado_atividade", columnList = "atividade_id")
        }
)
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class MaterialUtilizado implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Integer id;

    @NotNull(message = "Material é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "material_id", referencedColumnName = "material_id", nullable = false)
    @ToString.Include(name = "materialId")
    private Material material;

    @NotNull(message = "Atividade é obrigatória")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "atividade_id", referencedColumnName = "atividade_id", nullable = false)
    @ToString.Include(name = "atividadeId")
    private Atividade atividade;

    @NotNull(message = "Quantidade utilizada é obrigatória")
    @Min(value = 1, message = "A quantidade utilizada deve ser ao menos 1")
    @Column(name = "quantidade_utilizada", nullable = false)
    private Integer quantidadeUtilizada;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}