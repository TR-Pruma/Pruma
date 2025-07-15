package com.br.pruma.application.dto;

import lombok.Data;

@Data
public class AnexoDTO {

    private Integer id;
    private Integer projeto;
    private String tipoAnexo;
    private String caminhoArquivo;
}
