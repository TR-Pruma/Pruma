package com.br.pruma.modelos;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "cronograma")
public class Cronograma {

    @Id
    @Column(name = "cronograma_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "projeto_id", referencedColumnName = "projeto_id")
    private Projeto projeto;

    @Column(name = "data_inicio")
    private LocalDate dataInicio;

    @Column(name = "data_fim")
    private LocalDate dataFim;
}
