package com.br.pruma.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ProjetoCategoriaResponseDTO", description = "Resposta com dados da categoria de projeto")
public record ProjetoCategoriaResponseDTO(

        @Schema(description = "Identificador da categoria", example = "1", required = true)
        Integer id,

        @Schema(description = "Nome da categoria", example = "RESIDENCIAL", required = true)
        String nome,

        @Schema(description = "Descrição da categoria", example = "Categoria para projetos residenciais", required = false)
        String descricao

) {}