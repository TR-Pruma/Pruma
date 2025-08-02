package com.br.pruma.application.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "DTO para criação/atualização de Cliente")
public class ClienteRequestDTO {

    @NotBlank(message = "CPF é obrigatório")
    @Pattern(regexp = "^\\d{11}$", message = "CPF deve conter 11 dígitos numéricos")
    @ApiModelProperty(value = "CPF do cliente", required = true, example = "12345678900")
    private String cpf;

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    @ApiModelProperty(value = "Nome do cliente", required = true, example = "João da Silva")
    private String nome;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ser válido")
    @ApiModelProperty(value = "Email do cliente", required = true, example = "joao@email.com")
    private String email;

    @NotBlank(message = "Telefone é obrigatório")
    @Pattern(regexp = "^\\d{10,11}$", message = "Telefone deve ter 10 ou 11 dígitos")
    @ApiModelProperty(value = "Telefone do cliente", required = true, example = "11999999999")
    private String telefone;

    @ApiModelProperty(value = "ID do endereço do cliente", example = "1")
    private Integer enderecoId;

    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 6, max = 100, message = "Senha deve ter entre 6 e 100 caracteres")
    @ApiModelProperty(value = "Senha do cliente", required = true, example = "senha123")
    private String senha;
}
