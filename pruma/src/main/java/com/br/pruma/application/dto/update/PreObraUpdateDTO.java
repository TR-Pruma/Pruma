package com.br.pruma.application.dto.update;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Schema(name = "PreObraUpdateDTO", description = "Dados para atualização parcial de um registro de pré-obra")
public class PreObraUpdateDTO {

    @Schema(description = "Identificador da obra associada", example = "10")
    private Integer obraId;

    @Schema(description = "Descrição da pré-obra", example = "Ajuste no cronograma")
    private String descricao;

    @Schema(description = "Data de início prevista (YYYY-MM-DD)", example = "2025-07-05")
    private LocalDate dataInicio;
}

