package com.br.pruma.application.dto.update;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Schema(name = "PosObraUpdateDTO", description = "Dados para atualização parcial de um registro pós-obra")
public class PosObraUpdateDTO {

    @Schema(description = "Identificador da obra associada", example = "10")
    private Long obraId;

    @Schema(description = "Descrição do pós-obra", example = "Correções em rejunte")
    private String descricao;

    @Schema(description = "Data de conclusão (YYYY-MM-DD)", example = "2025-11-20")
    private LocalDate dataConclusao;
}
