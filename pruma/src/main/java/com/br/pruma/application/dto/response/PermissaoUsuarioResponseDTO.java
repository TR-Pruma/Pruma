package com.br.pruma.application.dto.response;


import lombok.Data;

@Data
public class PermissaoUsuarioResponseDTO {

    private Long id;
    private String clienteCpf;
    private String clienteNome;      // opcional: exibir o nome do cliente
    private Integer tipoUsuarioId;
    private String tipoUsuarioDescricao; // opcional: descrição do tipo
    private String permissao;
}
