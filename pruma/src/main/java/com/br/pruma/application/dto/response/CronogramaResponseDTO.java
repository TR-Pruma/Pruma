package com.br.pruma.application.dto.response;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CronogramaResponseDTO {
    private Integer id;
    private Integer projetoId;
    private String projetoNome; // opcional, útil para listagens
    private LocalDate dataInicio;
    private LocalDate dataFim;
}