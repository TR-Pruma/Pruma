package com.br.pruma.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Schema(
        name        = "OrcamentoRequestDTO",
        description = "Dados para criação de um orçamento"
)
public class OrcamentoRequestDTO {

    @NotNull(message = "projetoId é obrigatório")
    @Schema(
            description = "Identificador do projeto associado",
            example     = "3",
            required    = true
    )
    private Integer projetoId;

    @NotBlank(message = "empresaCnpj é obrigatório")
    @Schema(
            description = "CNPJ da empresa responsável pelo orçamento",
            example     = "12345678000199",
            required    = true
    )
    private String empresaCnpj;

    @NotNull(message = "valor é obrigatório")
    @DecimalMin(value = "0.00", message = "valor deve ser maior ou igual a zero")
    @Schema(
            description = "Valor total do orçamento",
            example     = "15000.50",
            required    = true
    )
    private BigDecimal valor;

    @NotNull(message = "dataEnvio é obrigatória")
    @Schema(
            description = "Data de envio do orçamento (YYYY-MM-DD)",
            example     = "2025-09-15",
            required    = true
    )
    private LocalDate dataEnvio;

    @NotNull(message = "status é obrigatório")
    @Schema(
            description = "Status do orçamento",
            example     = "PENDENTE",
            required    = true
    )
    private String status;
}

