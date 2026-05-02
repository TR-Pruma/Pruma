package com.br.pruma.application.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Schema(description = "DTO para respostas de auditoria")
public class AuditoriaResponseDTO {

    @Schema(description = "Identificador unico da auditoria", example = "1")
    private Integer id;

    @Schema(description = "ID do usuario que realizou a acao", example = "7")
    private Integer usuarioId;

    @Schema(description = "Nome do usuario que realizou a acao", example = "Joao Silva")
    private String usuarioNome;

    @Schema(description = "Acao realizada", example = "UPDATE")
    private String acao;

    @Schema(description = "Entidade afetada", example = "Projeto")
    private String entidade;

    @Schema(description = "ID do registro afetado", example = "42")
    private Integer entidadeId;

    @Schema(description = "Detalhe da alteracao")
    private String detalhe;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @Schema(description = "Data e hora de criacao do registro", example = "01-08-2025 14:30:00")
    private LocalDateTime createdAt;

    @Schema(description = "Versao do registro para controle de concorrencia", example = "1")
    private Long version;
}
