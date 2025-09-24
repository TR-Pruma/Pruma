package com.br.pruma.application.dto.request;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Schema(name = "ObraRequestDTO", description = "Dados para criação de uma Obra")
public class ObraRequestDTO {

    @NotNull(message = "projetoId é obrigatório")
    @Schema(description = "Identificador do projeto associado", example = "12", required = true)
    private Integer projetoId;

    @NotBlank(message = "descricao é obrigatória")
    @Schema(description = "Descrição da obra", example = "Reforma de escola municipal", required = true)
    private String descricao;

    @NotNull(message = "dataInicio é obrigatória")
    @Schema(description = "Data de início da obra (YYYY-MM-DD)", example = "2025-06-01", required = true)
    private LocalDate dataInicio;

    @Schema(description = "Data de fim prevista (YYYY-MM-DD)", example = "2025-12-15")
    private LocalDate dataFim;
}
