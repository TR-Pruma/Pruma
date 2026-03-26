package com.br.pruma.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record InspecaoRequestDTO(
        @NotNull(message = "{inspecaoRequestDTO.projetoId}")
        Integer projetoId,

        @NotNull
        Long tecnicoCpf,

        @NotBlank(message = "{inspecaoRequestDTO.descricao}")
        String descricao,

        @NotNull
        LocalDate dataInspecao,

        @NotBlank
        @Size(max = 100, message = "{inspecaoRequestDTO.resultado}")
        String resultado
) {}