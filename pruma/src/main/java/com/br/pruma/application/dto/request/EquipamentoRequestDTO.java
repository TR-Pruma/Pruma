package com.br.pruma.application.dto.request;

import com.br.pruma.core.enums.StatusEquipamento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EquipamentoRequestDTO {

    @NotBlank(message = "O nome do equipamento é obrigatório")
    private String nome;

    private String descricao;

    @NotNull(message = "O status do equipamento é obrigatório")
    private StatusEquipamento status;
}
