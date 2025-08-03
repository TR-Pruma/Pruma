package com.br.pruma.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados para upload de documento")
public class DocumentoRequestDTO {

    @NotNull(message = "O ID do projeto é obrigatório")
    @Schema(description = "ID do projeto", example = "1")
    private Integer projetoId;

    @NotNull(message = "O ID do tipo de documento é obrigatório")
    @Schema(description = "ID do tipo de documento", example = "1")
    private Integer tipoDocumentoId;

    @NotNull(message = "O arquivo é obrigatório")
    @Schema(description = "Arquivo a ser enviado")
    private MultipartFile arquivo;

    @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres")
    @Schema(description = "Descrição opcional do documento", example = "Contrato assinado do projeto")
    private String descricao;
}
