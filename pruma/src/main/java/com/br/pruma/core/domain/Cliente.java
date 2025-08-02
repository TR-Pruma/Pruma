package com.br.pruma.core.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "cpf")
@Table(name = "cliente")
@SQLDelete(sql = "UPDATE cliente SET ativo = false WHERE cliente_cpf = ?")
@FilterDef(name = "ativoFilter", parameters = @ParamDef(name = "ativo", type = boolean.class))
@Filter(name = "ativoFilter", condition = "ativo = :ativo")
@ApiModel(description = "Representa um cliente no sistema")
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "cliente_cpf")
    @Pattern(regexp = "^\\d{11}$", message = "CPF deve conter 11 dígitos numéricos")
    @ApiModelProperty(value = "CPF do cliente", example = "12345678900")
    private String cpf;

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    @Column(name = "nome", length = 100, nullable = false)
    @ApiModelProperty(value = "Nome do cliente", example = "João da Silva")
    private String nome;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ser válido")
    @Column(name = "email", length = 100, nullable = false, unique = true)
    @ApiModelProperty(value = "Email do cliente", example = "joao@email.com")
    private String email;

    @NotBlank(message = "Telefone é obrigatório")
    @Pattern(regexp = "^\\d{10,11}$", message = "Telefone deve ter 10 ou 11 dígitos")
    @Column(name = "telefone", nullable = false)
    @ApiModelProperty(value = "Telefone do cliente", example = "11999999999")
    private String telefone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_endereco", referencedColumnName = "id_endereco")
    @ApiModelProperty(value = "Endereço do cliente")
    private Endereco endereco;

    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 6, max = 100, message = "Senha deve ter entre 6 e 100 caracteres")
    @Column(name = "senha", length = 100, nullable = false)
    @ApiModelProperty(value = "Senha do cliente")
    private String senha;

    @Column(name = "ativo", nullable = false)
    @ApiModelProperty(value = "Indica se o cliente está ativo", example = "true")
    private boolean ativo = true;
}
