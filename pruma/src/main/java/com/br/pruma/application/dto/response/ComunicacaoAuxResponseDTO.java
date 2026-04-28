package com.br.pruma.application.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record ComunicacaoAuxResponseDTO(
        Integer comunicacaoId,
        String tipoMensagem,

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime dataCriacao,

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime dataAtualizacao,

        Long versao,
        Boolean ativo,

        String mensagem,
        String tipoRemetente,
        Integer projetoId,
        String projetoNome,
        Integer clienteId,
        String clienteNome
) {}
