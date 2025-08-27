package com.br.pruma.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Schema(name = "LembreteResponseDTO", description = "Dados retornados de um lembrete")
public class LembreteResponseDTO {

    @Schema(description = "Identificador único do lembrete", example = "1")
    private Integer id;

    @Schema(description = "CPF do cliente associado", example = "123.456.789-00")
    private String clienteCpf;

    @Schema(description = "Identificador do tipo de usuário", example = "2")
    private Integer tipoUsuarioId;

    @Schema(description = "Conteúdo da mensagem do lembrete", example = "Verificar documentação pendente")
    private String mensagem;

    @Schema(description = "Data e hora agendadas", example = "2025-08-27T09:30:00")
    private LocalDateTime dataHora;

    @Schema(description = "Versão para controle otimista", example = "5")
    private Long version;

    @Schema(description = "Timestamp de criação", example = "2025-08-25T17:45:00")
    private LocalDateTime createdAt;

    @Schema(description = "Timestamp da última atualização", example = "2025-08-26T08:10:00")
    private LocalDateTime updatedAt;

}