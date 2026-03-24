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
@Schema(name = "PreObraRequestDTO", description = "Dados para criação de um registro de pré-obra")
public class PreObraRequestDTO {

    @NotNull(message = "obraId é obrigatório")
    @Schema(description = "Identificador da obra associada", example = "10", required = true)
    private Integer obraId;

    @NotBlank(message = "descrição é obrigatória")
    @Schema(description = "Descrição da pré-obra", example = "Mobilização do canteiro", required = true)
    private String descricao;

    @Schema(description = "Data de início prevista (YYYY-MM-DD)", example = "2025-07-01")
    private LocalDate dataInicio;
}
