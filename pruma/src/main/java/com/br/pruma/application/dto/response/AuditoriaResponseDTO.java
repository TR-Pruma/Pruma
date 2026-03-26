package com.br.pruma.application.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Schema(description = "DTO para respostas de auditoria")
public class AuditoriaResponseDTO {

    @Schema(
            description = "Identificador único da auditoria",
            example     = "123e4567-e89b-12d3-a456-426614174000",
            required    = true
    )
    private UUID id;

    @Schema(
            description = "CPF do cliente",
            example     = "12345678900",
            required    = true
    )
    private String clienteCpf;

    @Schema(
            description = "Tipo de usuário que realizou a ação",
            example     = "ADMINISTRADOR",
            required    = true
    )
    private String tipoUsuario;

    @Schema(
            description = "Descrição da ação realizada",
            example     = "Atualização dos dados cadastrais",
            required    = true
    )
    private String acao;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @Schema(
            description = "Data e hora da ação",
            example     = "01-08-2025 14:30:00",
            required    = true
    )
    private LocalDateTime dataHora;

    @Schema(
            description = "Versão do registro para controle de concorrência",
            example     = "1"
    )
    private Long version;
}
