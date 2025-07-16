package com.br.pruma.application.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AuditoriaResponseDTO {
    private Integer id;
    private String entidade;
    private String acao;
    private String usuario;
    private LocalDateTime dataHora;
}
