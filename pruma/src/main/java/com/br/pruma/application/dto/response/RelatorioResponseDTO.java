package com.br.pruma.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(name = "RelatorioResponseDTO", description = "Resposta com dados do relatório")
public record RelatorioResponseDTO(

        @Schema(description = "Identificador do relatório", example = "100", required = true)
        Integer id,

        @Schema(description = "Identificador da obra relacionada", example = "5", required = true)
        Integer obraId,

        @Schema(description = "Descrição detalhada do relatório", example = "Relatório técnico da obra")
        String descricao,

        @Schema(description = "Data de criação lógica do relatório", example = "2025-12-07", required = true)
        LocalDate dataCriacao

) {}

