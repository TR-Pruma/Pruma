package com.br.pruma.application.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@ApiModel(description = "DTO para requisições de auditoria")
public class AuditoriaRequestDTO {

    @NotBlank(message = "CPF do cliente é obrigatório")
    @Pattern(regexp = "^\\d{11}$", message = "CPF deve conter 11 dígitos numéricos")
    @ApiModelProperty(value = "CPF do cliente", required = true, example = "12345678900")
    private String clienteCpf;

    @NotNull(message = "Tipo de usuário é obrigatório")
    @ApiModelProperty(value = "Identificador do tipo de usuário", required = true, example = "1")
    private Integer tipoUsuario;

    @NotBlank(message = "Descrição da ação é obrigatória")
    @ApiModelProperty(value = "Descrição da ação realizada", required = true,
                     example = "Atualização dos dados cadastrais")
    private String acao;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @ApiModelProperty(value = "Data e hora da ação (opcional - preenchido automaticamente se não informado)",
                     example = "01-08-2025 14:30:00")
    private LocalDateTime dataHora;
}
