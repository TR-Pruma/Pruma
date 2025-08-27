package com.br.pruma.application.dto.update;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Schema(name = "LembreteUpdateDTO", description = "Dados para atualização parcial de um lembrete")
public class LembreteUpdateDTO {

    @Size(max = 2000, message = "mensagem deve ter no máximo 2000 caracteres")
    @Schema(
            description = "Nova mensagem do lembrete",
            example = "Atualizar prazo de entrega"
    )
    private String mensagem;

    @FutureOrPresent(message = "dataHora não pode ser no passado")
    @Schema(
            description = "Nova data e hora do lembrete",
            example = "2025-08-28T14:00:00"
    )
    private LocalDateTime dataHora;

}
