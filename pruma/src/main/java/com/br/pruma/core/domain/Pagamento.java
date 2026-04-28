package com.br.pruma.core.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "pagamento",
        indexes = {
                @Index(name = "idx_pagamento_orcamento",  columnList = "orcamento_id"),
                @Index(name = "idx_pagamento_status_liq", columnList = "status_liquidacao"),
                @Index(name = "idx_pagamento_origem",     columnList = "origem_pagamento")
        }
)
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class Pagamento implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pagamento_id", updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Integer id;

    @NotNull(message = "Orçamento é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "orcamento_id", nullable = false)
    @ToString.Include(name = "orcamentoId")
    private Orcamento orcamento;

    @NotNull(message = "Valor é obrigatório")
    @DecimalMin(value = "0.00", message = "Valor deve ser maior ou igual a zero")
    @Column(name = "valor", nullable = false, precision = 18, scale = 2)
    @ToString.Include
    private BigDecimal valor;

    @NotNull(message = "Data de pagamento é obrigatória")
    @Column(name = "data_pagamento", nullable = false)
    private LocalDate dataPagamento;

    @NotBlank(message = "Forma de pagamento é obrigatória")
    @Column(name = "forma_pagamento", length = 15, nullable = false)
    @ToString.Include
    private String formaPagamento;

    // -------------------------------------------------------------------------
    // Campos de liquidação atômica — suporte a Fintech / Drex / microcrédito
    // -------------------------------------------------------------------------

    /**
     * Origem do pagamento: OBRA | CREDITO | MARKETPLACE.
     * Permite ao engine financeiro externo segmentar fluxos de extração.
     */
    @Column(name = "origem_pagamento", length = 20)
    private String origemPagamento;

    /**
     * Meio de liquidação: PIX | DREX | TED | BOLETO.
     * DREX indica integração com moeda digital e contrato inteligente.
     */
    @Column(name = "meio_liquidacao", length = 20)
    private String meioLiquidacao;

    /** Valor bruto antes das deduções da plataforma */
    @DecimalMin(value = "0.00")
    @Column(name = "valor_bruto", precision = 18, scale = 2)
    private BigDecimal valorBruto;

    /** Taxa retida pela plataforma sobre este pagamento */
    @DecimalMin(value = "0.00")
    @Column(name = "taxa_plataforma", precision = 18, scale = 2)
    private BigDecimal taxaPlataforma;

    /** Parcela de crédito deduzida automaticamente (liquidação atômica) */
    @DecimalMin(value = "0.00")
    @Column(name = "parcela_credito", precision = 18, scale = 2)
    private BigDecimal parcelaCredito;

    /**
     * Valor líquido efetivamente creditado ao trabalhador.
     * valorLiquidoTrabalhador = valorBruto - taxaPlataforma - parcelaCredito
     */
    @DecimalMin(value = "0.00")
    @Column(name = "valor_liquido_trabalhador", precision = 18, scale = 2)
    private BigDecimal valorLiquidoTrabalhador;

    /** Status do ciclo de liquidação: PENDENTE | LIQUIDADO | FALHA */
    @Column(name = "status_liquidacao", length = 20)
    @Builder.Default
    private String statusLiquidacao = "PENDENTE";

    /** Referência da transação no parceiro financeiro ou no contrato Drex */
    @Column(name = "id_transacao_externa", length = 100)
    private String idTransacaoExterna;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
