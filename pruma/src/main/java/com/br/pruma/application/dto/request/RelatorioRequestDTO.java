package com.br.pruma.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Schema(name = "RelatorioRequestDTO", description = "Dados para criação/atualização de relatório")
public record RelatorioRequestDTO(

        @NotNull(message = "obraId é obrigatório")
        @Schema(description = "Identificador da obra relacionada", example = "5", required = true)
        Integer obraId,

        @Size(max = 5000, message = "Descrição pode ter no máximo 5000 caracteres")
        @Schema(description = "Descrição detalhada do relatório", example = "Relatório técnico da obra")
        String descricao,

        @NotNull(message = "Data de criação é obrigatória")
        @Schema(description = "Data de criação lógica do relatório", example = "2025-12-07", required = true)
        LocalDate dataCriacao

) {}

