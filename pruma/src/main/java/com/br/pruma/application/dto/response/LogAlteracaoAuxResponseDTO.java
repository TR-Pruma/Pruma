package com.br.pruma.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Schema(name = "LogAlteracaoAuxResponseDTO", description = "Dados retornados do tipo de alteração auxiliar")
public class LogAlteracaoAuxResponseDTO {

    @Schema(description = "Identificador do registro auxiliar (igual ao ID do log de alteração)", example = "1")
    private Integer id;

    @Schema(description = "Código do tipo de alteração (ex: CRIACAO, ATUALIZACAO, REMOCAO)",
            example = "CRIACAO")
    private String tipoAlteracao;

}
