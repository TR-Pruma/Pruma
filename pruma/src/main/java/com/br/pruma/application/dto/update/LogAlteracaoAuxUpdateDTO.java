package com.br.pruma.application.dto.update;

import lombok.Data;

@Data
public class LogAlteracaoAuxUpdateDTO {

    private String campo;
    private String valorAnterior;
    private String valorNovo;
    private Integer logAlteracaoId;
}
