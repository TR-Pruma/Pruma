package com.br.pruma.core.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Série temporal dos scores TFE (Trust Flow Engine) calculados para cada trabalhador.
 * Os registros são imutáveis — nunca atualizados, apenas inseridos pelo engine externo
 * via endpoint protegido com ROLE_ENGINE.
 */
@Entity
@Table(
        name = "historico_score_tfe",
        indexes = {
                @Index(name = "idx_score_tfe_profissional", columnList = "profissional_id"),
                @Index(name = "idx_score_tfe_calculado_em", columnList = "calculado_em")
        }
)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class HistoricoScoreTFE implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "historico_score_id", updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    private Long id;

    @NotNull(message = "Profissional é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "profissional_id", nullable = false)
    private ProfissionalDeBase profissional;

    /** Score total Φ(w) — resultado final ponderado dos três eixos */
    @NotNull
    @Column(name = "score_total", nullable = false, precision = 5, scale = 4)
    private BigDecimal scoreTotal;

    /** Componente ψ_tec — Desempenho Técnico (peso 0.45) */
    @Column(name = "score_desempenho_tecnico", precision = 5, scale = 4)
    private BigDecimal scoreDesempenhoTecnico;

    /** Componente ψ_op — Comportamento Operacional */
    @Column(name = "score_comportamental", precision = 5, scale = 4)
    private BigDecimal scoreComportamental;

    /** Componente ψ_soc — Validação Social / Apadrinhamento (peso 0.50) */
    @Column(name = "score_social", precision = 5, scale = 4)
    private BigDecimal scoreSocial;

    /** Versão do algoritmo TFE que gerou este score — rastreabilidade de mudanças */
    @Column(name = "versao_algoritmo", length = 20)
    private String versaoAlgoritmo;

    @NotNull
    @Column(name = "calculado_em", nullable = false, updatable = false)
    private LocalDateTime calculadoEm;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;
}
