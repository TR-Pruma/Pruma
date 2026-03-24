package com.br.pruma.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(name = "ProjetoProfissionalRequestDTO", description = "Dados para associar um profissional a um projeto")
public record ProjetoProfissionalRequestDTO(

        @NotNull(message = "projetoId é obrigatório")
        @Schema(description = "Identificador do projeto", example = "1", required = true)
        Integer projetoId,

        @NotNull(message = "profissionalId é obrigatório")
        @Schema(description = "Identificador do profissional", example = "10", required = true)
        Integer profissionalId

) {}