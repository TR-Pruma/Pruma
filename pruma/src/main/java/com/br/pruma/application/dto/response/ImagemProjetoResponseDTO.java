package com.br.pruma.application.dto.response;

import java.time.LocalDate;

public record ImagemProjetoResponseDTO(
        Integer id,
        Integer projetoId,
        String caminhoArquivo,
        String descricao,
        LocalDate dataUpload
) {}