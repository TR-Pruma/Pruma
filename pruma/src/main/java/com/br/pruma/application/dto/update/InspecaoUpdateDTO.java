package com.br.pruma.application.dto.update;

import lombok.Data;

import java.time.LocalDate;

@Data
public class InspecaoUpdateDTO {
    private Integer obraId;
    private String descricao;
    private String resultado;
    private LocalDate dataInspecao;
}
