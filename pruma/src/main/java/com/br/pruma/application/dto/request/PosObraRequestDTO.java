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
@Schema(name = "PosObraRequestDTO", description = "Dados para criação de um registro pós-obra")
public class PosObraRequestDTO {

    @NotNull(message = "obraId é obrigatório")
    @Schema(description = "Identificador da obra associada", example = "10", required = true)
    private Long obraId;

    @NotBlank(message = "descrição é obrigatória")
    @Schema(description = "Descrição do pós-obra", example = "Revisão final dos acabamentos", required = true)
    private String descricao;

    @NotNull(message = "dataConclusao é obrigatória")
    @Schema(description = "Data de conclusão (YYYY-MM-DD)", example = "2025-11-15", required = true)
    private LocalDate dataConclusao;
}
