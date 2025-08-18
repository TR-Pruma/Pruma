package com.br.pruma.application.dto.response;

import java.time.LocalDateTime;

public record HistoricoLocalizacaoResponseDTO(
        Integer id,
        Long profissionalCpf,
        Integer projetoId,
        String localizacao,
        LocalDateTime dataHora

) {
}
