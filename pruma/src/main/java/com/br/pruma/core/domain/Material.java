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
import java.time.LocalDateTime;

@Entity
@Table(
        name              = "material",
        uniqueConstraints = @UniqueConstraint(
                name        = "uk_material_descricao",
                columnNames = "descricao"
        ),
        indexes = @Index(
                name        = "idx_material_descricao",
                columnList  = "descricao"
        )
)
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class Material implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "material_id", updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Integer id;

    @NotBlank(message = "Descrição é obrigatória")
    @Size(max = 2000, message = "Descrição deve ter no máximo 2000 caracteres")
    @Column(name = "descricao", columnDefinition = "TEXT", nullable = false)
    @ToString.Include
    private String descricao;

    @NotNull(message = "Quantidade é obrigatória")
    @Min(value = 0, message = "Quantidade não pode ser negativa")
    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;

    @NotNull(message = "Custo unitário é obrigatório")
    @DecimalMin(value = "0.00", inclusive = true, message = "Custo unitário não pode ser negativo")
    @Digits(integer = 16, fraction = 2, message = "Custo unitário deve ter até 16 dígitos inteiros e 2 decimais")
    @Column(name = "custo_unitario", precision = 18, scale = 2, nullable = false)
    private BigDecimal custoUnitario;

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

