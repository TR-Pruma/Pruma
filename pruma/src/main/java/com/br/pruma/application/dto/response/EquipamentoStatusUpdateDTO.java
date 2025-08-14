package com.br.pruma.application.dto.response;

import com.br.pruma.core.enums.StatusEquipamento;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EquipamentoStatusUpdateDTO {

    @NotNull(message = "O status é obrigatório.")
    private StatusEquipamento status;
}

