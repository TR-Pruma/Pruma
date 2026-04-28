package com.br.pruma.core.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "assinatura_digital",
        indexes = {
                @Index(name = "idx_assinatura_data_assinatura", columnList = "data_assinatura")
        }
)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Schema(description = "Representa uma assinatura digital vinculada a um usuário e um documento")
public class AssinaturaDigital extends AuditableEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assinatura_digital_id", updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    @ToString.Include
    @Schema(description = "Identificador único da assinatura digital", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", referencedColumnName = "usuario_id", nullable = false)
    @ToString.Exclude
    @Schema(description = "Usuário que realizou a assinatura", example = "5", requiredMode = Schema.RequiredMode.REQUIRED)
    private Usuario usuario;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "documento_id", referencedColumnName = "documento_id", nullable = false)
    @ToString.Exclude
    @Schema(description = "Identificador do documento assinado", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
    private Documento documento;

    @Column(name = "hash", nullable = true, length = 255)
    @ToString.Include
    @Schema(description = "Hash criptográfico da assinatura", example = "abc123def456...")
    private String hash;

    @NotNull
    @Column(name = "data_assinatura", nullable = false)
    @ToString.Include
    @Schema(description = "Data e hora da assinatura", example = "2024-03-16T14:30:00", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime dataAssinatura;
}