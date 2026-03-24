package com.br.pruma.application.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Schema(name = "SolicitacaoMudancaRequestDTO", description = "Dados para criação/atualização de solicitação de mudança")
public record SolicitacaoMudancaRequestDTO(

        @NotNull(message = "projetoId é obrigatório")
        @Schema(description = "Identificador do projeto relacionado", example = "1", required = true)
        Integer projetoId,

        @Size(max = 5000, message = "Descrição pode ter no máximo 5000 caracteres")
        @Schema(description = "Descrição detalhada da solicitação de mudança", example = "Alteração no escopo do projeto")
        String descricao,

        @NotNull(message = "statusSolicitacaoId é obrigatório")
        @Schema(description = "Identificador do status da solicitação", example = "1", required = true)
        Integer statusSolicitacaoId,

        @NotNull(message = "Data de solicitação é obrigatória")
        @Schema(description = "Data da solicitação", example = "2025-12-21", required = true)
        LocalDate dataSolicitacao,

        @Schema(description = "Data de resposta da solicitação", example = "2025-12-21")
        LocalDate dataResposta

) {}