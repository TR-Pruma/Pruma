package com.br.pruma.application.dto.update;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Schema(name = "LogradouroUpdateDTO", description = "Dados para atualização parcial de um logradouro")
public class LogradouroUpdateDTO {

    @Size(max = 255, message = "tipo deve ter no máximo 255 caracteres")
    @Schema(
            description = "Novo tipo do logradouro (ex: Rua, Avenida, Travessa)",
            example     = "Travessa"
    )
    private String tipo;
}

