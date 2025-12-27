package com.br.pruma.application.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;

@Schema(name = "RequisicaoMaterialResponseDTO", description = "Dados de requisição de material retornado")
public record RequisicaoMaterialResponseDTO(

        @Schema(description = "Identificador único da requisição", example = "1", required = true)
        Integer id,

        @Schema(description = "Identificador da obra relacionada", example = "1", required = true)
        Integer obraId,

        @Schema(description = "Nome da obra", example = "Construção edifício A")
        String obraNome,

        @Schema(description = "Identificador do material requisitado", example = "10", required = true)
        Integer materialId,

        @Schema(description = "Nome do material", example = "Cimento Portland")
        String materialNome,

        @Schema(description = "Quantidade requisitada", example = "100", required = true)
        Integer quantidade,

        @Schema(description = "Data da requisição", example = "2025-12-20", required = true)
        LocalDate dataRequisicao,

        @Schema(description = "Data de criação do registro no sistema", example = "2025-12-20", required = true)
        LocalDate createdAt

) {}
