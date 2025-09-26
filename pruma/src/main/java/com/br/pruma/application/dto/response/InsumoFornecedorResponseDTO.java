package com.br.pruma.application.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InsumoFornecedorResponseDTO {

    private Integer insumoId;
    private Integer fornecedorId;
    private BigDecimal preco;
    private Long version;
}
