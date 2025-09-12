package com.br.pruma.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Schema(
        name        = "ObraRequestDTO",
        description = "Dados para criação de uma obra"
)
public class ObraRequestDTO {

    @NotNull(message = "projetoId é obrigatório")
    @Schema(
            description = "Identificador do projeto associado",
            example     = "5",
            required    = true
    )
    private Integer projetoId;

    @NotBlank(message = "descrição é obrigatória")
    @Schema(
            description = "Descrição da obra",
            example     = "Construção de alvenaria",
            required    = true
    )
    private String descricao;

    @NotNull(message = "dataInicio é obrigatória")
    @Schema(
            description = "Data de início da obra (YYYY-MM-DD)",
            example     = "2025-09-01",
            required    = true
    )
    private LocalDate dataInicio;

    @Schema(
            description = "Data de término da obra (YYYY-MM-DD)",
            example     = "2025-12-31"
    )
    private LocalDate dataFim;
}