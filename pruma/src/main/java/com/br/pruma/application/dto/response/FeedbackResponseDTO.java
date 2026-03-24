package com.br.pruma.application.dto.response;

import java.time.LocalDateTime;

public record FeedbackResponseDTO(
        Integer id,
        Integer projetoId,
        Long clienteCpf,
        Integer tipoUsuarioId,
        String mensagem,
        LocalDateTime dataHora
) {}

