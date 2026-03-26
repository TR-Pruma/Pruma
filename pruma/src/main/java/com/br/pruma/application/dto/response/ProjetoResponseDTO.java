package com.br.pruma.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Schema(name = "ProjetoResponseDTO", description = "Representação de Projeto retornada pela API")
public class ProjetoResponseDTO {

    @Schema(description = "Identificador único do projeto", example = "1", required = true)
    private Integer id;

    @Schema(description = "Nome do projeto", example = "Reforma Comercial Centro", required = true)
    private String nome;

    @Schema(description = "Descrição detalhada do projeto", example = "Reforma completa do térreo com troca de instalações")
    private String descricao;

    @Schema(description = "Data de criação do projeto (YYYY-MM-DD)", example = "2025-09-01")
    private LocalDate dataCriacao;

    @Schema(description = "Timestamp de criação (ISO 8601)", example = "2025-06-01T10:15:30")
    private LocalDateTime createdAt;

    @Schema(description = "Timestamp da última atualização (ISO 8601)", example = "2025-06-05T08:00:00")
    private LocalDateTime updatedAt;

    @Schema(description = "Versão para controle otimista", example = "1")
    private Long version;
}