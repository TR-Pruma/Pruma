package com.br.pruma.application.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AuditoriaRequestDTO {
    private String entidade;
    private String acao;
    private String usuario;
    private LocalDateTime dataHora;

}
