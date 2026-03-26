package com.br.pruma.application.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Schema(description = "DTO para criação/atualização de Checklist")
public class ChecklistRequestDTO {

    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 3, max = 50, message = "O nome deve ter entre 3 e 50 caracteres")
    @Schema(description = "Nome do checklist", example = "Checklist de Qualidade", required = true)
    private String nome;

    @NotNull(message = "O ID do projeto é obrigatório")
    @Schema(description = "ID do projeto ao qual o checklist pertence", example = "1", required = true)
    private Integer projetoId;
}
