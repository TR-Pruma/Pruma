package com.br.pruma.application.dto.response;

import lombok.Data;

@Data
public class AnexoResponseDTO {

    private Integer id;
    private Integer projeto;
    private String tipoAnexo;
    private String caminhoArquivo;
}

