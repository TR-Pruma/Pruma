package com.br.pruma.application.dto.update;

import lombok.Data;

@Data
public class UsuarioUpdateDTO {

    private String nome;
    private String email;
    private String telefone;
    private Integer tipoUsuarioId;
}
