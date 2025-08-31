package com.br.pruma.application.dto.update;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.math.BigDecimal;

public class MaterialUpdateDTO {

    @Getter
    @Size(max = 2000, message = "descrição deve ter no máximo 2000 caracteres")
    @Schema(description = "Nova descrição do material", example = "Cimento CP II 50kg")
    private String descricao;

    @Min(value = 0, message = "quantidade não pode ser negativa")
    @Schema(description = "Nova quantidade disponível do material", example = "120")
    private Integer quantidade;

    @DecimalMin(value = "0.00", inclusive = true, message = "custoUnitario não pode ser negativo")
    @Digits(integer = 16, fraction = 2, message = "custoUnitario deve ter até 16 dígitos inteiros e 2 decimais")
    @Schema(description = "Novo custo unitário do material", example = "26.75")
    private BigDecimal custoUnitario;

}

