package com.br.pruma.application.dto.update;

import java.time.LocalDate;

public record SolicitacaoMudancaUpdateDTO(
        Integer projetoId,
        Integer statusSolicitacaoId,
        String descricao,
        String justificativa,
        LocalDate dataSolicitacao
) {}