package com.br.pruma.application.dto.update;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "DTO para atualização parcial de Checklist")
public class ChecklistUpdateDTO {

    @Size(min = 3, max = 50, message = "O nome deve ter entre 3 e 50 caracteres")
    @Schema(description = "Nome do checklist", example = "Checklist de Qualidade")
    private String nome;

    @Schema(description = "ID do projeto ao qual o checklist pertence", example = "1")
    private Integer projetoId;

    @Schema(description = "Indica se o checklist está ativo", example = "true")
    private Boolean ativo;
}
