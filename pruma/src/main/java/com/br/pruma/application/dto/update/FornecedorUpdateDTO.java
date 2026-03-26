package com.br.pruma.application.dto.update;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Schema(name = "FornecedorUpdateDTO", description = "Dados para atualização parcial de fornecedor")
public class FornecedorUpdateDTO {

    @Size(max = 255, message = "O nome deve ter no máximo {max} caracteres")
    @Schema(description = "Nome do fornecedor", example = "Fornecedor ABC Ltda")
    private String nome;

    @Pattern(
            regexp = "^(\\d{14}|\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2})$",
            message = "CNPJ inválido"
    )
    @Schema(description = "CNPJ do fornecedor", example = "12.345.678/0001-90")
    private String cnpj;

    @Size(max = 100, message = "O contato deve ter no máximo {max} caracteres")
    @Schema(description = "Contato do fornecedor", example = "(11) 99999-9999")
    private String contato;
}
