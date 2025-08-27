package com.br.pruma.application.dto.request;

import com.br.pruma.core.enums.StatusOrcamento;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Schema(name = "OrcamentoRequestDTO", description = "Dados para criação de um novo orçamento")
public class OrcamentoRequestDTO {

    @NotNull(message = "projetoId é obrigatório")
    @Schema(description = "Identificador do projeto", example = "42", required = true)
    private Integer projetoId;

    @NotNull(message = "empresaCnpj é obrigatório")
    @Pattern(
            regexp = "\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}",
            message = "empresaCnpj deve seguir o padrão 00.000.000/0000-00"
    )
    @Schema(
            description = "CNPJ da empresa contratante",
            example = "12.345.678/0001-90",
            required = true
    )
    private String empresaCnpj;

    @NotNull(message = "valor é obrigatório")
    @DecimalMin(value = "0.00", inclusive = false, message = "valor deve ser maior que 0.00")
    @Schema(description = "Valor total do orçamento", example = "15000.75", required = true)
    private BigDecimal valor;

    @NotNull(message = "dataEnvio é obrigatória")
    @Schema(description = "Data de envio do orçamento", example = "2025-08-26", required = true)
    private LocalDate dataEnvio;

    @NotNull(message = "status é obrigatório")
    @Schema(description = "Status do orçamento", example = "PENDENTE", required = true)
    private StatusOrcamento status;

}
