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
        name        = "MensagemInstantaneaRequestDTO",
        description = "Dados para criação de uma mensagem instantânea"
)
public class MensagemInstantaneaRequestDTO {

    @NotNull(message = "clienteCpf é obrigatório")
    @Schema(
            description = "CPF do cliente remetente",
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

    @NotBlank(message = "destinatarioId é obrigatório")
    @Size(max = 255, message = "destinatarioId deve ter no máximo 255 caracteres")
    @Schema(
            description = "ID ou canal do destinatário",
            example     = "user@example.com",
            required    = true
    )
    private String destinatarioId;

    @NotBlank(message = "tipoDestinatario é obrigatório")
    @Size(max = 15, message = "tipoDestinatario deve ter no máximo 15 caracteres")
    @Schema(
            description = "Categoria do destinatário (ex: USER, GROUP)",
            example     = "USER",
            required    = true
    )
    private String tipoDestinatario;

    @NotBlank(message = "conteudo é obrigatório")
    @Size(max = 4000, message = "conteudo deve ter no máximo 4000 caracteres")
    @Schema(
            description = "Corpo da mensagem",
            example     = "Olá, sua atividade foi iniciada!",
            required    = true
    )
    private String conteudo;

    @NotNull(message = "dataHora é obrigatória")
    @Schema(
            description = "Data e hora do envio da mensagem (ISO 8601)",
            example     = "2025-09-01T10:15:30",
            required    = true
    )
    private LocalDateTime dataHora;
}

