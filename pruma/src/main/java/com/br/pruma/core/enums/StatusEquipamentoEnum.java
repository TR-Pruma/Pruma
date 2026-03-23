package com.br.pruma.core.enums;

/**
 * Status possíveis de um equipamento.
 * Substitui a entidade JPA StatusEquipamento — preferir enum para domínios fixos.
 * ATENÇÃO: a entidade StatusEquipamento.java foi mantida por compatibilidade de schema,
 * mas novas referências devem usar este enum.
 */
public enum StatusEquipamentoEnum {
    DISPONIVEL,
    ALOCADO,
    EM_MANUTENCAO,
    INATIVO
}
