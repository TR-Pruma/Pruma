package com.br.pruma.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "ItemOrcamentoRequestDTO", description = "Dados para criar um novo item de orçamento")
public class ItemOrcamentoRequestDTO {

    @NotNull(message = "orcamentoId é obrigatório")
    @Schema(description = "Identificador do orçamento pai", example = "1", required = true)
    private Integer orcamentoId;

    @NotBlank(message = "descrição é obrigatória")
    @Size(max = 1000, message = "descrição deve ter até 1000 caracteres")
    @Schema(description = "Descrição detalhada do item", example = "Serviço de instalação elétrica",
            required = true)
    private String descricao;

    @NotNull(message = "quantidade é obrigatória")
    @Positive(message = "quantidade deve ser maior que zero")
    @Schema(description = "Quantidade de unidades", example = "10", required = true)
    private Integer quantidade;

    @NotNull(message = "valorUnitario é obrigatório")
    @DecimalMin(value = "0.00", inclusive = false, message = "valorUnitario deve ser maior que 0.00")
    @Schema(description = "Valor unitário do item", example = "150.50", required = true)
    private BigDecimal valorUnitario;
}
