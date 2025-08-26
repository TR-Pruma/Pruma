package com.br.pruma.application.dto.response;



import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "Dados retornados de um cronograma de projeto")
public record CronogramaResponseDTO (

        @Schema(description = "ID do cronograma", example = "1", required = true)
        Integer id,

        @Schema(description = "ID do projeto associado", example = "42", required = true)
        Integer projetoId,

        @Schema(description = "Data de início do cronograma", example = "2025-01-01", required = true)
        LocalDate dataInicio,

        @Schema(description = "Data de término do cronograma", example = "2025-03-31", required = true)
        LocalDate dataFim

) {}
