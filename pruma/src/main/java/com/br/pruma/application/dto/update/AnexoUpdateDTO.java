package com.br.pruma.application.dto.update;

import lombok.Data;

@Data
public class AnexoUpdateDTO {

    private String nome;
    private String descricao;
    private String url;
    private String tipo;
    private Long tamanho;
}
