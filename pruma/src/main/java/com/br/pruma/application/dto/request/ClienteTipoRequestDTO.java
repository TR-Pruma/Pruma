package com.br.pruma.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteTipoRequestDTO {
    private Integer tipoUsuarioId;
    private String descricaoCliente;
}
