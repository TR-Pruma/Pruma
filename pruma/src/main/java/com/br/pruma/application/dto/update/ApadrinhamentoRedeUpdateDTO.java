package com.br.pruma.application.dto.update;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(name = "ApadrinhamentoRedeUpdateDTO",
        description = "Dados para atualização de um vínculo de apadrinhamento")
public record ApadrinhamentoRedeUpdateDTO(

        @Schema(description = "Nova data de encerramento do apadrinhamento", example = "2025-06-30")
        LocalDate dataFim,

        @Schema(description = "Novo status do vínculo: ATIVO | ENCERRADO", example = "ENCERRADO",
                allowableValues = {"ATIVO", "ENCERRADO"})
        String status

) {}