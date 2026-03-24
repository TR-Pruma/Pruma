package com.br.pruma.application.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AssinaturaDigitalRequestDTO {

        @NotNull(message = "Cliente CPF é obrigatório")
        private Long clienteCpf;

        @NotNull(message = "Tipo usuário é obrigatório")
        private Integer tipoUsuarioId;

        @NotNull(message = "Documento é obrigatório")
        private Integer documentoId;

    }

