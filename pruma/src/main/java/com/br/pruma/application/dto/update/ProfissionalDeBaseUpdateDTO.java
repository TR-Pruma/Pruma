package com.br.pruma.application.dto.update;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Schema(name = "ProfissionalDeBaseUpdateDTO", description = "Dados para atualização parcial de um Profissional de Base")
public class ProfissionalDeBaseUpdateDTO {

    @Size(max = 100)
    @Schema(description = "Nome completo do profissional", example = "João da Silva")
    private String nome;

    @Size(max = 50)
    @Schema(description = "Especialidade do profissional", example = "Engenheiro Civil")
    private String especialidade;

    @Size(max = 20)
    @Schema(description = "Telefone de contato", example = "+55 11 91234-5678")
    private String telefone;
}