package com.br.pruma.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Schema(
        name = "PosObraResponseDTO",
        description = "Dados retornados de um registro pós-obra",
        requiredProperties = {"id", "obraId", "descricao", "dataConclusao"}
)
public class PosObraResponseDTO {

    @Schema(description = "Identificador único do pós-obra", example = "5", required = true)
    private Long id;

    @Schema(description = "Identificador da obra associada", example = "10", required = true)
    private Long obraId;

    @Schema(description = "Descrição do pós-obra", example = "Revisão final dos acabamentos", required = true)
    private String descricao;

    @Schema(description = "Data de conclusão (YYYY-MM-DD)", example = "2025-11-15", required = true)
    private LocalDate dataConclusao;
}
