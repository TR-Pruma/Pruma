package com.br.pruma.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ProjetoProfissionalResponseDTO", description = "Resposta com dados da associação entre projeto e profissional")
public record ProjetoProfissionalResponseDTO(

        @Schema(description = "Identificador da associação", example = "100", required = true)
        Integer id,

        @Schema(description = "Identificador do projeto", example = "1", required = true)
        Integer projetoId,

        @Schema(description = "Identificador do profissional", example = "10", required = true)
        Integer profissionalId

) {}

