package com.br.pruma.core.enums;

public enum StatusAtividade {
    PENDENTE,
    EM_ANDAMENTO,
    CONCLUIDA,
    CANCELADA;

    public String getLabel() {
        return name().replace("_", " ").toUpperCase();
    }
}
