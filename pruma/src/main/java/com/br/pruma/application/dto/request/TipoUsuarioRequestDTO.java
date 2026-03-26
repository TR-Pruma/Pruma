package com.br.pruma.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Schema(name = "TipoUsuarioRequestDTO", description = "Dados para criação de um tipo de usuário")
public class TipoUsuarioRequestDTO {

    @NotBlank(message = "A descrição do tipo de usuário é obrigatória")
    @Size(max = 255, message = "A descrição deve ter no máximo {max} caracteres")
    @Schema(description = "Descrição do tipo de usuário", example = "Administrador", required = true)
    private String descricao;

    @Schema(description = "Indica se o tipo de usuário está ativo")
    private Boolean ativo = true;
}
