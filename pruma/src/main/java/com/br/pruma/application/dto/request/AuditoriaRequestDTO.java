package com.br.pruma.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AuditoriaRequestDTO {

    @NotBlank(message = "A entidade é obrigatória")
    private String entidade;

    @NotBlank(message = "A ação é obrigatória")
    private String acao;

    @NotBlank(message = "O usuário é obrigatório")
    private String usuario;

    @NotNull(message = "A data/hora é obrigatória")
    private LocalDateTime dataHora;
}
