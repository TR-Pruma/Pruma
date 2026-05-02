package com.br.pruma.application.dto.update;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "DTO para atualizacao parcial de auditoria")
public class AuditoriaUpdateDTO {

    @Size(max = 100)
    @Schema(description = "Acao realizada", example = "DELETE")
    private String acao;

    @Size(max = 100)
    @Schema(description = "Nome da entidade afetada", example = "Obra")
    private String entidade;

    @Schema(description = "ID do registro afetado", example = "10")
    private Integer entidadeId;

    @Schema(description = "Detalhe da alteracao")
    private String detalhe;
}
