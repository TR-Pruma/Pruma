package com.br.pruma.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Schema(description = "Dados para criar ou atualizar um cronograma de projeto")
public record CronogramaRequestDTO (

        @NotNull(message = "Projeto é obrigatório")
        @Schema(description = "ID do projeto associado", example = "42", required = true)
        Integer projetoId,

        @NotNull(message = "Data de início é obrigatória")
        @FutureOrPresent(message = "Data de início deve ser hoje ou uma data futura")
        @Schema(description = "Data de início do cronograma", example = "2025-01-01", required = true)
        LocalDate dataInicio,

        @NotNull(message = "Data de fim é obrigatória")
        @Future(message = "Data de fim deve ser uma data futura")
        @Schema(description = "Data de término do cronograma", example = "2025-03-31", required = true)
        LocalDate dataFim

) {}
