package com.br.pruma.application.dto.update;

import com.br.pruma.core.enums.StatusOrcamento;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Schema(name = "OrcamentoUpdateDTO", description = "Dados para atualização parcial de um orçamento")
public class OrcamentoUpdateDTO {
    @DecimalMin(value = "0.00", inclusive = false, message = "valor deve ser maior que 0.00")
    @Schema(description = "Novo valor do orçamento", example = "15500.00")
    private BigDecimal valor;

    @Schema(description = "Nova data de envio do orçamento", example = "2025-08-27")
    private LocalDate dataEnvio;

    @Schema(description = "Novo status do orçamento", example = "APROVADO")
    private StatusOrcamento status;

}
