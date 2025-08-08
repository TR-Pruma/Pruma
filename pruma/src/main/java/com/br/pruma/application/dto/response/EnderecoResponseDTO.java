package com.br.pruma.application.dto.response;

import java.time.LocalDateTime;

public record EnderecoResponseDTO(
        Integer id,
        String logradouro,
        String numero,
        String complemento,
        String bairro,
        String cidade,
        String uf,
        String cep,
        Boolean ativo,
        String empresaCnpj,
        LocalDateTime dataCriacao,
        LocalDateTime dataAtualizacao
) {}