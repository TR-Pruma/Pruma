package com.br.pruma.core.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "fatiamento_pagamento",
        indexes = {
                @Index(name = "idx_fatiamento_pagamento_id", columnList = "pagamento_id"),
                @Index(name = "idx_fatiamento_obra_id",      columnList = "obra_id")
        }
)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Schema(description = "Fatiamento de pagamento entre plataforma, fornecedores e trabalhador")
public class FatiamentoPagamento implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fatiamento_id", updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pagamento_id", nullable = false)
    private Pagamento pagamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "obra_id")
    private Obra obra;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissional_id")
    private ProfissionalDeBase profissional;

    @Column(name = "valor_bruto", precision = 18, scale = 2)
    private BigDecimal valorBruto;

    @Column(name = "valor_plataforma", precision = 18, scale = 2)
    private BigDecimal valorPlataforma;

    @Column(name = "valor_fornecedores", precision = 18, scale = 2)
    private BigDecimal valorFornecedores;

    @Column(name = "valor_deducao_divida", precision = 18, scale = 2)
    private BigDecimal valorDeducaoDivida;

    @Column(name = "valor_liquido_trabalhador", precision = 18, scale = 2)
    private BigDecimal valorLiquidoTrabalhador;

    @Size(max = 20)
    @Column(name = "status_liquidacao", length = 20)
    private String statusLiquidacao;

    @Column(name = "liquidado_em")
    private LocalDateTime liquidadoEm;

    @Size(max = 128)
    @Column(name = "hash_transacao", length = 128)
    private String hashTransacao;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() { createdAt = LocalDateTime.now(); updatedAt = createdAt; }

    @PreUpdate
    protected void onUpdate() { updatedAt = LocalDateTime.now(); }
}
