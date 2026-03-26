package com.br.pruma.core.domain;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "pre_obra",
        indexes = {
                @Index(name = "idx_preobra_obra", columnList = "obra_id"),
                @Index(name = "idx_preobra_data_inicio", columnList = "data_inicio")
        })
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class PreObra implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pre_obra_id", updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Integer id;

    @NotNull(message = "Obra é obrigatória")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "obra_id", referencedColumnName = "obra_id", nullable = false)
    @ToString.Exclude
    private Obra obra;

    @NotBlank(message = "Descrição é obrigatória")
    @Column(name = "descricao", columnDefinition = "TEXT", nullable = false)
    private String descricao;

    @Column(name = "data_inicio")
    private LocalDate dataInicio;

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
     * Atualiza apenas os campos não-nulos do DTO/entidade fonte.
     */
    public void applyPatch(PreObra source) {
        if (source == null) return;
        if (source.getObra() != null) this.setObra(source.getObra());
        if (source.getDescricao() != null && !source.getDescricao().isBlank()) this.setDescricao(source.getDescricao());
        if (source.getDataInicio() != null) this.setDataInicio(source.getDataInicio());
    }

    /**
     * Helper para atribuir apenas pela PK da obra sem carregar a entidade inteira.
     */
    public void setObraById(Integer obraId) {
        if (obraId == null) {
            this.obra = null;
        } else {
            this.obra = Obra.builder().id(obraId).build();
        }
    }
}
