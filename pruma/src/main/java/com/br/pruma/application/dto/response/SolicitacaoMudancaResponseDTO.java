package com.br.pruma.application.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;

@Schema(name = "SolicitacaoMudancaResponseDTO", description = "Dados de solicitação de mudança retornado")
public record SolicitacaoMudancaResponseDTO(

        @Schema(description = "Identificador único da solicitação", example = "1", required = true)
        Integer id,

        @Schema(description = "Identificador do projeto relacionado", example = "1", required = true)
        Integer projetoId,

        @Schema(description = "Nome do projeto", example = "Projeto ABC")
        String projetoNome,

        @Schema(description = "Descrição da solicitação de mudança", example = "Alteração no escopo do projeto")
        String descricao,

        @Schema(description = "Identificador do status", example = "1", required = true)
        Integer statusSolicitacaoId,

        @Schema(description = "Descrição do status", example = "Pendente")
        String statusSolicitacaoDescricao,

        @Schema(description = "Data da solicitação", example = "2025-12-21", required = true)
        LocalDate dataSolicitacao,

        @Schema(description = "Data de resposta da solicitação", example = "2025-12-21")
        LocalDate dataResposta,

        @Schema(description = "Data de criação do registro no sistema", example = "2025-12-21", required = true)
        LocalDate createdAt

) {}
