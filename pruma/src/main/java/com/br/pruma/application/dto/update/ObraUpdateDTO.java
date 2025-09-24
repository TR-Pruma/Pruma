package com.br.pruma.application.dto.update;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Schema(name = "ObraUpdateDTO", description = "Dados para atualização parcial de uma Obra")
public class ObraUpdateDTO {

    @Schema(description = "Identificador do projeto associado", example = "12")
    private Integer projetoId;

    @Schema(description = "Descrição da obra", example = "Ajuste nos acabamentos")
    private String descricao;

    @Schema(description = "Data de início da obra (YYYY-MM-DD)", example = "2025-06-05")
    private LocalDate dataInicio;

    @Schema(description = "Data de fim prevista (YYYY-MM-DD)", example = "2025-12-20")
    private LocalDate dataFim;
}
