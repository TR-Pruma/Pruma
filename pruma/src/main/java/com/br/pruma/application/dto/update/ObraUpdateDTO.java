package com.br.pruma.application.dto.update;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Schema(
        name        = "ObraUpdateDTO",
        description = "Dados para atualização parcial de uma obra"
)
public class ObraUpdateDTO {

    @Schema(
            description = "Identificador do projeto associado",
            example     = "5"
    )
    private Integer projetoId;

    @Schema(
            description = "Descrição da obra",
            example     = "Reforma de piso"
    )
    private String descricao;

    @Schema(
            description = "Data de início da obra (YYYY-MM-DD)",
            example     = "2025-10-01"
    )
    private LocalDate dataInicio;

    @Schema(
            description = "Data de término da obra (YYYY-MM-DD)",
            example     = "2026-03-30"
    )
    private LocalDate dataFim;
}