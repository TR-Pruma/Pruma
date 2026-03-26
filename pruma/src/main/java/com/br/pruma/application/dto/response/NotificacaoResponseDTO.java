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
        name              = "NotificacaoResponseDTO",
        description       = "Dados retornados de uma notificação",
        requiredProperties = {
                "id",
                "clienteCpf",
                "tipoUsuarioId",
                "mensagem",
                "dataHora",
                "lida",
                "version",
                "createdAt",
                "updatedAt"
        }
)
public class NotificacaoResponseDTO {

    @Schema(
            description = "Identificador único da notificação",
            example     = "10",
            required    = true
    )
    private Integer id;

    @Schema(
            description = "CPF do cliente destinatário",
            example     = "12345678901",
            required    = true
    )
    private Long clienteCpf;

    @Schema(
            description = "Identificador do tipo de usuário",
            example     = "2",
            required    = true
    )
    private Integer tipoUsuarioId;

    @Schema(
            description = "Texto da notificação",
            example     = "Sua atividade foi iniciada",
            required    = true
    )
    private String mensagem;

    @Schema(
            description = "Data e hora da notificação",
            example     = "2025-09-01T10:15:30",
            required    = true
    )
    private LocalDateTime dataHora;

    @Schema(
            description = "Indica se a notificação já foi lida",
            example     = "false",
            required    = true
    )
    private Boolean lida;

    @Schema(
            description = "Versão para controle otimista",
            example     = "1",
            required    = true
    )
    private Long version;

    @Schema(
            description = "Timestamp de criação do registro",
            example     = "2025-08-27T10:15:30",
            required    = true
    )
    private LocalDateTime createdAt;

    @Schema(
            description = "Timestamp da última atualização do registro",
            example     = "2025-08-28T14:05:00",
            required    = true
    )
    private LocalDateTime updatedAt;
}