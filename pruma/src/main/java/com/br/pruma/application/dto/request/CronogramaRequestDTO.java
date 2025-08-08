package com.br.pruma.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados para criação/atualização de um cronograma")
public class CronogramaRequestDTO {

    @NotNull(message = "O ID do projeto é obrigatório")
    @Schema(description = "ID do projeto", example = "1")
    private Integer projetoId;
    @NotNull(message = "A data de início é obrigatória")
    @Schema(description = "Data de início do cronograma", example = "2025-08-01")
    private LocalDate dataInicio;

    @NotNull(message = "A data de fim é obrigatória")
    @Schema(description = "Data de fim do cronograma", example = "2025-12-31")
    private LocalDate dataFim;

    @Schema(description = "Descrição do cronograma", example = "Cronograma de implementação do sistema")
    private String descricao;
}

