package com.br.pruma.application.dto.response;

import com.br.pruma.core.enums.TipoUsuarioEnum;

import java.time.LocalDateTime;

public record UsuarioResponseDTO(
        Integer id,
        String cpf,
        TipoUsuarioEnum tipo,
        Boolean ativo,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
