package com.br.pruma.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO de resposta de autenticação.
 * Não expõe CPF por conformidade com LGPD.
 */
@Data
@AllArgsConstructor
public class TokenResponseDTO {
    private String token;
    private String tipo;
    private Integer usuarioId;
    private String perfil;
}
