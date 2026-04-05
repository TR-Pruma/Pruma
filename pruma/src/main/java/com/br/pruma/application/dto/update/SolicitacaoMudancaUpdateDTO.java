package com.br.pruma.application.dto.update;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SolicitacaoMudancaUpdateDTO {
    private Integer projetoId;
    private String descricao;
    private String justificativa;
    private String status;
    private LocalDate dataSolicitacao;
}
