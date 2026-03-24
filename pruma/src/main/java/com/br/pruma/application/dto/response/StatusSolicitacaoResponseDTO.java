package com.br.pruma.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Schema(name = "StatusSolicitacaoResponseDTO", description = "Representação de status de solicitação retornada pela API")
public class StatusSolicitacaoResponseDTO {

    @Schema(description = "Identificador único do status", example = "1", required = true)
    private Integer id;

    @Schema(description = "Descrição do status", example = "Pendente", required = true)
    private String descricaoSolicitacao;

    @Schema(description = "Data de criação do status")
    private LocalDate createdAt;
}
