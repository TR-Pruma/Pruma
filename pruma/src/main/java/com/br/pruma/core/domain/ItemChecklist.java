package com.br.pruma.core.domain;

import com.br.pruma.core.enums.StatusItem;
import jakarta.persistence.*;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@FilterDef(name = "ativoFilter", parameters = @ParamDef(name = "ativo", type = boolean.class))
@Filter(name = "ativoFilter", condition = "ativo = :ativo")

@Entity
@Table(
        name = "item_checklist",
        indexes = @Index(name = "idx_item_checklist_ordem", columnList = "checklist_id, ordem"),
        uniqueConstraints = @UniqueConstraint(
                name = "uk_item_checklist_ordem",
                columnNames = {"checklist_id", "ordem"}
        )
)
@SQLDelete(sql = "UPDATE item_checklist SET ativo = false WHERE item_id = ?")
@Where(clause = "ativo = true")
@EntityListeners(org.springframework.data.jpa.domain.support.AuditingEntityListener.class)
@Getter
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ItemChecklist implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "item_id", updatable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "checklist_id", nullable = false)
    @NotNull(message = "O checklist é obrigatório")
    @EqualsAndHashCode.Include
    @ToString.Include(name = "checklistId")
    private Checklist checklist;

    @NotBlank(message = "A descrição é obrigatória")
    @Size(max = 500, message = "A descrição deve ter no máximo 500 caracteres")
    @Column(name = "descricao", nullable = false, length = 500)
    private String descricao;

    @NotNull(message = "A ordem é obrigatória")
    @Min(value = 1, message = "A ordem deve ser maior ou igual a 1")
    @Column(name = "ordem", nullable = false)
    private Integer ordem;

    @NotNull(message = "O status é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StatusItem status;

    @Size(max = 1000, message = "A observação deve ter no máximo 1000 caracteres")
    @Column(name = "observacao", length = 1000)
    private String observacao;

    @Column(name = "ativo", nullable = false)
    private boolean ativo;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * Garante valores default e invariantes antes do primeiro persist.
     */
    @PrePersist
    private void prePersist() {
        this.status = (this.status == null ? StatusItem.PENDENTE : this.status);
        this.ativo  = true;
    }
}