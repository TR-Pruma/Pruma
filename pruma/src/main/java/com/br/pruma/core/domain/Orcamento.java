package com.br.pruma.core.domain;

import com.br.pruma.core.enums.StatusOrcamento;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(
        name    = "orcamento",
        indexes = @Index(
                name       = "idx_orcamento_projeto_status",
                columnList = "projeto_id,status"
        )
)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class Orcamento extends AuditableEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orcamento_id", updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Integer id;

    @NotNull(message = "Projeto é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "projeto_id", nullable = false)
    @ToString.Include(name = "projetoId")
    private Projeto projeto;

    @NotNull(message = "Empresa é obrigatória")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name                 = "empresa_cnpj",
            referencedColumnName = "empresa_cnpj",
            nullable             = false
    )
    @ToString.Include(name = "empresaCnpj")
    private Empresa empresa;

    @NotNull(message = "Valor é obrigatório")
    @DecimalMin(value = "0.00", message = "Valor deve ser maior ou igual a zero")
    @Column(name = "valor", nullable = false, precision = 18, scale = 2)
    @ToString.Include
    private BigDecimal valor;

    @NotNull(message = "Data de envio é obrigatória")
    @Column(name = "data_envio", nullable = false)
    @ToString.Include
    private LocalDate dataEnvio;

    @NotNull(message = "Status é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 15, nullable = false)
    @ToString.Include
    private StatusOrcamento status;
}
