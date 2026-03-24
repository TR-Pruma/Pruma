package com.br.pruma.application.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AtividadeRequestDTO {

    @NotNull(message = "O projeto é obrigatório")
    private Integer projeto;

    @NotBlank(message = "A descrição é obrigatória")
    private String descricao;

    @NotBlank(message = "O status é obrigatório")
    private String status;

    @NotNull(message = "A data de início é obrigatória")
    private LocalDate dataInicio;

    @NotNull(message = "A data de fim é obrigatória")
    private LocalDate dataFim;
}