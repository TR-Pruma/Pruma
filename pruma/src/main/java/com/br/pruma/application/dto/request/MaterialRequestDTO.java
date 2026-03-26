package com.br.pruma.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Schema(name = "MaterialRequestDTO", description = "Dados para criação de um material")
public class MaterialRequestDTO {

    @NotBlank(message = "descrição é obrigatória")
    @Size(max = 2000, message = "descrição deve ter no máximo 2000 caracteres")
    @Schema(description = "Descrição detalhada do material", example = "Cimento CP II 50kg", required = true)
    private String descricao;

    @NotNull(message = "quantidade é obrigatória")
    @Min(value = 0, message = "quantidade não pode ser negativa")
    @Schema(description = "Quantidade disponível do material", example = "100", required = true)
    private Integer quantidade;

    @NotNull(message = "custoUnitario é obrigatório")
    @DecimalMin(value = "0.00", inclusive = true, message = "custoUnitario não pode ser negativo")
    @Digits(integer = 16, fraction = 2, message = "custoUnitario deve ter até 16 dígitos inteiros e 2 decimais")
    @Schema(description = "Custo unitário do material", example = "25.50", required = true)
    private BigDecimal custoUnitario;
}

