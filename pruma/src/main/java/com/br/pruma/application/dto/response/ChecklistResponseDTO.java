package com.br.pruma.application.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "DTO para resposta de Checklist")
public class ChecklistResponseDTO {

    @ApiModelProperty(value = "ID do checklist", example = "1")
    private Integer id;

    @ApiModelProperty(value = "Nome do checklist", example = "Checklist de Qualidade")
    private String nome;

    @ApiModelProperty(value = "ID do projeto", example = "1")
    private Integer projetoId;

    @ApiModelProperty(value = "Nome do projeto", example = "Projeto ABC")
    private String projetoNome;

    @ApiModelProperty(value = "Lista de itens do checklist")
    private List<ItemChecklistResponseDTO> itens = new ArrayList<>();

    @ApiModelProperty(value = "Percentual de conclusão", example = "75")
    private Long percentualConcluido;

    @ApiModelProperty(value = "Status de ativação do checklist", example = "true")
    private boolean ativo;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @ApiModelProperty(value = "Data de criação", example = "01/08/2025 14:30:00")
    private LocalDateTime dataCriacao;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @ApiModelProperty(value = "Data da última atualização", example = "01/08/2025 14:30:00")
    private LocalDateTime dataAtualizacao;
}
