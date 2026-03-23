package com.br.pruma.core.enums;

/**
 * Status possíveis de uma solicitação de mudança.
 * Substitui a entidade JPA StatusSolicitacao — preferir enum para domínios fixos.
 * ATENÇÃO: a entidade StatusSolicitacao.java foi mantida por compatibilidade de schema,
 * mas novas referências devem usar este enum.
 */
public enum StatusSolicitacaoEnum {
    PENDENTE,
    EM_ANALISE,
    APROVADA,
    REJEITADA,
    CANCELADA
}
