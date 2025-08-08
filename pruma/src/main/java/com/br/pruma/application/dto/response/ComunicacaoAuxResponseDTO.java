package com.br.pruma.application.dto.response;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComunicacaoAuxResponseDTO {
    private Integer comunicacaoId;
    private String tipoMensagem;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataCriacao;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataAtualizacao;

    private Long versao;
    private Boolean ativo;

    // Dados da comunicação relacionada
    private String mensagem;
    private String tipoRemetente;
    private Integer projetoId;
    private String projetoNome;
    private Integer clienteId;
    private String clienteNome;
}

