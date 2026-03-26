package com.br.pruma.application.dto.update;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Schema(name = "StatusSolicitacaoUpdateDTO", description = "Dados para atualização parcial de status de solicitação")
public class StatusSolicitacaoUpdateDTO {

    @Size(max = 255, message = "Descrição deve ter no máximo {max} caracteres")
    @Schema(description = "Descrição do status", example = "Aprovado")
    private String descricaoSolicitacao;
}
