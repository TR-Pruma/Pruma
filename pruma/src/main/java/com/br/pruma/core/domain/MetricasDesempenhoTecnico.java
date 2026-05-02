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
        name = "metricas_desempenho_tecnico",
        indexes = @Index(name = "idx_metrica_tec_profissional", columnList = "profissional_id")
)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@ToString(onlyExplicitlyIncluded = true)
@Schema(description = "Metricas de desempenho tecnico do profissional")
public class MetricasDesempenhoTecnico extends AuditableEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "metrica_tecnico_id", updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "profissional_id", nullable = false)
    private ProfissionalDeBase profissional;

    @Column(name = "desvio_pontualidade_horas", precision = 10, scale = 4)
    private BigDecimal desvioPontualidadeHoras;

    @Column(name = "total_etapas_concluidas")
    private Integer totalEtapasConcluidas;

    @Column(name = "total_etapas_planejadas")
    private Integer totalEtapasPlanejadas;

    @Column(name = "qualidade_media_fotos", precision = 5, scale = 4)
    private BigDecimal qualidadeMediaFotos;

    @Column(name = "percentual_fotos_validas_exif", precision = 5, scale = 4)
    private BigDecimal percentualFotosValidasExif;

    @Column(name = "periodo_referencia_inicio")
    private LocalDate periodoReferenciaInicio;

    @Column(name = "periodo_referencia_fim")
    private LocalDate periodoReferenciaFim;
}
