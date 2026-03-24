package com.br.pruma.application.dto.request;

import com.br.pruma.core.enums.StatusItem;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "ItemChecklistRequestDTO", description = "Dados para criar um item de checklist")
public class ItemChecklistRequestDTO {

    @NotNull(message = "O ID do checklist é obrigatório")
    @Schema(description = "Identificador do checklist", example = "1", required = true)
    private Integer checklistId;

    @NotBlank(message = "A descrição é obrigatória")
    @Size(max = 500, message = "A descrição deve ter no máximo 500 caracteres")
    @Schema(description = "Descrição do item", example = "Verificar documentação", required = true)
    private String descricao;

    @NotNull(message = "A ordem é obrigatória")
    @Min(value = 1, message = "A ordem deve ser maior ou igual a 1")
    @Schema(description = "Posição do item no checklist", example = "1", required = true)
    private Integer ordem;

    @NotNull(message = "O status é obrigatório")
    @Schema(description = "Status inicial do item", example = "PENDENTE", required = true)
    private StatusItem status;

    @Size(max = 1000, message = "A observação deve ter no máximo 1000 caracteres")
    @Schema(description = "Observações adicionais", example = "Necessita revisão adicional")
    private String observacao;

}