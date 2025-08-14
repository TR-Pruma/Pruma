package com.br.pruma.application.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class FaseCronogramaRequestDTO {

    @NotNull
    private Long cronogramaId;

    @NotBlank
    private String nome;

    private String descricao;

    @NotNull
    private LocalDate dataInicio;

    @NotNull
    private LocalDate dataFim;

}
