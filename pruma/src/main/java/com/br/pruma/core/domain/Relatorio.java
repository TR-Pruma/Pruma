package com.br.pruma.core.domain;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "relatorio")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@ToString(onlyExplicitlyIncluded = true)
public class Relatorio extends AuditableEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "relatorio_id", updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Integer id;

    /**
     * Associação com Obra.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "obra_id", referencedColumnName = "obra_id", nullable = false)
    @ToString.Exclude
    private Obra obra;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    /**
     * Data de criação lógica do relatório.
     * Usa LocalDate para representar apenas a data.
     */
    @Column(name = "data_criacao", nullable = false)
    private LocalDate dataCriacao;

}
