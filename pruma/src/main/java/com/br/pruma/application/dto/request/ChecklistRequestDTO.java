package com.br.pruma.application.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ChecklistRequestDTO {

    private String nome;
    private String descricao;
    private LocalDate data;
    private String status;
}
