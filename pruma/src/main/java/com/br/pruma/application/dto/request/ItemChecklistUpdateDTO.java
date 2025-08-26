package com.br.pruma.application.dto.request;

import com.br.pruma.core.enums.StatusItem;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "ItemChecklistUpdateDTO", description = "Dados para atualizar um item de checklist")
public class ItemChecklistUpdateDTO {

    @Size(max = 500, message = "A descrição deve ter no máximo 500 caracteres")
    @Schema(description = "Nova descrição do item", example = "Atualizar documentação")
    private String descricao;

    @Min(value = 1, message = "A ordem deve ser maior ou igual a 1")
    @Schema(description = "Nova posição do item no checklist", example = "2")
    private Integer ordem;

    @Schema(description = "Novo status do item", example = "CONCLUIDO")
    private StatusItem status;

    @Size(max = 1000, message = "A observação deve ter no máximo 1000 caracteres")
    @Schema(description = "Novas observações sobre o item", example = "Revisão concluída")
    private String observacao;


}
