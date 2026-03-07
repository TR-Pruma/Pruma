package com.br.pruma.application.dto.update;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Schema(name = "TipoUsuarioUpdateDTO", description = "Dados para atualização parcial de tipo de usuário")
public class TipoUsuarioUpdateDTO {

    @Size(max = 255, message = "A descrição deve ter no máximo {max} caracteres")
    @Schema(description = "Descrição do tipo de usuário", example = "Administrador")
    private String descricao;

    @Schema(description = "Indica se o tipo de usuário está ativo")
    private Boolean ativo;
}
