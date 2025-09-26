package com.br.pruma.core.domain;


import jakarta.persistence.*;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(
        name    = "tipo_documento",
        indexes = {
                @Index(name = "idx_tipo_documento_descricao", columnList = "descricao")
        }
)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Schema(
        description = "Representa um tipo de documento no sistema"
)
public class TipoDocumento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tipo_id", updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    @ToString.Include
    @Schema(
            description = "Identificador único do tipo de documento",
            example     = "5",
            required    = true
    )
    private Integer id;

    @Column(name = "descricao", length = 100, nullable = false)
    @Schema(
            description = "Descrição do tipo de documento",
            example     = "CONTRATO",
            required    = true
    )
    private String descricao;
}
