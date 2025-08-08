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
public class ComunicacaoRequestDTO {

    @NotNull(message = "ID do projeto é obrigatório")
    private Integer projetoId;

    @NotNull(message = "ID do cliente é obrigatório")
    private Integer clienteId;

    @NotBlank(message = "Tipo de remetente é obrigatório")
    @Size(max = 15, message = "Tipo de remetente deve ter no máximo 15 caracteres")
    private String tipoRemetente;

    @NotBlank(message = "Mensagem é obrigatória")
    private String mensagem;
}
