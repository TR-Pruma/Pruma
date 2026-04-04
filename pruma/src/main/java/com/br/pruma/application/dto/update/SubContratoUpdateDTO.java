package com.br.pruma.application.dto.update;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class SubContratoUpdateDTO {
    private Integer projetoId;
    private Integer fornecedorId;
    private String descricao;
    private BigDecimal valor;
    private LocalDate dataInicio;
    private LocalDate dataFim;
}
