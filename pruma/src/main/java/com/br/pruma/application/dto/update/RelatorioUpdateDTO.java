package com.br.pruma.application.dto.update;

import java.time.LocalDate;

public record RelatorioUpdateDTO(
        Integer obraId,       // alinhado com RelatorioRequestDTO
        String descricao,     // alinhado com a entidade Relatorio
        LocalDate dataCriacao // alinhado com a entidade Relatorio
) {}