package com.br.pruma.application.dto;

import lombok.Data;

@Data
public class AuditoriaDTO {
    private Integer id;
    private String entidade;
    private String acao;
    private String data;
    private String usuario;
    private String valorAnterior;
    private String valorNovo;
}
