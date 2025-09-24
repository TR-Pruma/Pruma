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
        name              = "PagamentoResponseDTO",
        description       = "Dados retornados de um pagamento",
        requiredProperties = {
                "id",
                "orcamentoId",
                "valor",
                "dataPagamento",
                "formaPagamento",
                "version",
                "createdAt",
                "updatedAt"
        }
)
public class PagamentoResponseDTO {

    @Schema(
            description = "Identificador único do pagamento",
            example     = "15",
            required    = true
    )
    private Integer id;

    @Schema(
            description = "Identificador do orçamento associado",
            example     = "7",
            required    = true
    )
    private Integer orcamentoId;

    @Schema(
            description = "Valor do pagamento",
            example     = "2000.00",
            required    = true
    )
    private BigDecimal valor;

    @Schema(
            description = "Data do pagamento (YYYY-MM-DD)",
            example     = "2025-10-05",
            required    = true
    )
    private LocalDate dataPagamento;

    @Schema(
            description = "Forma de pagamento",
            example     = "CARTAO",
            required    = true
    )
    private String formaPagamento;

    @Schema(
            description = "Versão para controle otimista",
            example     = "1",
            required    = true
    )
    private Long version;

    @Schema(
            description = "Timestamp de criação do registro (ISO 8601)",
            example     = "2025-10-05T14:20:00",
            required    = true
    )
    private LocalDateTime createdAt;

    @Schema(
            description = "Timestamp da última atualização do registro (ISO 8601)",
            example     = "2025-10-06T08:15:00",
            required    = true
    )
    private LocalDateTime updatedAt;
}
