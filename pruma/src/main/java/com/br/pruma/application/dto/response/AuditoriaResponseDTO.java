package com.br.pruma.application.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "DTO para respostas de auditoria")
public class AuditoriaResponseDTO {

    @ApiModelProperty(value = "Identificador único da auditoria", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID id;

    @ApiModelProperty(value = "CPF do cliente", example = "12345678900")
    private String clienteCpf;

    @ApiModelProperty(value = "Tipo de usuário que realizou a ação", example = "ADMINISTRADOR")
    private String tipoUsuario;

    @ApiModelProperty(value = "Descrição da ação realizada", example = "Atualização dos dados cadastrais")
    private String acao;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @ApiModelProperty(value = "Data e hora da ação", example = "01-08-2025 14:30:00")
    private LocalDateTime dataHora;

    @ApiModelProperty(value = "Versão do registro para controle de concorrência")
    private Long version;
}
