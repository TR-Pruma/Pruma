package com.br.pruma.application.dto.update;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LogAlteracaoUpdateDTO {

    private String entidade;
    private String campo;
    private String valorAnterior;
    private String valorNovo;
    private String usuario;
    private LocalDateTime dataAlteracao;
}
