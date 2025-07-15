package com.br.pruma.application.dto;

import lombok.Data;

@Data
public class AvaliacaoDTO {
    private Integer id;
    private Integer projetoId;
    private Integer avaliadorId;
    private String comentario;
    private Integer nota;
}

