package com.br.pruma.application.dto.update;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Schema(name = "CategoriaUpdateDTO", description = "Dados para atualização parcial de categoria")
public class CategoriaUpdateDTO {

    @Size(max = 255, message = "O nome da categoria deve ter no máximo {max} caracteres")
    @Schema(description = "Nome da categoria", example = "Elétrica")
    private String nome;

    @Size(max = 255, message = "A descrição deve ter no máximo {max} caracteres")
    @Schema(description = "Descrição da categoria", example = "Serviços de instalação elétrica")
    private String descricao;
}
