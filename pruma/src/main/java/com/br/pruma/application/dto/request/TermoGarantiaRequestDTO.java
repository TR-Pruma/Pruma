package com.br.pruma.application.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

/**
 * DTO de entrada para criação e atualização de TermoGarantia.
 */
@Data
public class TermoGarantiaRequestDTO {

    @NotNull(message = "O ID do projeto é obrigatório")
    private Integer projetoId;

    private String descricao;

    @NotNull(message = "A data de emissão é obrigatória")
    private LocalDate dataEmissao;

    @Min(value = 1, message = "A validade deve ser de no mínimo 1 mês")
    private Integer validadeMeses;
}
