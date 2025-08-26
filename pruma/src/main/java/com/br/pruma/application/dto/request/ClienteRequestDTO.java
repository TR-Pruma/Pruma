package com.br.pruma.application.dto.request;

import jakarta.validation.constraints.*;


public record ClienteRequestDTO (
        @NotBlank(message = "{cliente.cpf}")
        @Size(min = 11, max = 11, message = "{cliente.cpf.size}")
        String cpf,

        @NotBlank(message = "{cliente.nome}")
        String nome,

        @NotBlank(message = "{cliente.email}")
        @Email(message = "{cliente.email.valid}")
        String email,

        @NotBlank(message = "{cliente.telefone}")
        String telefone,

        @NotNull(message = "{cliente.enderecoId}")
        Integer enderecoId,

        @NotBlank(message = "{cliente.senha}")
        String senha,

        @NotNull(message = "{cliente.ativo}")
        Boolean ativo
) {}


