package com.br.pruma.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Schema(
        name = "MaterialUtilizadoRequestDTO",
        description = "Dados para criação de associação de material utilizado em uma atividade"
)
public class MaterialUtilizadoRequestDTO {

    @NotNull(message = "materialId é obrigatório")
    @Schema(description = "Identificador do material utilizado", example = "10", required = true)
    private Integer materialId;

    @NotNull(message = "atividadeId é obrigatório")
    @Schema(description = "Identificador da atividade onde o material foi usado", example = "5", required = true)
    private Integer atividadeId;

    @NotNull(message = "quantidadeUtilizada é obrigatória")
    @Min(value = 1, message = "quantidadeUtilizada deve ser ao menos 1")
    @Schema(description = "Quantidade do material utilizada na atividade", example = "20", required = true)
    private Integer quantidadeUtilizada;
}

