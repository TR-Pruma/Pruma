package com.br.pruma.application.dto;

import lombok.Data;

@Data
public class AssinaturaDigitalDTO {
    private Integer id;
    private Integer usuario;
    private String tipoAssinatura;
    private String imagemAssinatura;
}
