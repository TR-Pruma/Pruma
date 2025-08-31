package com.br.pruma.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Schema(
        name        = "MaterialUtilizadoResponseDTO",
        description = "Dados retornados da associação de um material utilizado em uma atividade",
        requiredProperties = {
                "id",
                "materialId",
                "atividadeId",
                "quantidadeUtilizada",
                "version",
                "createdAt",
                "updatedAt"
        }
)
public class MaterialUtilizadoResponseDTO {

    @Schema(
            description = "Identificador único da associação material-atividade",
            example     = "3",
            required    = true
    )
    private Integer id;

    @Schema(
            description = "Identificador do material utilizado",
            example     = "10",
            required    = true
    )
    private Integer materialId;

    @Schema(
            description = "Identificador da atividade onde o material foi usado",
            example     = "5",
            required    = true
    )
    private Integer atividadeId;

    @Schema(
            description = "Quantidade de material utilizada na atividade",
            example     = "20",
            required    = true
    )
    private Integer quantidadeUtilizada;

    @Schema(
            description = "Versão para controle otimista",
            example     = "2",
            required    = true
    )
    private Long version;

    @Schema(
            description = "Timestamp de criação do registro (ISO 8601)",
            example     = "2025-08-27T10:15:30",
            required    = true
    )
    private LocalDateTime createdAt;

    @Schema(
            description = "Timestamp da última atualização do registro (ISO 8601)",
            example     = "2025-08-28T14:05:00",
            required    = true
    )
    private LocalDateTime updatedAt;
}

