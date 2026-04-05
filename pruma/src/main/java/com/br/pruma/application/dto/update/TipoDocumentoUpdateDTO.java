package com.br.pruma.application.dto.update;

import lombok.Data;

@Data
public class TipoDocumentoUpdateDTO {

    private String nome;
    private String descricao;
    private Boolean obrigatorio;
}
