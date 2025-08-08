package com.br.pruma.application.dto.response;

import java.util.List;

public record EmpresaResponseDTO(
        String cnpj,
        String razaoSocial,
        String nomeFantasia,
        List<EnderecoResponseDTO> enderecos
) {}