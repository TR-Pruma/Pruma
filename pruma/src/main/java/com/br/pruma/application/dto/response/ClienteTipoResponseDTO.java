package com.br.pruma.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteTipoResponseDTO {
    private Integer id;
    private Integer tipoUsuarioId;
    private String descricaoCliente;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    private Long versao;
    private Boolean ativo;
}

