package com.br.pruma.application.dto.response;

public record ComunicacaoResponseDTO(
        Integer id,
        Integer projetoId,
        String projetoNome,
        Integer clienteId,
        String clienteNome,
        String tipoRemetente,
        String mensagem,
        Boolean ativo
) {}
