package com.br.pruma.application.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "DTO para criação/atualização de Checklist")
public class ChecklistRequestDTO {

    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 3, max = 50, message = "O nome deve ter entre 3 e 50 caracteres")
    @ApiModelProperty(value = "Nome do checklist", required = true, example = "Checklist de Qualidade")
    private String nome;

    @NotNull(message = "O ID do projeto é obrigatório")
    @ApiModelProperty(value = "ID do projeto ao qual o checklist pertence", required = true, example = "1")
    private Integer projetoId;
}

