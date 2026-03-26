package com.br.pruma.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;

@Schema(name = "StatusEquipamentoResponseDTO", description = "Dados de status de equipamento retornado")
public record StatusEquipamentoResponseDTO(

        @Schema(description = "Identificador único do status", example = "1", required = true)
        Integer id,

        @Schema(description = "Descrição do status de equipamento", example = "Operacional", required = true)
        String descricao,

        @Schema(description = "Data de criação do registro no sistema", example = "2025-12-21", required = true)
        LocalDate createdAt

) {}

