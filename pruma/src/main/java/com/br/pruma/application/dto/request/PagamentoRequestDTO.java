package com.br.pruma.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Schema(
        name        = "PagamentoRequestDTO",
        description = "Dados para criação de um pagamento"
)
public class PagamentoRequestDTO {

    @NotNull(message = "orcamentoId é obrigatório")
    @Schema(
            description = "Identificador do orçamento associado",
            example     = "7",
            required    = true
    )
    private Integer orcamentoId;

    @NotNull(message = "valor é obrigatório")
    @DecimalMin(value = "0.00", message = "valor deve ser maior ou igual a zero")
    @Schema(
            description = "Valor do pagamento",
            example     = "2000.00",
            required    = true
    )
    private BigDecimal valor;

    @NotNull(message = "dataPagamento é obrigatória")
    @Schema( description = "Data do pagamento (YYYY-MM-DD)",
            example     = "2025-10-05",
            required    = true
    )
    private LocalDate dataPagamento;

    @NotBlank(message = "formaPagamento é obrigatória")
    @Schema(
            description = "Forma de pagamento",
            example     = "CARTAO",
            required    = true
    )
    private String formaPagamento;
}