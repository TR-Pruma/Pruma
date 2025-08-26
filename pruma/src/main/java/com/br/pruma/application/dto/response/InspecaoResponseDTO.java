package com.br.pruma.application.dto.response;

import java.time.LocalDate;

public record InspecaoResponseDTO(
        Integer id,
        Integer projetoId,
        Long tecnicoCpf,
        String descricao,
        LocalDate dataInspecao,
        String resultado

) {}
