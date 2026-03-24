package com.br.pruma.application.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class EquipamentoProjetoRequestDTO {

    @NotNull
    private Long equipamentoId;

    @NotNull
    private Long projetoId;

    @NotNull
    private LocalDate dataAlocacao;
}