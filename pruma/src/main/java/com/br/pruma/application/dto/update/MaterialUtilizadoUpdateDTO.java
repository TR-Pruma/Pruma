package com.br.pruma.application.dto.update;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Schema(
        name = "MaterialUtilizadoUpdateDTO",
        description = "Dados para atualização parcial de associação de material utilizado"
)
public class MaterialUtilizadoUpdateDTO {

    @Min(value = 1, message = "materialId deve ser um ID válido")
    @Schema(description = "Novo identificador do material", example = "12")
    private Integer materialId;

    @Min(value = 1, message = "atividadeId deve ser um ID válido")
    @Schema(description = "Novo identificador da atividade", example = "7")
    private Integer atividadeId;

    @Min(value = 1, message = "quantidadeUtilizada deve ser ao menos 1")
    @Schema(description = "Nova quantidade de material utilizada", example = "15")
    private Integer quantidadeUtilizada;
}

