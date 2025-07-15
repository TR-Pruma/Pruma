package com.br.pruma.application.dto;

import lombok.Data;

@Data
public class AtividadeDTO {
    private Integer id;
    private String nome;
    private String descricao;
    private Integer responsavelId;
    private String dataInicio;
    private String dataFim;
    private String status;
}
