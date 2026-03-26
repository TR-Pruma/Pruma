package com.br.pruma.application.dto.update;

import com.br.pruma.core.enums.StatusEquipamento;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Schema(name = "EquipamentoUpdateDTO", description = "Dados para atualização parcial de equipamento")
public class EquipamentoUpdateDTO {

    @Size(max = 255, message = "O nome deve ter no máximo {max} caracteres")
    @Schema(description = "Nome do equipamento", example = "Furadeira Industrial")
    private String nome;

    @Size(max = 255, message = "A descrição deve ter no máximo {max} caracteres")
    @Schema(description = "Descrição do equipamento", example = "Furadeira de impacto 800W")
    private String descricao;

    @Schema(description = "Status do equipamento", example = "ATIVO")
    private StatusEquipamento status;
}
