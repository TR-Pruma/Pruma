package com.br.pruma.application.dto.update;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EquipamentoAtivoUpdateDTO {

    @NotNull(message = "O campo ativo é obrigatório.")
    private Boolean ativo;
}