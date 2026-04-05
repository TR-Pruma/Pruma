package com.br.pruma.application.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FornecedorResponseDTO {
    private Integer id;
    private String nome;
    private String cnpj;
    private String contato;
}
