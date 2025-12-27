package com.br.pruma.application.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(name = "StatusSolicitacaoRequestDTO", description = "Dados para criação/atualização de status de solicitação")
public record StatusSolicitacaoRequestDTO(

        @NotBlank(message = "Descrição é obrigatória")
        @Size(max = 255, message = "Descrição deve ter no máximo 255 caracteres")
        @Schema(description = "Descrição do status de solicitação", example = "Pendente", required = true)
        String descricaoSolicitacao

) {}