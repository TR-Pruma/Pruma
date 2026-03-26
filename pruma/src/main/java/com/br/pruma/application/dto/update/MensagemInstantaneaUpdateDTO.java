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
        name        = "MensagemInstantaneaUpdateDTO",
        description = "Dados para atualização parcial de uma mensagem instantânea"
)
public class MensagemInstantaneaUpdateDTO {

    @Schema(
            description = "CPF do cliente remetente",
            example     = "12345678901"
    )
    private Long clienteCpf;

    @Schema(
            description = "Identificador do tipo de usuário",
            example     = "2"
    )
    private Integer tipoUsuarioId;

    @Size(max = 255, message = "destinatarioId deve ter no máximo 255 caracteres")
    @Schema(
            description = "ID ou canal do destinatário",
            example     = "user@example.com"
    )
    private String destinatarioId;

    @Size(max = 15, message = "tipoDestinatario deve ter no máximo 15 caracteres")
    @Schema(
            description = "Categoria do destinatário (ex: USER, GROUP)",
            example     = "USER"
    )
    private String tipoDestinatario;

    @Size(max = 4000, message = "conteudo deve ter no máximo 4000 caracteres")
    @Schema(
            description = "Corpo da mensagem",
            example     = "Olá, atualizei o conteúdo!"
    )
    private String conteudo;

    @Schema(
            description = "Nova data e hora do envio da mensagem (ISO 8601)",
            example     = "2025-09-01T12:00:00"
    )
    private LocalDateTime dataHora;
}