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
        name        = "MensagemInstantaneaResponseDTO",
        description = "Dados retornados de uma mensagem instantânea",
        requiredProperties = {
                "id",
                "clienteCpf",
                "tipoUsuarioId",
                "destinatarioId",
                "tipoDestinatario",
                "conteudo",
                "dataHora",
                "version",
                "createdAt",
                "updatedAt"
        }
)
public class MensagemInstantaneaResponseDTO {

    @Schema(
            description = "Identificador único da mensagem",
            example     = "15",
            required    = true
    )
    private Integer id;

    @Schema(
            description = "CPF do cliente remetente",
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
            description = "ID ou canal do destinatário",
            example     = "user@example.com",
            required    = true
    )
    private String destinatarioId;

    @Schema(
            description = "Categoria do destinatário",
            example     = "USER",
            required    = true
    )
    private String tipoDestinatario;

    @Schema(
            description = "Corpo da mensagem",
            example     = "Olá, sua atividade foi iniciada!",
            required    = true
    )
    private String conteudo;

    @Schema(
            description = "Data e hora do envio da mensagem",
            example     = "2025-09-01T10:15:30",
            required    = true
    )
    private LocalDateTime dataHora;

    @Schema(
            description = "Versão para controle otimista",
            example     = "3",
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