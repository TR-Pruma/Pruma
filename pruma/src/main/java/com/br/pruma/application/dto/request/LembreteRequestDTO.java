package com.br.pruma.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Schema(name = "LembreteRequestDTO", description = "Dados para criação de um lembrete")

public class LembreteRequestDTO {
    @NotBlank(message = "clienteCpf é obrigatório")
    @Pattern(
            regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}",
            message = "clienteCpf deve seguir o formato 000.000.000-00"
    )
    @Schema(
            description = "CPF do cliente associado",
            example = "123.456.789-00",
            required = true
    )
    private String clienteCpf;

    @NotNull(message = "tipoUsuarioId é obrigatório")
    @Schema(
            description = "Identificador do tipo de usuário",
            example = "2",
            required = true
    )
    private Integer tipoUsuarioId;

    @NotBlank(message = "mensagem é obrigatória")
    @Size(max = 2000, message = "mensagem deve ter no máximo 2000 caracteres")
    @Schema(
            description = "Conteúdo da mensagem do lembrete",
            example = "Verificar documentação pendente",
            required = true
    )
    private String mensagem;

    @NotNull(message = "dataHora é obrigatória")
    @FutureOrPresent(message = "dataHora não pode ser no passado")
    @Schema(
            description = "Data e hora do lembrete",
            example = "2025-08-27T09:30:00",
            required = true
    )
    private LocalDateTime dataHora;

}
