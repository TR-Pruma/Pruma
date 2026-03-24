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
        name = "material",
        uniqueConstraints = @UniqueConstraint(
                name        = "uk_material_descricao",
                columnNames = "descricao"
        ),
        indexes = @Index(
                name       = "idx_material_descricao",
                columnList = "descricao"
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

    @NotBlank(message = "Descri\u00e7\u00e3o \u00e9 obrigat\u00f3ria")
    @Size(max = 1000, message = "Descri\u00e7\u00e3o deve ter no m\u00e1ximo 1000 caracteres")
    @Column(name = "descricao", length = 1000, nullable = false)
    @ToString.Include
    private String descricao;

    @NotNull(message = "Quantidade \u00e9 obrigat\u00f3ria")
    @Min(value = 0, message = "Quantidade n\u00e3o pode ser negativa")
    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;

    @NotNull(message = "Custo unit\u00e1rio \u00e9 obrigat\u00f3rio")
    @DecimalMin(value = "0.00", inclusive = true, message = "Custo unit\u00e1rio n\u00e3o pode ser negativo")
    @Digits(integer = 16, fraction = 2, message = "Custo unit\u00e1rio deve ter at\u00e9 16 d\u00edgitos inteiros e 2 decimais")
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
