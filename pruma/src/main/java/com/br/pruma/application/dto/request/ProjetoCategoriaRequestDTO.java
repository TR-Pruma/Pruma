package com.br.pruma.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(name = "ProjetoCategoriaRequestDTO", description = "Dados para criação/atualização de uma categoria de projeto")
public record ProjetoCategoriaRequestDTO(

        @NotBlank(message = "Nome é obrigatório")
        @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
        @Schema(description = "Nome da categoria", example = "RESIDENCIAL", required = true)
        String nome,

        @Size(max = 1000, message = "Descrição deve ter no máximo 1000 caracteres")
        @Schema(description = "Descrição da categoria", example = "Categoria para projetos residenciais", required = false)
        String descricao

) {}