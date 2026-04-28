package com.br.pruma.application.dto.update;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class InsumoFornecedorUpdateDTO {

    private Integer insumoId;
    private Integer fornecedorId;
    private BigDecimal precoNegociado;
    private Integer prazoEntregaDias;
}
