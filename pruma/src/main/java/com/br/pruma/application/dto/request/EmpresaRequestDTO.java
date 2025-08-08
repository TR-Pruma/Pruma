package com.br.pruma.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record EmpresaRequestDTO(  @NotBlank @Size(min = 14, max = 14) String cnpj,
                                  @NotBlank @Size(max = 50) String razaoSocial,
                                  @NotBlank @Size(max = 50) String nomeFantasia,
                                  List<EnderecoRequestDTO> enderecos) {
}
