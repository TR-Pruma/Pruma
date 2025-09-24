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
        name = "PreObraResponseDTO",
        description = "Dados retornados de um registro de pré-obra",
        requiredProperties = {"id", "obraId", "descricao"}
)
public class PreObraResponseDTO {

    @Schema(description = "Identificador único do pré-obra", example = "5", required = true)
    private Integer id;

    @Schema(description = "Identificador da obra associada", example = "10", required = true)
    private Integer obraId;

    @Schema(description = "Descrição da pré-obra", example = "Mobilização do canteiro", required = true)
    private String descricao;

    @Schema(description = "Data de início prevista (YYYY-MM-DD)", example = "2025-07-01")
    private LocalDate dataInicio;

    @Schema(description = "Timestamp de criação (ISO 8601)", example = "2025-06-01T10:15:30")
    private LocalDateTime createdAt;

    @Schema(description = "Timestamp da última atualização (ISO 8601)", example = "2025-06-05T08:00:00")
    private LocalDateTime updatedAt;

    @Schema(description = "Versão para controle otimista", example = "1")
    private Long version;
}

