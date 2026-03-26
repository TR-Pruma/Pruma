package com.br.pruma.application.dto.response;

import com.br.pruma.core.enums.StatusEquipamento;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EquipamentoResponseDTO {

    private Integer id;
    private String nome;
    private String descricao;
    private StatusEquipamento status;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    private Boolean ativo;

}