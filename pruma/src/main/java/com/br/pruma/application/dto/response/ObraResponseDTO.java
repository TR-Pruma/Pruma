package com.br.pruma.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Schema(
        name              = "ObraResponseDTO",
        description       = "Dados retornados de uma obra",
        requiredProperties = {
                "id",
                "projetoId",
                "descricao",
                "dataInicio",
                "version",
                "createdAt",
                "updatedAt"
        }
)
public class ObraResponseDTO {

    @Schema(
            description = "Identificador único da obra",
            example     = "10",
            required    = true
    )
    private Integer id;

    @Schema(
            description = "Identificador do projeto associado",
            example     = "5",
            required    = true
    )
    private Integer projetoId;

    @Schema(
            description = "Descrição da obra",
            example     = "Construção de alvenaria",
            required    = true
    )
    private String descricao;

    @Schema(
            description = "Data de início da obra (YYYY-MM-DD)",
            example     = "2025-09-01",
            required    = true
    )
    private LocalDate dataInicio;

    @Schema(
            description = "Data de término da obra (YYYY-MM-DD)",
            example     = "2025-12-31"
    )
    private LocalDate dataFim;

    @Schema(
            description = "Versão para controle otimista",
            example     = "1",
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
