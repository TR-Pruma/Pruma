package com.br.pruma.application.dto.update;

import lombok.Data;

import java.time.LocalDate;

@Data
public class FaseCronogramaUpdateDTO {

    private String nome;
    private String descricao;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private Integer cronogramaId;
}
