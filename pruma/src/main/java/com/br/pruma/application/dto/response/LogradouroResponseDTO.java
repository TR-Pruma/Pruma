package com.br.pruma.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Schema(name = "LogradouroResponseDTO", description = "Dados retornados de um logradouro")
public class LogradouroResponseDTO {

    @Schema(description = "Identificador único do logradouro", example = "5")
    private Integer id;

    @Schema(description = "Tipo do logradouro (ex: Rua, Avenida, Travessa)", example = "Avenida")
    private String tipo;

    @Schema(description = "Versão para controle otimista", example = "2")
    private Long version;

    @Schema(description = "Timestamp de criação", example = "2025-08-26T14:22:00")
    private LocalDateTime createdAt;

    @Schema(description = "Timestamp da última atualização", example = "2025-08-27T09:45:00")
    private LocalDateTime updatedAt;
}

