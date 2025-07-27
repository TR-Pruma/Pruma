package com.br.pruma.application.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AssinaturaDigitalResponseDTO {

    private Integer id;
    private Long clienteCpf;
    private Integer tipoUsuarioId;
    private Integer documentoId;
    private LocalDateTime dataHora;
}
