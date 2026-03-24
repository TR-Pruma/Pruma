package com.br.pruma.core.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Status do orçamento", example = "PENDENTE")
public enum StatusOrcamento {
    PENDENTE("PENDENTE"),
    APROVADO("APROVADO"),
    REJEITADO("REJEITADO");

    private final String value;

    StatusOrcamento(String value) {
        this.value = value;
    }

    /**
     * Retorna o valor que será serializado em JSON.
     */
    @JsonValue
    public String getValue() {
        return value;
    }

    /**
     * Converte uma string em enum, ignorando case.
     * @param value texto recebido
     * @return StatusOrcamento correspondente
     * @throws IllegalArgumentException se não houver correspondência
     */
    @JsonCreator
    public static StatusOrcamento fromValue(String value) {
        for (StatusOrcamento status : values()) {
            if (status.value.equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("StatusOrcamento inválido: " + value);
    }

}
