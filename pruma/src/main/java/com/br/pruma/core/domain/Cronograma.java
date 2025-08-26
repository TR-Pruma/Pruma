package com.br.pruma.core.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "cronograma",
        indexes = {
                @Index(name = "idx_cronograma_projeto",        columnList = "projeto_id"),
                @Index(name = "idx_cronograma_periodo",        columnList = "data_inicio, data_fim")
        },
        uniqueConstraints = {
                @UniqueConstraint(
                        name        = "uk_cronograma_projeto_periodo",
                        columnNames = {"projeto_id", "data_inicio", "data_fim"}
                )
        }
)
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@ToString(exclude = "projeto")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(builderClassName = "CronogramaBuilder")

public class Cronograma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cronograma_id", nullable = false, updatable = false)
    @EqualsAndHashCode.Include
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "projeto_id", nullable = false)
    @NotNull
    private Projeto projeto;

    @Column(name = "data_inicio", nullable = false)
    @NotNull
    @FutureOrPresent
    private LocalDate dataInicio;

    @Column(name = "data_fim", nullable = false)
    @NotNull
    @Future
    private LocalDate dataFim;

    @Version
    @Column(name = "version")
    private Integer version;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

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