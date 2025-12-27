package com.br.pruma.application.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

@Schema(name = "RequisicaoMaterialRequestDTO", description = "Dados para criação/atualização de requisição de material")
public record RequisicaoMaterialRequestDTO(

        @NotNull(message = "obraId é obrigatório")
        @Schema(description = "Identificador da obra relacionada", example = "1", required = true)
        Integer obraId,

        @NotNull(message = "materialId é obrigatório")
        @Schema(description = "Identificador do material requisitado", example = "10", required = true)
        Integer materialId,

        @NotNull(message = "Quantidade é obrigatória")
        @Positive(message = "Quantidade deve ser maior que zero")
        @Schema(description = "Quantidade de material solicitado", example = "100", required = true)
        Integer quantidade,

        @NotNull(message = "Data de requisição é obrigatória")
        @Schema(description = "Data da requisição do material", example = "2025-12-20", required = true)
        LocalDate dataRequisicao

) {}
