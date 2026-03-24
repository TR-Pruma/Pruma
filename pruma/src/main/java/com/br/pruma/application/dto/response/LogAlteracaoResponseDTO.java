package com.br.pruma.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Schema(name = "LogAlteracaoResponseDTO", description = "Dados retornados de um log de alteração")
public class LogAlteracaoResponseDTO {

    @Schema(description = "Identificador único do log", example = "15")
    private Integer id;

    @Schema(description = "Identificador do projeto", example = "100")
    private Integer projetoId;

    @Schema(description = "CPF do cliente relacionado", example = "123.456.789-00")
    private String clienteCpf;

    @Schema(description = "Identificador do tipo de usuário", example = "2")
    private Integer tipoUsuarioId;

    @Schema(description = "Descrição da alteração realizada", example = "Status atualizado para APROVADO")
    private String descricao;

    @Schema(description = "Timestamp de criação do log", example = "2025-08-26T14:22:00")
    private LocalDateTime dataHora;

}
