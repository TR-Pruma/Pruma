package com.br.pruma.application.dto.update;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaterialUpdateDTO {

    @Size(max = 500, message = "descri\u00e7\u00e3o deve ter no m\u00e1ximo 500 caracteres")
    @Schema(description = "Nova descri\u00e7\u00e3o do material", example = "Cimento CP II 50kg")
    private String descricao;

    @Min(value = 0, message = "quantidade n\u00e3o pode ser negativa")
    @Schema(description = "Nova quantidade dispon\u00edvel do material", example = "120")
    private Integer quantidade;

    @DecimalMin(value = "0.00", inclusive = true, message = "custoUnitario n\u00e3o pode ser negativo")
    @Digits(integer = 16, fraction = 2, message = "custoUnitario deve ter at\u00e9 16 d\u00edgitos inteiros e 2 decimais")
    @Schema(description = "Novo custo unit\u00e1rio do material", example = "26.75")
    private BigDecimal custoUnitario;
}
