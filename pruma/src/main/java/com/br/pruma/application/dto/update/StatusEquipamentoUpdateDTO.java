package com.br.pruma.application.dto.update;

import lombok.Data;

@Data
public class StatusEquipamentoUpdateDTO {
    private Integer equipamentoId;
    private String status;
    private String observacao;
}
