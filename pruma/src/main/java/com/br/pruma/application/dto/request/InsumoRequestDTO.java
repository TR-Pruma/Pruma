package com.br.pruma.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Schema(description = "Dados para criar/atualizar um insumo")
public record InsumoRequestDTO(

        @NotBlank
        @Size(max = 255)
        @Schema(description = "Nome do insumo", example = "Parafuso", required = true)
        String nome,

        @Size(max = 2000)
        @Schema(description = "Descrição detalhada", example = "Parafuso de aço inox")
        String descricao,

        @NotNull
        @Schema(description = "Unidade de medida", example = "UN", required = true)
        String unidadeMedida,

        @NotNull
        @Positive
        @Schema(description = "Custo unitário", example = "0.50", required = true)
        BigDecimal custo

) {}
