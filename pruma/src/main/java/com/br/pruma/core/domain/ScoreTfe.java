package com.br.pruma.core.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "score_tfe",
        indexes = @Index(name = "idx_score_tfe_profissional", columnList = "profissional_id")
)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@ToString(onlyExplicitlyIncluded = true)
@Schema(description = "Score TFE atual do profissional")
public class ScoreTfe extends AuditableEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "score_tfe_id", updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "profissional_id", nullable = false)
    private ProfissionalDeBase profissional;

    @Column(name = "score_total", precision = 5, scale = 4)
    private BigDecimal scoreTotal;

    @Column(name = "score_tecnico", precision = 5, scale = 4)
    private BigDecimal scoreTecnico;

    @Column(name = "score_operacional", precision = 5, scale = 4)
    private BigDecimal scoreOperacional;

    @Column(name = "score_social", precision = 5, scale = 4)
    private BigDecimal scoreSocial;

    @Column(name = "versao_algoritmo", length = 20)
    private String versaoAlgoritmo;

    @Column(name = "calculado_em")
    private LocalDateTime calculadoEm;
}
