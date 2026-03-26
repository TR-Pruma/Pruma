package com.br.pruma.application.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Schema(name = "SubContratoRequestDTO", description = "Dados para criação/atualização de subcontrato")
public record SubContratoRequestDTO(

        @NotNull(message = "clienteCpf é obrigatório")
        @Schema(description = "CPF do cliente", example = "12345678900", required = true)
        String clienteCpf,

        @NotNull(message = "projetoId é obrigatório")
        @Schema(description = "Identificador do projeto", example = "1", required = true)
        Integer projetoId,

        @Size(max = 5000, message = "Descrição pode ter no máximo 5000 caracteres")
        @Schema(description = "Descrição do subcontrato", example = "Subcontrato para serviços especializados")
        String descricao,

        @NotNull(message = "Valor é obrigatório")
        @Positive(message = "Valor deve ser maior que zero")
        @Schema(description = "Valor do subcontrato", example = "5000.00", required = true)
        Float valor,

        @NotNull(message = "Data de início é obrigatória")
        @Schema(description = "Data de início do subcontrato", example = "2025-12-27", required = true)
        LocalDate dataInicio,

        @Schema(description = "Data de término do subcontrato", example = "2026-12-27")
        LocalDate dataFim

) {}
