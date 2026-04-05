package com.br.pruma.application.dto.update;

import lombok.Data;

@Data
public class ClienteUpdateDTO {
    private String nome;
    private String email;
    private String telefone;
    private String cpfCnpj;
    private Boolean ativo;
}
