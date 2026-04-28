package com.br.pruma.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados de resposta de um anexo associado a um projeto")
public record AnexoResponseDTO(

        @Schema(description = "Identificador único do anexo", example = "1")
        Integer id,

        @Schema(description = "Identificador do projeto ao qual o anexo está vinculado", example = "42")
        Integer projetoId,

        @Schema(description = "Tipo do anexo (Ex: Imagem, Documento, Vídeo)", example = "Imagem")
        String tipoAnexo,

        @Schema(description = "Caminho onde o arquivo está armazenado", example = "/uploads/documento.pdf")
        String caminho
) {}
