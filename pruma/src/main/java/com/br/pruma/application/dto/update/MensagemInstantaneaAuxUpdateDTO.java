package com.br.pruma.application.dto.update;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Schema(
        name        = "MensagemInstantaneaAuxUpdateDTO",
        description = "Dados para atualização parcial dos metadados de tipo de uma mensagem instantânea"
)
public class MensagemInstantaneaAuxUpdateDTO {

    @Size(max = 15, message = "tipoMensagem deve ter no máximo 15 caracteres")
    @Schema(
            description = "Nova categoria da mensagem instantânea (ex: ALERTA, NOTIFICACAO)",
            example     = "NOTIFICACAO"
    )
    private String tipoMensagem;
}

