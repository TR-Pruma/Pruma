package com.br.pruma.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PermissaoUsuarioRequestDTO {
    @NotNull
    private String clienteCpf;      // CPF do cliente

    @NotNull
    private Integer tipoUsuarioId;  // ID do tipo de usuário

    @NotBlank
    private String permissao;       // Ex: "ADMIN", "USER"
}
