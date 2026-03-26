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
@Schema(name = "CategoriaRequestDTO", description = "Dados para criação/atualização de categoria")
public class CategoriaRequestDTO {

    @NotBlank(message = "O nome da categoria é obrigatório")
    @Size(max = 255, message = "O nome da categoria deve ter no máximo {max} caracteres")
    @Schema(description = "Nome da categoria", example = "Elétrica", required = true)
    private String nome;

    @Size(max = 255, message = "A descrição deve ter no máximo {max} caracteres")
    @Schema(description = "Descrição da categoria", example = "Serviços de instalação elétrica")
    private String descricao;
}
