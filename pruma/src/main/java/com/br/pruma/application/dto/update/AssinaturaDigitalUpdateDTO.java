package com.br.pruma.application.dto.update;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AssinaturaDigitalUpdateDTO {

    private String hashAssinatura;
    private LocalDateTime dataAssinatura;
}
