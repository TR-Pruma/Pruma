package com.br.pruma.application.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name        = "TipoDocumentoResponseDTO",
        description = "Dados de resposta de um tipo de documento"
)
public record TipoDocumentoResponseDTO(

        @Schema(
                description = "Identificador único do tipo de documento",
                example     = "5",
                required    = true
        )
        Integer id,

        @Schema(
                description = "Descrição do tipo de documento",
                example     = "CONTRATO",
                required    = true
        )
        String descricao

) {}

