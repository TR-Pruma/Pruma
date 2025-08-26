package com.br.pruma.application.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InsumoFornecedorResponseDTO {
    /**
     * Identificador do insumo.
     */
    private Integer insumoId;

    /**
     * Identificador do fornecedor.
     */
    private Integer fornecedorId;

    /**
     * Preço negociado.
     */
    private BigDecimal preco;

    /**
     * Versão para controle de concorrência otimista.
     */
    private Long version;

}
