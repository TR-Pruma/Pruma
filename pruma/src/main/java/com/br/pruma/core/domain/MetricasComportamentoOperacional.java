package com.br.pruma.core.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(
        name = "metricas_comportamento_operacional",
        indexes = @Index(name = "idx_metrica_op_profissional", columnList = "profissional_id")
)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@ToString(onlyExplicitlyIncluded = true)
@Schema(description = "Metricas comportamentais e operacionais do profissional")
public class MetricasComportamentoOperacional extends AuditableEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "metrica_operacional_id", updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "profissional_id", nullable = false)
    private ProfissionalDeBase profissional;

    @Column(name = "frequencia_login_dias")
    private Integer frequenciaLoginDias;

    @Column(name = "tempo_medio_resposta_horas", precision = 10, scale = 4)
    private BigDecimal tempoMedioRespostaHoras;

    @Column(name = "indice_consistencia_documental", precision = 5, scale = 4)
    private BigDecimal indiceConsistenciaDocumental;

    @Column(name = "total_solicitacoes_respondidas")
    private Integer totalSolicitacoesRespondidas;

    @Column(name = "total_solicitacoes_recebidas")
    private Integer totalSolicitacoesRecebidas;

    @Column(name = "periodo_referencia_inicio")
    private LocalDate periodoReferenciaInicio;

    @Column(name = "periodo_referencia_fim")
    private LocalDate periodoReferenciaFim;
}
