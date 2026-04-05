package com.br.pruma.application.dto.update;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ComunicacaoUpdateDTO {

    private String assunto;
    private String mensagem;
    private String tipo;
    private LocalDateTime dataEnvio;
    private Boolean lido;
}
