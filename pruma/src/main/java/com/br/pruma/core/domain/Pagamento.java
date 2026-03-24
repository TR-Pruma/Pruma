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
                @Index(name = "idx_pagamento_orcamento", columnList = "orcamento_id")
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