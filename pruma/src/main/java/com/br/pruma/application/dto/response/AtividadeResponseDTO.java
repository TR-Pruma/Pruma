package com.br.pruma.application.dto.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AtividadeResponseDTO {

    private Integer id;
    private Integer projeto;
    private String descricao;
    private String status;
    private LocalDate dataInicio;
    private LocalDate dataFim;
}
