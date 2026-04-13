package com.br.pruma.core.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Modela o grafo social de apadrinhamento entre trabalhadores.
 * Vértices = ProfissionalDeBase; Arestas = esta entidade.
 * Usado pelo componente ψ_soc do TFE para calcular medida espectral de influência.
 */
@Entity
@Table(
        name = "apadrinhamento_rede",
        indexes = {
                @Index(name = "idx_apadrinhamento_padrinho",  columnList = "padrinho_id"),
                @Index(name = "idx_apadrinhamento_afilhado",  columnList = "afilhado_id"),
                @Index(name = "idx_apadrinhamento_status",    columnList = "status")
        }
)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class ApadrinhamentoRede implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "apadrinhamento_id", updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    private Long id;

    @NotNull(message = "Padrinho é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "padrinho_id", nullable = false)
    private ProfissionalDeBase padrinho;

    @NotNull(message = "Afilhado é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "afilhado_id", nullable = false)
    private ProfissionalDeBase afilhado;

    @NotNull
    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    /** Null = relacionamento ainda ativo */
    @Column(name = "data_fim")
    private LocalDate dataFim;

    /** ATIVO | ENCERRADO */
    @Column(name = "status", length = 20, nullable = false)
    @Builder.Default
    private String status = "ATIVO";

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
