package com.br.pruma.application.dto;

import lombok.Data;

@Data
public class ChecklistDTO {
    private Integer id;
    private String titulo;
    private Integer projetoId;
    private String status;
}
