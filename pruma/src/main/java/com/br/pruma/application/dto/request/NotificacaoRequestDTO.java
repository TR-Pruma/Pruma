package com.br.pruma.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Schema(
        name        = "NotificacaoRequestDTO",
        description = "Dados para criação de uma notificação"
)
public class NotificacaoRequestDTO {

    @NotNull(message = "clienteCpf é obrigatório")
    @Schema(
            description = "CPF do cliente destinatário",
            example     = "12345678901",
            required    = true
    )
    private Long clienteCpf;

    @NotNull(message = "tipoUsuarioId é obrigatório")
    @Schema(
            description = "Identificador do tipo de usuário",
            example     = "2",
            required    = true
    )
    private Integer tipoUsuarioId;

    @NotBlank(message = "mensagem é obrigatória")
    @Size(max = 255, message = "mensagem deve ter no máximo 255 caracteres")
    @Schema(
            description = "Texto da notificação",
            example     = "Sua atividade foi iniciada",
            required    = true
    )
    private String mensagem;

    @NotNull(message = "dataHora é obrigatória")
    @Schema(
            description = "Data e hora da notificação (ISO 8601)",
            example     = "2025-09-01T10:15:30",
            required    = true
    )
    private LocalDateTime dataHora;
}