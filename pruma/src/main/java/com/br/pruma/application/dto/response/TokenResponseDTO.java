package com.br.pruma.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenResponseDTO {
    private String token;
    private String tipo;
    private String cpf;
    private String perfil;
}
