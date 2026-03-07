package com.br.pruma.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "EquipamentoProjetoResponseDTO", description = "Dados retornados de um equipamento associado a um projeto")
public class EquipamentoProjetoResponseDTO {

    @Schema(description = "Identificador do equipamento", example = "1")
    private Integer equipamentoId;

    @Schema(description = "Identificador do projeto", example = "10")
    private Integer projetoId;
}
