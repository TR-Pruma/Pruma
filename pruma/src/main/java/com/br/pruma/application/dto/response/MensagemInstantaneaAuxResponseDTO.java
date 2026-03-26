package com.br.pruma.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Schema(
        name              = "MensagemInstantaneaAuxResponseDTO",
        description       = "Metadados de tipo de uma mensagem instantânea",
        requiredProperties = {
                "mensagemId",
                "tipoMensagem",
                "version",
                "createdAt",
                "updatedAt"
        }
)
public class MensagemInstantaneaAuxResponseDTO {

    @Schema(
            description = "Identificador da mensagem instantânea principal",
            example     = "15",
            required    = true
    )
    private Integer mensagemId;

    @Schema(
            description = "Tipo da mensagem instantânea (ex: ALERTA, NOTIFICACAO)",
            example     = "ALERTA",
            required    = true
    )
    private String tipoMensagem;

    @Schema(
            description = "Versão para controle otimista",
            example     = "1",
            required    = true
    )
    private Long version;

    @Schema(
            description = "Timestamp de criação do registro (ISO 8601)",
            example     = "2025-08-27T10:15:30",
            required    = true
    )
    private LocalDateTime createdAt;

    @Schema(
            description = "Timestamp da última atualização do registro (ISO 8601)",
            example     = "2025-08-28T14:05:00",
            required    = true
    )
    private LocalDateTime updatedAt;
}