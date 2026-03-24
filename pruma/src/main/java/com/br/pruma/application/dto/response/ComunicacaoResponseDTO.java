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
public class ComunicacaoResponseDTO {
    private Integer id;
    private Integer projetoId;
    private String projetoNome;
    private Integer clienteId;
    private String clienteNome;
    private String tipoRemetente;
    private String mensagem;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
}
