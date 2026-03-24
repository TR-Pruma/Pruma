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
@Schema(name = "LogAlteracaoAuxRequestDTO", description = "Dados para criação ou atualização do tipo de alteração auxiliar")
public class LogAlteracaoAuxRequestDTO {

    @NotNull(message = "logId é obrigatório")
    @Schema(description = "Identificador do log de alteração principal", example = "1", required = true)
    private Integer logId;

    @NotBlank(message = "tipoAlteracao é obrigatório")
    @Size(max = 15, message = "tipoAlteracao deve ter no máximo 15 caracteres")
    @Schema(description = "Código do tipo de alteração (ex: CRIACAO, ATUALIZACAO, REMOCAO)",
            example = "CRIACAO", required = true)
    private String tipoAlteracao;

}
