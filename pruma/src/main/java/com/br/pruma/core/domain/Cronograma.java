package com.br.pruma.core.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(
        name = "cronograma",
        indexes = {
                @Index(name = "idx_cronograma_projeto", columnList = "projeto_id"),
                @Index(name = "idx_cronograma_periodo", columnList = "data_inicio, data_fim")
        },
        uniqueConstraints = {
                @UniqueConstraint(
                        name        = "uk_cronograma_projeto_periodo",
                        columnNames = {"projeto_id", "data_inicio", "data_fim"}
                )
        }
)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class Cronograma extends AuditableEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cronograma_id", nullable = false, updatable = false)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Integer id;

    @NotBlank(message = "Nome é obrigatório")
    @Column(name = "nome", nullable = false)
    @ToString.Include
    private String nome;

    @NotNull(message = "Projeto é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "projeto_id", nullable = false)
    @ToString.Exclude
    private Projeto projeto;

    @NotNull
    @FutureOrPresent
    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @NotNull
    @Future
    @Column(name = "data_fim", nullable = false)
    private LocalDate dataFim;

    @PrePersist
    @PreUpdate
    private void validatePeriodo() {
        if (dataFim.isBefore(dataInicio)) {
            throw new IllegalArgumentException(
                    "A data de fim deve ser igual ou posterior à data de início"
            );
        }
    }
}
