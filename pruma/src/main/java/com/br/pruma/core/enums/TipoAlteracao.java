package com.br.pruma.core.enums;

/**
 * Tipos de alteração registrados em log.
 * Substitui o campo String tipoAlteracao de LogAlteracaoAux.
 */
public enum TipoAlteracao {
    CRIACAO,
    ATUALIZACAO,
    EXCLUSAO,
    RESTAURACAO,
    APROVACAO,
    REJEICAO
}
