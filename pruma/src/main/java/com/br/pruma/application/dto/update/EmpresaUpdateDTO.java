package com.br.pruma.application.dto.update;

import lombok.Data;

@Data
public class EmpresaUpdateDTO {

    private String nome;
    private String cnpj;
    private String email;
    private String telefone;
}
