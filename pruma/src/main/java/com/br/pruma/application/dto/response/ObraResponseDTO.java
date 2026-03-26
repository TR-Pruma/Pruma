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
        name = "ObraResponseDTO",
        description = "Dados retornados de uma Obra",
        requiredProperties = {"id", "projetoId", "descricao", "dataInicio", "createdAt", "updatedAt", "version"}
)
public class ObraResponseDTO {

    @Schema(description = "Identificador único da obra", example = "7", required = true)
    private Integer id;

    @Schema(description = "Identificador do projeto associado", example = "12", required = true)
    private Integer projetoId;

    @Schema(description = "Descrição da obra", example = "Reforma de escola municipal", required = true)
    private String descricao;

    @Schema(description = "Data de início da obra (YYYY-MM-DD)", example = "2025-06-01", required = true)
    private LocalDate dataInicio;

    @Schema(description = "Data de fim prevista (YYYY-MM-DD)", example = "2025-12-15")
    private LocalDate dataFim;

    @Schema(description = "Timestamp de criação (ISO 8601)", example = "2025-06-01T10:15:30", required = true)
    private LocalDateTime createdAt;

    @Schema(description = "Timestamp da última atualização (ISO 8601)", example = "2025-06-05T08:00:00", required = true)
    private LocalDateTime updatedAt;

    @Schema(description = "Versão para controle otimista", example = "1", required = true)
    private Long version;
}
