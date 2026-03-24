package com.br.pruma.application.dto.request;

import com.br.pruma.core.enums.StatusEquipamento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EquipamentoRequestDTO {

    @NotBlank(message = "O nome é obrigatório.")
    @Size(max = 255, message = "O nome deve ter no máximo {max} caracteres.")
    private String nome;

    @NotBlank(message = "A descrição é obrigatória.")
    @Size(max = 255, message = "A descrição deve ter no máximo {max} caracteres.")
    private String descricao;

    @NotNull(message = "O status é obrigatório.")
    private StatusEquipamento status;
}
