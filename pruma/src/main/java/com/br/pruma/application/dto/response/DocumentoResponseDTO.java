package com.br.pruma.application.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de retorno de um documento")
public class DocumentoResponseDTO {

    @Schema(description = "ID do documento", example = "1")
    private Integer id;

    @Schema(description = "ID do projeto", example = "1")
    private Integer projetoId;

    @Schema(description = "Nome do projeto", example = "Projeto XYZ")
    private String projetoNome;

    @Schema(description = "ID do tipo de documento", example = "1")
    private Integer tipoDocumentoId;
    @Schema(description = "Nome do tipo de documento", example = "Contrato")
    private String tipoDocumentoNome;

    @Schema(description = "Nome do arquivo", example = "contrato.pdf")
    private String nomeArquivo;

    @Schema(description = "Tipo do arquivo", example = "application/pdf")
    private String tipoArquivo;

    @Schema(description = "Tamanho do arquivo em bytes", example = "1048576")
    private Long tamanhoArquivo;

    @Schema(description = "URL para download do arquivo", example = "/api/documentos/1/download")
    private String urlDownload;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @Schema(description = "Data e hora do upload", example = "01/08/2025 10:30:00")
    private LocalDateTime dataUpload;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @Schema(description = "Data da última atualização", example = "01/08/2025 10:30:00")
    private LocalDateTime dataAtualizacao;

    @Schema(description = "Versão do registro", example = "1")
    private Long versao;

    @Schema(description = "Indica se o documento está ativo", example = "true")
    private Boolean ativo;
}

