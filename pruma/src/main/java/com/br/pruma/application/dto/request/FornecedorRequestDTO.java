package com.br.pruma.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

public class FornecedorRequestDTO {

    @Getter
    @NotBlank
    private String nome;

    @Getter
    @NotBlank
    @Pattern(regexp = "\\d{14}", message = "CNPJ deve conter 14 dígitos numéricos")
    private String cnpj;

    @NotBlank
    private String contato;

    public String getContato() {
        return this.contato;
    }
}