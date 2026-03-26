package com.br.pruma.application.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Schema(
        name              = "OrcamentoResponseDTO",
        description       = "Dados retornados de um orçamento",
        requiredProperties = {
                "id",
                "projetoId",
                "empresaCnpj",
                "valor",
                "dataEnvio",
                "status",
                "version",
                "createdAt",
                "updatedAt"
        }
)
public class OrcamentoResponseDTO {

    @Schema(
            description = "Identificador único do orçamento",
            example     = "12",
            required    = true
    )
    private Integer id;

    @Schema(
            description = "Identificador do projeto associado",
            example     = "3",
            required    = true
    )
    private Integer projetoId;

    @Schema(
            description = "CNPJ da empresa responsável pelo orçamento",
            example     = "12345678000199",
            required    = true
    )
    private String empresaCnpj;

    @Schema(
            description = "Valor total do orçamento",
            example     = "15000.50",
            required    = true
    )
    private BigDecimal valor;

    @Schema(
            description = "Data de envio do orçamento (YYYY-MM-DD)",
            example     = "2025-09-15",
            required    = true
    )
    private LocalDate dataEnvio;

    @Schema(
            description = "Status do orçamento",
            example     = "PENDENTE",
            required    = true
    )
    private String status;

    @Schema(
            description = "Versão para controle otimista",
            example     = "2",
            required    = true
    )
    private Long version;

    @Schema(
            description = "Timestamp de criação do registro (ISO 8601)",
            example     = "2025-08-20T09:30:00",
            required    = true
    )
    private LocalDateTime createdAt;

    @Schema(
            description = "Timestamp da última atualização do registro (ISO 8601)",
            example     = "2025-09-01T14:45:00",
            required    = true
    )
    private LocalDateTime updatedAt;
}

