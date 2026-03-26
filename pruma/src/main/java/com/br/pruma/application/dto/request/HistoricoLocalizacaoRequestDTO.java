package com.br.pruma.application.dto.request;

import java.time.LocalDateTime;

public record HistoricoLocalizacaoRequestDTO (
        Long profissionalCpf,
        Integer projetoId,
        String localizacao,
        LocalDateTime dataHora)
{}
