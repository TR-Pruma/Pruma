package com.br.pruma.core.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Representa uma avaliação feita por um cliente sobre um projeto.
 */
@Entity
@Table(name = "avaliacao",
        indexes = {
                @Index(name = "idx_avaliacao_projeto", columnList = "projeto_id"),
                @Index(name = "idx_avaliacao_cliente", columnList = "cliente_cpf")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"projeto", "cliente"})
@Schema(description = "Representa uma avaliação de um cliente sobre um projeto")
public class Avaliacao implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "avaliacao_id", nullable = false, updatable = false)
    @EqualsAndHashCode.Include
    @Schema(description = "Identificador único da avaliação", example = "1")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "projeto_id", nullable = false)
    @Schema(description = "Projeto avaliado")
    private Projeto projeto;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cliente_cpf", nullable = false)
    @Schema(description = "Cliente que fez a avaliação")
    private Cliente cliente;

    @Column(name = "nota", precision = 3, scale = 1, nullable = false)
    @Schema(description = "Nota dada pelo cliente ao projeto (0.0 a 10.0)", example = "8.5")
    private BigDecimal nota;

    @Column(name = "comentario", columnDefinition = "TEXT")
    @Schema(description = "Comentário opcional sobre a avaliação", example = "Ótima experiência, recomendo!")
    private String comentario;

    @Column(name = "created_at", nullable = false, updatable = false)
    @Schema(description = "Data de criação da avaliação")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @Schema(description = "Data da última atualização da avaliação")
    private LocalDateTime updatedAt;

    @Version
    @Column(name = "version", nullable = false)
    @Schema(description = "Controle de versão para concorrência otimista", example = "1")
    private Long version;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
