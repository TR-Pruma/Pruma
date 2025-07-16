package com.br.pruma.application.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AtividadeRequestDTO {
    private Integer projeto;
    private String descricao;
    private String status;
    private LocalDate dataInicio;
    private LocalDate dataFim;
}