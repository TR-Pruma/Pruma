package com.br.pruma.core.domain;

import com.br.pruma.core.enums.StatusTarefa;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Representa uma tarefa vinculada a uma Atividade.
 * Status controlado por enum {@link StatusTarefa} para type-safety.
 */
@Entity
@Table(
        name = "tarefa",
        indexes = {
                @Index(name = "idx_tarefa_atividade", columnList = "atividade_id"),
                @Index(name = "idx_tarefa_status", columnList = "status")
        }
)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class Tarefa extends AuditableEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tarefa_id", updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "atividade_id", nullable = false)
    private Atividade atividade;

    @Column(name = "descricao", columnDefinition = "TEXT", nullable = false)
    @NotBlank(message = "Descrição da tarefa é obrigatória")
    @Size(max = 2000)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 30, nullable = false)
    @Builder.Default
    private StatusTarefa status = StatusTarefa.PENDENTE;

    @Column(name = "data_criacao")
    private LocalDate dataCriacao;

    @Column(name = "data_conclusao")
    private LocalDate dataConclusao;
}
