package com.br.pruma.application.dto.update;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Schema(
        name        = "OrcamentoUpdateDTO",
        description = "Dados para atualização parcial de um orçamento"
)
public class OrcamentoUpdateDTO {

    @Schema(
            description = "Identificador do projeto associado",
            example     = "3"
    )
    private Integer projetoId;

    @Schema(
            description = "CNPJ da empresa responsável pelo orçamento",
            example     = "12345678000199"
    )
    private String empresaCnpj;

    @Schema(
            description = "Valor total do orçamento",
            example     = "16000.00"
    )
    private BigDecimal valor;

    @Schema(
            description = "Data de envio do orçamento (YYYY-MM-DD)",
            example     = "2025-10-01"
    )
    private LocalDate dataEnvio;

    @Schema(
            description = "Status do orçamento",
            example     = "APROVADO"
    )
    private String status;
}

