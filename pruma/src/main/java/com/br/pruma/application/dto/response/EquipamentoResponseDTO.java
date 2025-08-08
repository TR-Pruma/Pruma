package com.br.pruma.application.dto.response;

import com.br.pruma.core.enums.StatusEquipamento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EquipamentoResponseDTO {

    private Integer id;
    private String nome;
    private String descricao;
    private StatusEquipamento status;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    private Boolean ativo;
}
