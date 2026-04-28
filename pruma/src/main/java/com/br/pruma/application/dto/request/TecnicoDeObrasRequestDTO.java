package com.br.pruma.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * DTO de entrada para criação e atualização de TecnicoDeObras.
 */
@Data
public class TecnicoDeObrasRequestDTO {

    @NotBlank(message = "O nome do técnico é obrigatório")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
    private String nome;

    @Size(max = 50, message = "A especialidade deve ter no máximo 50 caracteres")
    private String especialidade;

    @Pattern(
            regexp = "^[\\d\\s()+-]{7,20}$",
            message = "Telefone inválido. Use apenas dígitos, espaços e os caracteres ( ) + -"
    )
    private String telefone;
}
