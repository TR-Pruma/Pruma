package com.br.pruma.application.dto.update;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class InsumoUpdateDTO {

    private String nome;
    private String descricao;
    private String unidadeMedida;
    private BigDecimal precoUnitario;
    private Double quantidadeEstoque;
}
