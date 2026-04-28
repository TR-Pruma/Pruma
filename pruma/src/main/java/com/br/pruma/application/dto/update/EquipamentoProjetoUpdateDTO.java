package com.br.pruma.application.dto.update;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EquipamentoProjetoUpdateDTO {

    private Integer equipamentoId;
    private Integer projetoId;
    private LocalDate dataAlocacao;
    private LocalDate dataDesalocacao;
}
