package com.br.pruma.application.dto.update;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DocumentoUpdateDTO {

    private String numero;
    private String descricao;
    private String urlArquivo;
    private LocalDate dataEmissao;
    private LocalDate dataVencimento;
    private Integer tipoDocumentoId;
}
