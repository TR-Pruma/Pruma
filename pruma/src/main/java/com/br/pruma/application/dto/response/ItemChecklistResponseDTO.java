package com.br.pruma.application.dto.response;

import com.br.pruma.core.enums.StatusItem;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "ItemChecklistResponseDTO", description = "Dados retornados de um item de checklist")
public class ItemChecklistResponseDTO {

    @Schema(description = "Identificador único do item", example = "1")
    private Integer id;

    @Schema(description = "Identificador do checklist pai", example = "1")
    private Integer checklistId;

    @Schema(description = "Descrição do item", example = "Verificar documentação")
    private String descricao;

    @Schema(description = "Ordem do item no checklist", example = "1")
    private Integer ordem;

    @Schema(description = "Status do item", example = "PENDENTE")
    private StatusItem status;

    @Schema(description = "Observações sobre o item", example = "Necessita revisão adicional")
    private String observacao;

    @Schema(description = "Flag de ativo/inativo", example = "true")
    private boolean ativo;

    @Schema(description = "Versão para controle otimista", example = "3")
    private Long version;

    @Schema(description = "Data de criação do registro", example = "2025-08-25T23:43:00")
    private LocalDateTime createdAt;

    @Schema(description = "Data da última atualização", example = "2025-08-26T00:01:00")
    private LocalDateTime updatedAt;
}
