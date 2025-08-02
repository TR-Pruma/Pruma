package com.br.pruma.application.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "DTO para resposta de Cliente")
public class ClienteResponseDTO {

    @ApiModelProperty(value = "CPF do cliente", example = "12345678900")
    private String cpf;

    @ApiModelProperty(value = "Nome do cliente", example = "João da Silva")
    private String nome;

    @ApiModelProperty(value = "Email do cliente", example = "joao@email.com")
    private String email;

    @ApiModelProperty(value = "Telefone do cliente", example = "11999999999")
    private String telefone;

    @ApiModelProperty(value = "Endereço completo do cliente")
    private EnderecoResponseDTO endereco;

    @ApiModelProperty(value = "Indica se o cliente está ativo", example = "true")
    private boolean ativo;
}
