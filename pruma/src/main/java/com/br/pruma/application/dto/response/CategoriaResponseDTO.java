package com.br.pruma.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Schema(name = "CategoriaResponseDTO", description = "Representação de categoria retornada pela API")
public class CategoriaResponseDTO {

    @Schema(description = "Identificador único da categoria", example = "1", required = true)
    private Integer id;

    @Schema(description = "Nome da categoria", example = "Elétrica", required = true)
    private String nome;

    @Schema(description = "Descrição da categoria", example = "Serviços de instalação elétrica")
    private String descricao;
}
