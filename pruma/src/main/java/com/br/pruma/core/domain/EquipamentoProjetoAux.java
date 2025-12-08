package com.br.pruma.core.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "equipamento_projeto_aux")
public class EquipamentoProjetoAux {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "equipamento_id", referencedColumnName = "equipamento_id", nullable = false)
    private Equipamento equipamento;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "projeto_id", referencedColumnName = "projeto_id", nullable = false)
    private Projeto projeto;

    @Column(name = "data_alocacao")
    private LocalDate dataAlocacao;
}
