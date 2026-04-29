package com.br.pruma.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(name = "TarefaResponseDTO", description = "Dados de resposta de uma tarefa")
public record TarefaResponseDTO(

        @Schema(description = "ID da tarefa", example = "1")
        Integer id,

        @Schema(description = "ID da atividade vinculada", example = "1")
        Integer atividade,

        @Schema(description = "Descrição da tarefa", example = "Verificar instalação elétrica do pavimento térreo")
        String descricao,

        @Schema(description = "Status da tarefa", example = "PENDENTE")
        String status,

        @Schema(description = "Data de criação da tarefa", example = "2026-04-28")
        LocalDate dataCriacao,

        @Schema(description = "Data de conclusão da tarefa", example = "2026-05-10")
        LocalDate dataConclusao

) {}
