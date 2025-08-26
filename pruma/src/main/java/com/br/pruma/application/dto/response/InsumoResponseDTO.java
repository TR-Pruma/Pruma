package com.br.pruma.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Dados retornados de um insumo")
public record InsumoResponseDTO(

        @Schema(description = "ID do insumo", example = "1", required = true)
        Integer id,

        @Schema(description = "Nome do insumo", example = "Parafuso", required = true)
        String nome,

        @Schema(description = "Descrição detalhada", example = "Parafuso de aço inox")
        String descricao,

        @Schema(description = "Unidade de medida", example = "UN", required = true)
        String unidadeMedida,

        @Schema(description = "Custo unitário", example = "0.50", required = true)
        BigDecimal custo

) {}
