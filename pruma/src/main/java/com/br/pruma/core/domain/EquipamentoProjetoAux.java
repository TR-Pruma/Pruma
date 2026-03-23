package com.br.pruma.core.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Associação N:N entre {@link Equipamento} e {@link Projeto} com atributo de data de alocação.
 * Herda auditoria de {@link AuditableEntity}.
 */
@Entity
@Table(
        name = "equipamento_projeto_aux",
        indexes = {
                @Index(name = "idx_equip_proj_equipamento", columnList = "equipamento_id"),
                @Index(name = "idx_equip_proj_projeto", columnList = "projeto_id")
        }
)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class EquipamentoProjetoAux extends AuditableEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "equipamento_projeto_id", updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Long id;

    @NotNull(message = "Equipamento é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "equipamento_id", referencedColumnName = "equipamento_id", nullable = false)
    private Equipamento equipamento;

    @NotNull(message = "Projeto é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "projeto_id", referencedColumnName = "projeto_id", nullable = false)
    private Projeto projeto;

    @Column(name = "data_alocacao")
    private LocalDate dataAlocacao;
}
