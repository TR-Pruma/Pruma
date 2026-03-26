package com.br.pruma.core.enums;

public enum UnidadeMedida {
    UN,    // Unidade
    KG,    // Quilograma
    L,     // Litro
    M2,    // Metro quadrado
    M3;    // Metro cúbico

    public static UnidadeMedida of(String code) {
        for (UnidadeMedida um : values()) {
            if (um.name().equalsIgnoreCase(code)) {
                return um;
            }
        }
        throw new IllegalArgumentException("Unidade de medida inválida: " + code);
    }

}
