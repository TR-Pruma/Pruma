package com.br.pruma.application.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de retorno de um cronograma")
public class CronogramaResponseDTO {

    @Schema(description = "ID do cronograma", example = "1")
    private Integer id;

    @Schema(description = "ID do projeto", example = "1")
    private Integer projetoId;

    @Schema(description = "Nome do projeto", example = "Projeto XYZ")
    private String projetoNome;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Schema(description = "Data de início do cronograma", example = "01/08/2025")
    private LocalDate dataInicio;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Schema(description = "Data de fim do cronograma", example = "31/12/2025")
    private LocalDate dataFim;

    @Schema(description = "Descrição do cronograma", example = "Cronograma de implementação do sistema")
    private String descricao;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @Schema(description = "Data de criação do registro", example = "01/08/2025 10:30:00")
    private LocalDateTime dataCriacao;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @Schema(description = "Data da última atualização", example = "01/08/2025 10:30:00")
    private LocalDateTime dataAtualizacao;

    @Schema(description = "Versão do registro", example = "1")
    private Long versao;

    @Schema(description = "Indica se o cronograma está ativo", example = "true")
    private Boolean ativo;
}
