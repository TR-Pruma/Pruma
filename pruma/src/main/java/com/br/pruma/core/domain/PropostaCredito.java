package com.br.pruma.core.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Proposta de microcrédito gerada a partir do score TFE do trabalhador.
 * A taxa de juros é calculada externamente: taxa = taxa_livre_risco + β * (1 - score).
 * Este CRUD só persiste — a lógica de precificação fica no projeto principal.
 */
@Entity
@Table(
        name = "proposta_credito",
        indexes = {
                @Index(name = "idx_proposta_credito_profissional", columnList = "profissional_id"),
                @Index(name = "idx_proposta_credito_status",       columnList = "status")
        }
)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class PropostaCredito implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "proposta_credito_id", updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    private Long id;

    @NotNull(message = "Profissional é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "profissional_id", nullable = false)
    private ProfissionalDeBase profissional;

    /** Snapshot do score TFE no momento da concessão — imutável após criação */
    @NotNull
    @Column(name = "score_no_momento", nullable = false, precision = 5, scale = 4)
    private BigDecimal scoreNoMomento;

    @NotNull
    @DecimalMin(value = "0.01")
    @Column(name = "valor_solicitado", nullable = false, precision = 18, scale = 2)
    private BigDecimal valorSolicitado;

    @DecimalMin(value = "0.00")
    @Column(name = "valor_aprovado", precision = 18, scale = 2)
    private BigDecimal valorAprovado;

    /** Taxa de juros anual aplicada: taxa_livre_risco + β*(1 - score) */
    @DecimalMin(value = "0.00")
    @Column(name = "taxa_juros", precision = 6, scale = 4)
    private BigDecimal taxaJuros;

    @Min(value = 1)
    @Column(name = "numero_parcelas")
    private Integer numeroParcelas;

    /** SOLICITADA | APROVADA | NEGADA | QUITADA */
    @NotBlank
    @Column(name = "status", length = 20, nullable = false)
    @Builder.Default
    private String status = "SOLICITADA";

    /** Identificador do banco/fintech parceiro que aprovou o crédito */
    @Column(name = "parceiro_financeiro", length = 100)
    private String parceiroFinanceiro;

    @Column(name = "data_vencimento")
    private LocalDate dataVencimento;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;
}
