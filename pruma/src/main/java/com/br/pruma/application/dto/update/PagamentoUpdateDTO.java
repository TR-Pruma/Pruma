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
        name        = "PagamentoUpdateDTO",
        description = "Dados para atualização parcial de um pagamento"
)
public class PagamentoUpdateDTO {

    @Schema(
            description = "Identificador do orçamento associado",
            example     = "7"
    )
    private Integer orcamentoId;

    @Schema(
            description = "Valor do pagamento",
            example     = "2500.00"
    )
    private BigDecimal valor;

    @Schema(
            description = "Data do pagamento (YYYY-MM-DD)",
            example     = "2025-10-06"
    )
    private LocalDate dataPagamento;

    @Schema(
            description = "Forma de pagamento",
            example     = "DINHEIRO"
    )
    private String formaPagamento;
}
