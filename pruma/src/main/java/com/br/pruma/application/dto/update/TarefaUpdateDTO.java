package com.br.pruma.application.dto.update;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Schema(name = "TarefaUpdateDTO", description = "Dados para atualização de uma tarefa")
public record TarefaUpdateDTO(

        @Size(max = 2000, message = "A descrição deve ter no máximo 2000 caracteres")
        @Schema(description = "Nova descrição da tarefa", example = "Verificar instalação elétrica do pavimento térreo e superior")
        String descricao,

        @Schema(description = "Novo status da tarefa (valor do enum StatusTarefa)", example = "EM_ANDAMENTO")
        String status,

        @Schema(description = "Nova data de conclusão da tarefa", example = "2026-05-15")
        LocalDate dataConclusao

) {}
