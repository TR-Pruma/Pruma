package com.br.pruma.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Schema(name = "MaterialResponseDTO", description = "Dados retornados de um material")
public class MaterialResponseDTO {

    @Schema(description = "Identificador único do material", example = "10")
    private Integer id;

    @Schema(description = "Descrição detalhada do material", example = "Cimento CP II 50kg")
    private String descricao;

    @Schema(description = "Quantidade disponível do material", example = "100")
    private Integer quantidade;

    @Schema(description = "Custo unitário do material", example = "25.50")
    private BigDecimal custoUnitario;

    @Schema(description = "Versão para controle otimista", example = "3")
    private Long version;

    @Schema(description = "Timestamp de criação do registro", example = "2025-08-27T10:15:30")
    private LocalDateTime createdAt;

    @Schema(description = "Timestamp da última atualização", example = "2025-08-28T14:05:00")
    private LocalDateTime updatedAt;
}

