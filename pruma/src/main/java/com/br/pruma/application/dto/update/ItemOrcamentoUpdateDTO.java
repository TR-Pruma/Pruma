package com.br.pruma.application.dto.update;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "ItemOrcamentoUpdateDTO", description = "Dados para atualização parcial de um item de orçamento")
public class ItemOrcamentoUpdateDTO {

    @Size(max = 1000, message = "descrição deve ter até 1000 caracteres")
    @Schema(description = "Nova descrição do item", example = "Serviço de instalação sanitária")
    private String descricao;

    @Positive(message = "quantidade deve ser maior que zero")
    @Schema(description = "Nova quantidade de unidades", example = "8")
    private Integer quantidade;

    @DecimalMin(value = "0.00", inclusive = false, message = "valorUnitario deve ser maior que 0.00")
    @Schema(description = "Novo valor unitário do item", example = "155.75")
    private BigDecimal valorUnitario;

}
