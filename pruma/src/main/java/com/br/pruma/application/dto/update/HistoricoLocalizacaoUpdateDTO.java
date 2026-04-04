package com.br.pruma.application.dto.update;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HistoricoLocalizacaoUpdateDTO {

    private Double latitude;
    private Double longitude;
    private LocalDateTime dataHora;
}
