package com.br.pruma.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Schema(name = "TarefaRequestDTO", description = "Dados para criação de uma tarefa")
public record TarefaRequestDTO(

        @NotNull(message = "O ID da atividade é obrigatório")
        @Schema(description = "ID da atividade vinculada", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        Integer atividadeId,

        @NotBlank(message = "A descrição da tarefa é obrigatória")
        @Size(max = 2000, message = "A descrição deve ter no máximo 2000 caracteres")
        @Schema(description = "Descrição detalhada da tarefa", example = "Verificar instalação elétrica do pavimento térreo", requiredMode = Schema.RequiredMode.REQUIRED)
        String descricao,

        @NotBlank(message = "O status é obrigatório")
        @Schema(description = "Status da tarefa (valor do enum StatusTarefa)", example = "PENDENTE", requiredMode = Schema.RequiredMode.REQUIRED)
        String status,

        @NotNull(message = "A data de criação é obrigatória")
        @Schema(description = "Data de criação da tarefa", example = "2026-04-28", requiredMode = Schema.RequiredMode.REQUIRED)
        LocalDate dataCriacao,

        @Schema(description = "Data de conclusão da tarefa", example = "2026-05-10")
        LocalDate dataConclusao

) {}
