package com.br.pruma.application.dto.update;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ProjetoProfissionalUpdateDTO", description = "Dados para atualização parcial da associação entre projeto e profissional")
public record ProjetoProfissionalUpdateDTO(

        @Schema(description = "Identificador do projeto", example = "1")
        Integer projetoId,

        @Schema(description = "Identificador do profissional", example = "10")
        Integer profissionalId

) {}
