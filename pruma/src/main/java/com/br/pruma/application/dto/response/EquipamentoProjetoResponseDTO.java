package com.br.pruma.application.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class EquipamentoProjetoResponseDTO {
    private Long id;
    private Long equipamentoId;
    private String equipamentoNome; // opcional
    private Long projetoId;
    private String projetoNome; // opcional
    private LocalDate dataAlocacao;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    private Boolean ativo;
}
