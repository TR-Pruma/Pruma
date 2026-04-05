package com.br.pruma.application.dto.update;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "PermissaoUsuarioUpdateDTO", description = "Dados para atualização parcial de permissão de usuário")
public record PermissaoUsuarioUpdateDTO(

        @Schema(description = "ID do tipo de usuário", example = "2")
        Integer tipoUsuarioId,

        @Schema(description = "Permissão do usuário", example = "ADMIN")
        String permissao

) {}
