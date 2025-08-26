package com.br.pruma.application.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InsumoFornecedorRequestDTO {
    /**
     * Identificador do insumo.
     */
    @NotNull(message = "insumoId é obrigatório")
    private Integer insumoId;

    /**
     * Identificador do fornecedor.
     */
    @NotNull(message = "fornecedorId é obrigatório")
    private Integer fornecedorId;

    /**
     * Preço negociado do insumo com o fornecedor.
     */
    @NotNull(message = "preco é obrigatório")
    @DecimalMin(value = "0.0", inclusive = true, message = "preco deve ser >= 0.0")
    private BigDecimal preco;

}
