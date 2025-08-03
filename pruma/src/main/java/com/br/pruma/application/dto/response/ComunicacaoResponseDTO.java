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
@Schema(description = "Dados de retorno de uma comunicação")
public class ComunicacaoResponseDTO {

    @Schema(description = "ID da comunicação", example = "1")
    private Integer id;

    @Schema(description = "ID do projeto", example = "1")
    private Integer projetoId;

    @Schema(description = "Nome do projeto", example = "Projeto ABC")
    private String projetoNome;

    @Schema(description = "ID do cliente", example = "1")
    private Integer clienteId;

    @Schema(description = "Nome do cliente", example = "João da Silva")
    private String clienteNome;

    @Schema(description = "Tipo do remetente", example = "CLIENTE")
    private String tipoRemetente;

    @Schema(description = "Conteúdo da mensagem", example = "Precisamos agendar uma reunião")
    private String mensagem;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @Schema(description = "Data e hora da comunicação", example = "01/08/2025 14:30:00")
    private LocalDateTime dataHora;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @Schema(description = "Data e hora da última atualização", example = "01/08/2025 14:30:00")
    private LocalDateTime dataAtualizacao;

    @Schema(description = "Versão do registro para controle de concorrência", example = "1")
    private Long versao;

    @Schema(description = "Indica se a comunicação está ativa", example = "true")
    private Boolean ativo;
}
