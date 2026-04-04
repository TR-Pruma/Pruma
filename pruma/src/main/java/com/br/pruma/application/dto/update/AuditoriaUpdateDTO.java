package com.br.pruma.application.dto.update;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AuditoriaUpdateDTO {

    private String entidade;

    private String acao;

    private String usuario;

    private LocalDateTime dataHora;
}
