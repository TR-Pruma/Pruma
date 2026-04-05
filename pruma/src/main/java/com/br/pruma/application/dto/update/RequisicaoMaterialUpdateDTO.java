package com.br.pruma.application.dto.update;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class RequisicaoMaterialUpdateDTO {
    private Integer obraId;
    private Integer materialId;
    private BigDecimal quantidade;
    private LocalDate dataNecessidade;
    private String observacao;
}
