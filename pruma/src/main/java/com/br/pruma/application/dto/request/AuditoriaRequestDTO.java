package com.br.pruma.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "DTO para criacao de registro de auditoria")
public class AuditoriaRequestDTO {

    @NotNull(message = "Usuario e obrigatorio")
    @Schema(description = "ID do usuario que realizou a acao", example = "1", required = true)
    private Integer usuarioId;

    @NotBlank(message = "Acao e obrigatoria")
    @Size(max = 100)
    @Schema(description = "Acao realizada", example = "UPDATE", required = true)
    private String acao;

    @NotBlank(message = "Entidade e obrigatoria")
    @Size(max = 100)
    @Schema(description = "Nome da entidade afetada", example = "Projeto", required = true)
    private String entidade;

    @Schema(description = "ID do registro afetado", example = "42")
    private Integer entidadeId;

    @Schema(description = "Detalhe da alteracao em formato livre", example = "{\"campo\": \"valor\"}")
    private String detalhe;
}
