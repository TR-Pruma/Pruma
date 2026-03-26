package com.br.pruma.application.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;

@Schema(name = "SubContratoResponseDTO", description = "Dados de subcontrato retornado")
public record SubContratoResponseDTO(

        @Schema(description = "Identificador único do subcontrato", example = "1", required = true)
        Integer id,

        @Schema(description = "CPF do cliente", example = "12345678900", required = true)
        String clienteCpf,

        @Schema(description = "Nome do cliente", example = "João Silva")
        String clienteNome,

        @Schema(description = "Identificador do projeto", example = "1", required = true)
        Integer projetoId,

        @Schema(description = "Nome do projeto", example = "Projeto ABC")
        String projetoNome,

        @Schema(description = "Descrição do subcontrato", example = "Subcontrato para serviços especializados")
        String descricao,

        @Schema(description = "Valor do subcontrato", example = "5000.00", required = true)
        Float valor,

        @Schema(description = "Data de início do subcontrato", example = "2025-12-27", required = true)
        LocalDate dataInicio,

        @Schema(description = "Data de término do subcontrato", example = "2026-12-27")
        LocalDate dataFim,

        @Schema(description = "Data de criação do registro no sistema", example = "2025-12-27", required = true)
        LocalDate createdAt

) {}
