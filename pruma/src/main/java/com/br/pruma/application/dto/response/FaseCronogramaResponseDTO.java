package com.br.pruma.application.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class FaseCronogramaResponseDTO {

    private Integer id;
    private Long cronogramaId;
    private String cronogramaNome;
    private String nome;
    private String descricao;
    private LocalDate dataInicio;
    private LocalDate dataFim;

}
