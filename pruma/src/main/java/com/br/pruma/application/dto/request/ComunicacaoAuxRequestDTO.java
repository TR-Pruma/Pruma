package com.br.pruma.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComunicacaoAuxRequestDTO {

    @NotNull(message = "O ID da comunicação é obrigatório")
    private Integer comunicacaoId;

    @NotBlank(message = "O tipo da mensagem é obrigatório")
    @Size(max = 15, message = "O tipo da mensagem deve ter no máximo 15 caracteres")
    private String tipoMensagem;
}
