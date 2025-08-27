package com.br.pruma.application.dto.response;


import com.br.pruma.core.enums.StatusOrcamento;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Schema(name = "OrcamentoResponseDTO", description = "Dados retornados de um orçamento")
public class OrcamentoResponseDTO {

    @Schema(description = "Identificador único do orçamento", example = "10")
    private Integer id;

    @Schema(description = "Identificador do projeto", example = "42")
    private Integer projetoId;

    @Schema(description = "CNPJ da empresa contratante", example = "12.345.678/0001-90")
    private String empresaCnpj;

    @Schema(description = "Valor total do orçamento", example = "15000.75")
    private BigDecimal valor;

    @Schema(description = "Data de envio do orçamento", example = "2025-08-26")
    private LocalDate dataEnvio;

    @Schema(description = "Status do orçamento", example = "PENDENTE")
    private StatusOrcamento status;

    @Schema(description = "Versão para controle otimista", example = "3")
    private Long version;

    @Schema(description = "Timestamp de criação", example = "2025-08-25T23:43:00")
    private LocalDateTime createdAt;

    @Schema(description = "Timestamp da última atualização", example = "2025-08-26T00:01:00")
    private LocalDateTime updatedAt;

}
