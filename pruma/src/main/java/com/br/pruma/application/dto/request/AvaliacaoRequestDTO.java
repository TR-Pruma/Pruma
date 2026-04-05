package com.br.pruma.application.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

/**
 * DTO de entrada para criação e atualização de Avaliacao.
 * Nota deve estar entre 0.0 e 10.0.
 */
@Data
public class AvaliacaoRequestDTO {

    @NotNull(message = "O ID do projeto é obrigatório")
    private Integer projetoId;

    @NotBlank(message = "O CPF do cliente é obrigatório")
    private String clienteCpf;

    @NotNull(message = "A nota é obrigatória")
    @DecimalMin(value = "0.0", message = "A nota mínima é 0.0")
    @DecimalMax(value = "10.0", message = "A nota máxima é 10.0")
    private BigDecimal nota;

    @Size(max = 2000, message = "O comentário deve ter no máximo 2000 caracteres")
    private String comentario;
}
