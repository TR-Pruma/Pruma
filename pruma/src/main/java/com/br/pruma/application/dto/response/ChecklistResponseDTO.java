package com.br.pruma.application.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Schema(description = "DTO de resposta para Checklist")
public class ChecklistResponseDTO {

    @Schema(description = "ID do checklist", example = "1", required = true)
    private Integer id;

    @Schema(description = "Nome do checklist", example = "Checklist de Qualidade", required = true)
    private String nome;

    @Schema(description = "ID do projeto", example = "1", required = true)
    private Integer projetoId;

    @Schema(description = "Nome do projeto", example = "Projeto ABC", required = true)
    private String projetoNome;

    @Schema(description = "Itens do checklist")
    @Builder.Default
    private List<ItemChecklistResponseDTO> itens = List.of();

    @Schema(description = "Percentual de conclusão", example = "75")
    private Long percentualConcluido;

    @Schema(description = "Flag de ativação do checklist", example = "true")
    private boolean ativo;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @Schema(description = "Data de criação", example = "01/08/2025 14:30:00", required = true)
    private LocalDateTime dataCriacao;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @Schema(description = "Data da última atualização", example = "01/08/2025 14:30:00", required = true)
    private LocalDateTime dataAtualizacao;
}
