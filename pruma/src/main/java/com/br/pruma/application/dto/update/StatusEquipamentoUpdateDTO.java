package com.br.pruma.application.dto.update;


public record StatusEquipamentoUpdateDTO (
    Integer equipamentoId,
    String status,
    String observacao
){}
