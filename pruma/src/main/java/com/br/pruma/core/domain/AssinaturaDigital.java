package com.br.pruma.core.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "assinatura_digital",
        indexes = {
                @Index(name = "idx_assinatura_data_hora", columnList = "data_hora")
        }
)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Schema(description = "Representa uma assinatura digital vinculada a um cliente e um documento")
public class AssinaturaDigital implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assinatura_id", updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    @ToString.Include
    @Schema(description = "Identificador único da assinatura digital", example = "1", required = true)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cliente_cpf", referencedColumnName = "cliente_cpf", nullable = false)
    @ToString.Exclude
    @Schema(description = "CPF do cliente que realizou a assinatura", example = "12345678900", required = true)
    private Cliente cliente;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tipo_usuario", referencedColumnName = "tipo_usuario", nullable = false)
    @ToString.Exclude
    @Schema(description = "Tipo de usuário que realizou a assinatura", example = "2", required = true)
    private TipoUsuario tipoUsuario;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "documento_id", referencedColumnName = "documento_id", nullable = false)
    @ToString.Exclude
    @Schema(description = "Identificador do documento assinado", example = "10", required = true)
    private Documento documento;

    @NotNull
    @Column(name = "data_hora", nullable = false)
    @ToString.Include
    @Schema(description = "Data e hora da assinatura", example = "2024-03-16T14:30:00", required = true)
    private LocalDateTime dataHora;
}
