package com.br.pruma.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Schema(
        name        = "MensagemInstantaneaAuxRequestDTO",
        description = "Dados para criação de metadados de tipo de mensagem instantânea"
)
public class MensagemInstantaneaAuxRequestDTO {

    @NotNull(message = "mensagemId é obrigatório")
    @Schema(
            description = "Identificador da mensagem instantânea principal",
            example     = "15",
            required    = true
    )
    private Integer mensagemId;

    @NotBlank(message = "tipoMensagem é obrigatório")
    @Size(max = 15, message = "tipoMensagem deve ter no máximo 15 caracteres")
    @Schema(
            description = "Categoria da mensagem (ex: ALERTA, NOTIFICACAO)",
            example     = "ALERTA",
            required    = true
    )
    private String tipoMensagem;
}