package com.br.pruma.core.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(
        name = "trajetoria_laboral",
        indexes = {
                @Index(name = "idx_trajetoria_profissional", columnList = "profissional_id"),
                @Index(name = "idx_trajetoria_obra",         columnList = "obra_id")
        }
)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@ToString(onlyExplicitlyIncluded = true)
@Schema(description = "Trajetoria laboral do profissional em obras e projetos")
public class TrajetoriaLaboral extends AuditableEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trajetoria_id", updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "profissional_id", nullable = false)
    private ProfissionalDeBase profissional;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "obra_id")
    private Obra obra;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projeto_id")
    private Projeto projeto;

    @Size(max = 100)
    @Column(name = "funcao_desempenhada", length = 100)
    private String funcaoDesempenhada;

    @Column(name = "data_inicio")
    private LocalDate dataInicio;

    @Column(name = "data_fim")
    private LocalDate dataFim;

    @Column(name = "obra_concluida")
    private Boolean obraConcluida;

    @Column(name = "avaliacao_recebida", precision = 3, scale = 1)
    @Schema(description = "Avaliacao recebida (0.0 a 10.0)", example = "8.5")
    private BigDecimal avaliacaoRecebida;

    @Column(name = "comentario_avaliacao", columnDefinition = "TEXT")
    private String comentarioAvaliacao;
}
