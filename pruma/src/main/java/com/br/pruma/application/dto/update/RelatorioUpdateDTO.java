package com.br.pruma.application.dto.update;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RelatorioUpdateDTO {
    private Integer projetoId;
    private String titulo;
    private String conteudo;
    private LocalDate dataEmissao;
}
