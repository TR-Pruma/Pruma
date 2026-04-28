package com.br.pruma.application.dto.update;

import java.time.LocalDate;

public record SubContratoUpdateDTO(
        String clienteCpf,      // era fornecedorId — FK correta é cliente por CPF
        Integer projetoId,
        String descricao,
        Float valor,            // alinhado com o tipo da entidade
        LocalDate dataInicio,
        LocalDate dataFim
) {}