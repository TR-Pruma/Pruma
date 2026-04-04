package com.br.pruma.application.dto.update;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CronogramaUpdateDTO {
    private Integer projetoId;
    private String descricao;
    private LocalDate dataInicio;
    private LocalDate dataFim;
}
