package com.br.pruma.application.dto.request;

import com.br.pruma.core.enums.TipoUsuarioEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UsuarioRequestDTO(

        @NotBlank
        @Size(min = 11, max = 11, message = "CPF deve ter exatamente 11 dígitos")
        String cpf,

        @NotBlank
        @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres")
        String senha,

        @NotNull
        TipoUsuarioEnum tipo
) {}
