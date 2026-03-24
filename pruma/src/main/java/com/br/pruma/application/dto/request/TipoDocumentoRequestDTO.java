package com.br.pruma.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(
        name        = "TipoDocumentoRequestDTO",
        description = "Dados para criação ou atualização de um tipo de documento"
)
public record TipoDocumentoRequestDTO(

        @NotBlank(message = "descrição é obrigatória")
        @Size(max = 100, message = "descrição deve ter no máximo 100 caracteres")
        @Schema(
                description = "Descrição do tipo de documento",
                example     = "CONTRATO",
                required    = true
        )
        String descricao

) {}

