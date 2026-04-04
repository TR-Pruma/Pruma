package com.br.pruma.application.dto.update;

import com.br.pruma.core.enums.TipoUsuarioEnum;
import jakarta.validation.constraints.Size;

public record UsuarioUpdateDTO(

        @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres")
        String senha,

        TipoUsuarioEnum tipo,

        Boolean ativo
) {}
