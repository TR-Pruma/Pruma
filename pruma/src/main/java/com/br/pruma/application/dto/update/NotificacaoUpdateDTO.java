package com.br.pruma.application.dto.update;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Schema(
        name        = "NotificacaoUpdateDTO",
        description = "Dados para atualização parcial de uma notificação"
)
public class NotificacaoUpdateDTO {

    @Schema(
            description = "CPF do cliente destinatário",
            example     = "12345678901"
    )
    private Long clienteCpf;

    @Schema(
            description = "Identificador do tipo de usuário",
            example     = "2"
    )
    private Integer tipoUsuarioId;

    @Size(max = 255, message = "mensagem deve ter no máximo 255 caracteres")
    @Schema(
            description = "Texto da notificação",
            example     = "Atualização de status"
    )
    private String mensagem;

    @Schema(
            description = "Nova data e hora da notificação (ISO 8601)",
            example     = "2025-09-01T12:00:00"
    )
    private LocalDateTime dataHora;

    @Schema(
            description = "Indica se a notificação já foi lida",
            example     = "true"
    )
    private Boolean lida;
}

