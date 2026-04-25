package com.br.pruma.core.enums;

/**
 * Enum de classificacao de autenticacao/autorizacao do Usuario.
 * Usado em Usuario.tipo com @Enumerated(EnumType.STRING).
 *
 * Nao confundir com TipoUsuario (@Entity), que representa a tabela tipo_usuario
 * e e usado como FK em ClienteTipo, Auditoria, PermissaoUsuario, etc.
 */
public enum TipoUsuarioEnum {
    CLIENTE,
    PROFISSIONAL,
    TECNICO,
    EMPRESA,
    ADMIN
}
