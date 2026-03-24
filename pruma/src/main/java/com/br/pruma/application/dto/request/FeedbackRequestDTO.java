package com.br.pruma.application.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FeedbackRequestDTO(
        @NotNull Integer projetoId,
        @NotNull Long clienteCpf,
        @NotNull Integer tipoUsuarioId,
        @NotBlank String mensagem
) {}

