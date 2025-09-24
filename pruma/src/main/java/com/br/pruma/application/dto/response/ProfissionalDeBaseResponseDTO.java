package com.br.pruma.application.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Schema(
        name = "ProfissionalDeBaseResponseDTO",
        description = "Representação de um Profissional de Base retornada pela API",
        requiredProperties = {"id", "nome"}
)
public class ProfissionalDeBaseResponseDTO {

    @Schema(description = "Identificador único do profissional", example = "1", required = true)
    private Integer id;

    @Schema(description = "Nome completo do profissional", example = "João da Silva", required = true)
    private String nome;

    @Schema(description = "Especialidade do profissional", example = "Engenheiro Civil")
    private String especialidade;

    @Schema(description = "Telefone de contato", example = "+55 11 91234-5678")
    private String telefone;

    @Schema(description = "Timestamp de criação (ISO 8601)", example = "2025-06-01T10:15:30")
    private LocalDateTime createdAt;

    @Schema(description = "Timestamp da última atualização (ISO 8601)", example = "2025-06-05T08:00:00")
    private LocalDateTime updatedAt;

    @Schema(description = "Versão para controle otimista", example = "1")
    private Long version;
}
