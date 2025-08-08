package com.br.pruma.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record EnderecoRequestDTO(
        @NotBlank String logradouro,
        @NotBlank String numero,
        String complemento,
        @NotBlank String bairro,
        @NotBlank String cidade,
        @NotBlank @Size(min = 2, max = 2) String uf,
        @NotBlank @Pattern(regexp = "\\d{8}") String cep,
        Boolean ativo,
        @NotBlank String empresaCnpj
) {}
