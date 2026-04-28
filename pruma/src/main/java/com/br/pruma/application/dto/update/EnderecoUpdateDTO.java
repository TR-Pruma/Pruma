package com.br.pruma.application.dto.update;

import lombok.Data;

@Data
public class EnderecoUpdateDTO {

    private String cep;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
}
