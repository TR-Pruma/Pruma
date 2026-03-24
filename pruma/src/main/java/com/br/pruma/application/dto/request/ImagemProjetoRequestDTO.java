package com.br.pruma.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ImagemProjetoRequestDTO(
       @NotNull(message = "O ID do projeto é obrigatório")
       Integer projetoId,
       @NotBlank(message = "O caminho do arquivo é obrigatório")
       String caminhoArquivo,
       String descricao,
       @NotNull(message = "A data de upload é obrigatória")
       LocalDate dataUpload
) {}
