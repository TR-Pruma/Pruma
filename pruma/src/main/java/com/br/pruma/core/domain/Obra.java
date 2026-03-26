package com.br.pruma.core.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(
        name = "obra",
        indexes = {
                @Index(name = "idx_obra_projeto", columnList = "projeto_id"),
                @Index(name = "idx_obra_data_inicio", columnList = "data_inicio")
        }
)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class Obra extends AuditableEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "obra_id", updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Integer id;

    @NotNull(message = "Projeto é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "projeto_id", nullable = false)
    @ToString.Exclude
    private Projeto projeto;

    @NotBlank(message = "Descrição é obrigatória")
    @Column(name = "descricao", columnDefinition = "TEXT", nullable = false)
    private String descricao;

    @NotNull(message = "Data de início é obrigatória")
    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @Column(name = "data_fim")
    private LocalDate dataFim;

    // ---------- Domain helpers ----------

    public void updateFrom(Obra source) {
        if (source == null) return;
        if (source.getProjeto() != null) this.setProjeto(source.getProjeto());
        if (source.getDescricao() != null && !source.getDescricao().isBlank()) this.setDescricao(source.getDescricao());
        if (source.getDataInicio() != null) this.setDataInicio(source.getDataInicio());
        this.setDataFim(source.getDataFim());
    }

    public static Obra ofId(Integer id) {
        if (id == null) return null;
        Obra obra = new Obra();
        obra.setId(id);
        return obra;
    }
}
