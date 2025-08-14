package com.br.pruma.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CronogramaRequestDTO {

    @NotNull(message = "Projeto é obrigatório")
    @Schema(description = "ID do projeto associado")
    private Integer projetoId;

    @NotNull(message = "Data de início é obrigatória")
    @Schema(description = "Data de início do cronograma", example = "2025-01-01")
    private LocalDate dataInicio;

    @NotNull(message = "Data de fim é obrigatória")
    @Schema(description = "Data de término do cronograma", example = "2025-03-31")
    private LocalDate dataFim;
}