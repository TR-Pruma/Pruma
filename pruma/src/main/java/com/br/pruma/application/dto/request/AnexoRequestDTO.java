package com.br.pruma.application.dto.request;

import com.br.pruma.core.domain.Projeto;
import lombok.Data;



import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Dados para criação de um anexo associado a um projeto")
public class AnexoRequestDTO {

    @NotNull(message = "O ID do projeto é obrigatório.")
    @Schema(description = "Identificador do projeto ao qual o anexo será vinculado", example = "42", required = true)
    private Integer projetoId;

    @NotBlank(message = "O tipo do anexo é obrigatório.")
    @Size(max = 15, message = "O tipo do anexo deve ter no máximo {max} caracteres.")
    @Schema(description = "Tipo do anexo (Ex: Imagem, Documento, Vídeo)", example = "Imagem", required = true)
    private String tipoAnexo;

    @NotBlank(message = "O caminho do arquivo é obrigatório.")
    @Size(max = 1024, message = "O caminho do arquivo deve ter no máximo {max} caracteres.")
    @Schema(description = "Caminho onde o arquivo está armazenado", example = "/uploads/documento.pdf", required = true)
    private String caminhoArquivo;
}