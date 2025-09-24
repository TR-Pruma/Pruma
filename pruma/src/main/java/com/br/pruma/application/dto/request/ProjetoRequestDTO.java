package com.br.pruma.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Schema(name = "ProjetoRequestDTO", description = "Dados para criação de um Projeto")
public class ProjetoRequestDTO {

    @NotBlank(message = "nome é obrigatório")
    @Size(max = 100)
    @Schema(description = "Nome do projeto", example = "Reforma Comercial Centro", required = true)
    private String nome;

    @Schema(description = "Descrição detalhada do projeto", example = "Reforma completa do térreo com troca de instalações")
    private String descricao;

    @Schema(description = "Data de criação do projeto (YYYY-MM-DD)", example = "2025-09-01")
    private LocalDate dataCriacao;
}
