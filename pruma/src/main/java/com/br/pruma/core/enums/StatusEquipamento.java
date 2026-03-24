package com.br.pruma.core.enums;

import lombok.Getter;

@Getter
public enum StatusEquipamento {
    ATIVO("Ativo"),
    INATIVO("Inativo"),
    EM_MANUTENCAO("Em manutenção"),
    DESCARTADO("Descartado");

    private final String descricao;

    StatusEquipamento(String descricao) {
        this.descricao = descricao;
    }

}
