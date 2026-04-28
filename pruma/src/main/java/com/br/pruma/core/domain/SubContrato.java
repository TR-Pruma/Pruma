package com.br.pruma.core.domain;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "subcontrato")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@ToString(onlyExplicitlyIncluded = true)
public class SubContrato extends AuditableEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subcontrato_id", updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Integer id;

    /**
     * Associação com Cliente.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cliente_cpf", referencedColumnName = "cliente_cpf", nullable = false)
    @ToString.Exclude
    private Cliente cliente;

    /**
     * Associação com Projeto.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "projeto_id", referencedColumnName = "projeto_id", nullable = false)
    @ToString.Exclude
    private Projeto projeto;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    /**
     * Valor do subcontrato.
     */
    @Column(name = "valor", nullable = false)
    private Float valor;

    /**
     * Data de início do subcontrato.
     */
    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    /**
     * Data de término do subcontrato.
     */
    @Column(name = "data_fim")
    private LocalDate dataFim;

}
