package com.br.pruma.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Dados para criação/atualização de uma comunicação")
public class ComunicacaoRequestDTO {

    @NotNull(message = "O ID do projeto é obrigatório")
    @Schema(description = "ID do projeto associado à comunicação", example = "1")
    private Integer projetoId;

    @NotNull(message = "O ID do cliente é obrigatório")
    @Schema(description = "ID do cliente associado à comunicação", example = "1")
    private Integer clienteId;

    @NotBlank(message = "A mensagem é obrigatória")
    @Size(min = 1, max = 4000, message = "A mensagem deve ter entre 1 e 4000 caracteres")
    @Schema(description = "Conteúdo da mensagem", example = "Precisamos agendar uma reunião para discutir o projeto")
    private String mensagem;

    @NotBlank(message = "O tipo do remetente é obrigatório")
    @Size(max = 15, message = "O tipo do remetente deve ter no máximo 15 caracteres")
    @Schema(description = "Tipo do remetente da mensagem", example = "CLIENTE")
    private String tipoRemetente;
}
