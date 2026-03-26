package com.br.pruma.application.dto.response;

import com.br.pruma.core.enums.StatusEquipamento;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EquipamentoListDTO {

    private Integer id;
    private String nome;
    private StatusEquipamento status;
    private Boolean ativo;
}