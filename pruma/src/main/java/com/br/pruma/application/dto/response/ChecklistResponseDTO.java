package com.br.pruma.application.dto.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ChecklistResponseDTO {
    private Integer id;
    private String nome;
    private String descricao;
    private LocalDate data;
    private String status;
}
