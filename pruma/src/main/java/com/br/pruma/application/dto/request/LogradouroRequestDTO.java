package com.br.pruma.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Schema(name = "LogradouroRequestDTO", description = "Dados para criação de um logradouro")
public class LogradouroRequestDTO {

    @NotBlank(message = "tipo é obrigatório")
    @Size(max = 255, message = "tipo deve ter no máximo 255 caracteres")
    @Schema(
            description = "Tipo do logradouro (ex: Rua, Avenida, Travessa)",
            example     = "Avenida",
            required    = true
    )
    private String tipo;
}
