package com.br.pruma.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Schema(description = "DTO para resposta de item do checklist")
public class ItemChecklistResponseDTO {

    @Schema(description = "ID do item", example = "1", required = true)
    private Integer id;

    @Schema(description = "ID do checklist ao qual o item pertence", example = "42", required = true)
    private Integer checklistId;

    @Schema(description = "Descrição do item", example = "Verificar documentação técnica", required = true)
    private String descricao;

    @Schema(description = "Ordem do item no checklist", example = "1", required = true)
    private Integer ordem;

    @Schema(description = "Observações sobre o item", example = "Necessita revisão do supervisor")
    private String observacao;

    @Schema(description = "Indica se o item está concluído", example = "true", required = true)
    private boolean concluido;
}


