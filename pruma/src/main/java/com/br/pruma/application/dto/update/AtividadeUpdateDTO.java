package com.br.pruma.application.dto.update;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AtividadeUpdateDTO {
    private Integer projeto;
    private String descricao;
    private String status;
    private LocalDate dataInicio;
    private LocalDate dataFim;
}
