package com.br.pruma.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Schema(name = "LogAlteracaoRequestDTO", description = "Dados para criação de um log de alteração")
public class LogAlteracaoRequestDTO {

    @NotNull(message = "projetoId é obrigatório")
    @Schema(description = "Identificador do projeto", example = "100", required = true)
    private Integer projetoId;

    @NotBlank(message = "clienteCpf é obrigatório")
    @Pattern(
            regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}",
            message = "clienteCpf deve seguir o padrão 000.000.000-00"
    )
    @Schema(description = "CPF do cliente relacionado", example = "123.456.789-00", required = true)
    private String clienteCpf;

    @NotNull(message = "tipoUsuarioId é obrigatório")
    @Schema(description = "Identificador do tipo de usuário", example = "2", required = true)
    private Integer tipoUsuarioId;

    @NotBlank(message = "descrição é obrigatória")
    @Size(max = 255, message = "descrição deve ter no máximo 255 caracteres")
    @Schema(description = "Descrição da alteração realizada", example = "Status atualizado para APROVADO", required = true)
    private String descricao;
}