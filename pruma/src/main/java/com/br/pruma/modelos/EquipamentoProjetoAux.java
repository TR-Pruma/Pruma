package com.br.pruma.modelos;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "equipamento_projeto_aux")
public class EquipamentoProjetoAux {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "equipamento_id", referencedColumnName = "equipamento_id")
    private Integer equipamento;

    @ManyToOne
    @JoinColumn(name = "projeto_id", referencedColumnName = "projeto_id")
    private Projeto projeto;

    @Column(name = "data_alocacao")
    private LocalDate dataAlocacao;
}