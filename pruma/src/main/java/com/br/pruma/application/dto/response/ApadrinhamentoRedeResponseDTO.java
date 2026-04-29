package com.br.pruma.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(name = "ApadrinhamentoRedeResponseDTO",
        description = "Dados retornados de um vínculo de apadrinhamento")
public record ApadrinhamentoRedeResponseDTO(

        @Schema(description = "ID do apadrinhamento", example = "10")
        Long id,

        @Schema(description = "ID do profissional padrinho", example = "1")
        Long padrinhoId,

        @Schema(description = "Nome do profissional padrinho", example = "João da Silva")
        String padrinhoNome,

        @Schema(description = "ID do profissional afilhado", example = "2")
        Long afilhadoId,

        @Schema(description = "Nome do profissional afilhado", example = "Maria Souza")
        String afilhadoNome,

        @Schema(description = "Data de início do apadrinhamento", example = "2024-01-15")
        LocalDate dataInicio,

        @Schema(description = "Data de encerramento do apadrinhamento (null = ativo)", example = "2025-01-15")
        LocalDate dataFim,

        @Schema(description = "Status do vínculo: ATIVO | ENCERRADO", example = "ATIVO")
        String status,

        @Schema(description = "Data/hora de criação do registro")
        LocalDateTime createdAt,

        @Schema(description = "Data/hora da última atualização")
        LocalDateTime updatedAt

) {}