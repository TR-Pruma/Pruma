package com.br.pruma.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

/**
 * DTO de entrada para criação e atualização de Tarefa.
 * Status deve ser um valor válido de {@code StatusTarefa}.
 */
@Data
public class TarefaRequestDTO {

    @NotNull(message = "O ID da atividade é obrigatório")
    private Integer atividadeId;

    @NotBlank(message = "A descrição da tarefa é obrigatória")
    @Size(max = 2000, message = "A descrição deve ter no máximo 2000 caracteres")
    private String descricao;

    @NotBlank(message = "O status é obrigatório")
    private String status;

    @NotNull(message = "A data de criação é obrigatória")
    private LocalDate dataCriacao;

    private LocalDate dataConclusao;
}
