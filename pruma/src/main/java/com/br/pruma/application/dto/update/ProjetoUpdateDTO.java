package com.br.pruma.application.dto.update;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Schema(name = "ProjetoUpdateDTO", description = "Dados para atualização parcial de um Projeto")
public class ProjetoUpdateDTO {

    @Size(max = 100)
    @Schema(description = "Nome do projeto", example = "Reforma Comercial Centro")
    private String nome;

    @Schema(description = "Descrição detalhada do projeto", example = "Ajuste no escopo de instalações")
    private String descricao;

    @Schema(description = "Data de criação do projeto (YYYY-MM-DD)", example = "2025-09-01")
    private LocalDate dataCriacao;
}

