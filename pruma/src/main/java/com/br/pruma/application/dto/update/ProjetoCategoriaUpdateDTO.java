package com.br.pruma.application.dto.update;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ProjetoCategoriaUpdateDTO", description = "Dados para atualização parcial de categoria de projeto")
public record ProjetoCategoriaUpdateDTO(

        @Schema(description = "Nome da categoria", example = "RESIDENCIAL")
        String nome,

        @Schema(description = "Descrição da categoria", example = "Categoria para projetos residenciais")
        String descricao

) {}
