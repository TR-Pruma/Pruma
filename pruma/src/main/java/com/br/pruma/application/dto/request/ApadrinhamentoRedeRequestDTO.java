package com.br.pruma.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Schema(name = "ApadrinhamentoRedeRequestDTO",
        description = "Dados para criação de um vínculo de apadrinhamento entre profissionais")
public record ApadrinhamentoRedeRequestDTO(

        @NotNull(message = "padrinhoId é obrigatório")
        @Schema(description = "ID do profissional padrinho", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        Long padrinhoId,

        @NotNull(message = "afilhadoId é obrigatório")
        @Schema(description = "ID do profissional afilhado", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
        Long afilhadoId,

        @NotNull(message = "dataInicio é obrigatória")
        @Schema(description = "Data de início do apadrinhamento", example = "2024-01-15", requiredMode = Schema.RequiredMode.REQUIRED)
        LocalDate dataInicio,

        @Schema(description = "Data de encerramento do apadrinhamento (null = ativo)", example = "2025-01-15")
        LocalDate dataFim,

        @Schema(description = "Status do vínculo: ATIVO | ENCERRADO", example = "ATIVO",
                allowableValues = {"ATIVO", "ENCERRADO"})
        String status

) {}