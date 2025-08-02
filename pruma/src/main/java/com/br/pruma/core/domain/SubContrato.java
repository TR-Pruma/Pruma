package com.br.pruma.core.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "subcontrato")
@Getter
@Setter
@NoArgsConstructor
public class SubContrato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subcontrato_id")
    private Integer id;

    // CORREÇÃO: Mapeamento para a entidade Cliente.
    // Assumindo que a entidade Cliente existe e tem um campo `cliente_cpf`.
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_cpf", referencedColumnName = "cliente_cpf", nullable = false)
    private Cliente cliente;

    // CORREÇÃO: Mapeamento para a entidade Projeto.
    // Assumindo que a entidade Projeto existe.
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projeto_id", referencedColumnName = "projeto_id", nullable = false)
    private Projeto projeto;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @NotNull(message = "O valor é obrigatório.")
    @Column(name = "valor", nullable = false)
    private Float valor;

    // CORREÇÃO: Uso de java.time.LocalDate
    @NotNull(message = "A data de início é obrigatória.")
    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    // CORREÇÃO: Uso de java.time.LocalDate
    @Column(name = "data_fim")
    private LocalDate dataFim;
}