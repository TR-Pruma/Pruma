package com.br.pruma.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "ItemOrcamentoResponseDTO", description = "Dados retornados de um item de orçamento")
public class ItemOrcamentoResponseDTO {

    @Schema(description = "Identificador único do item", example = "1")
    private Integer id;

    @Schema(description = "Identificador do orçamento pai", example = "1")
    private Integer orcamentoId;

    @Schema(description = "Descrição detalhada do item", example = "Serviço de instalação elétrica")
    private String descricao;

    @Schema(description = "Quantidade de unidades", example = "10")
    private Integer quantidade;

    @Schema(description = "Valor unitário do item", example = "150.50")
    private BigDecimal valorUnitario;

    @Schema(description = "Valor total calculado (quantidade × unitário)", example = "1505.00")
    private BigDecimal valorTotal;

    @Schema(description = "Versão para controle otimista", example = "2")
    private Long version;

    @Schema(description = "Data de criação do registro", example = "2025-08-25T23:43:00")
    private LocalDateTime createdAt;

    @Schema(description = "Data da última atualização", example = "2025-08-26T00:01:00")
    private LocalDateTime updatedAt;

}
