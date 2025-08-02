package com.br.pruma.application.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "DTO para resposta de item do checklist")
public class ItemChecklistResponseDTO {

    @ApiModelProperty(value = "ID do item", example = "1")
    private Integer id;

    @ApiModelProperty(value = "Descrição do item", example = "Verificar documentação técnica")
    private String descricao;

    @ApiModelProperty(value = "Ordem do item no checklist", example = "1")
    private Integer ordem;

    @ApiModelProperty(value = "Observações sobre o item", example = "Necessita revisão do supervisor")
    private String observacao;

    @ApiModelProperty(value = "Indica se o item está concluído", example = "true")
    private boolean concluido;
}
