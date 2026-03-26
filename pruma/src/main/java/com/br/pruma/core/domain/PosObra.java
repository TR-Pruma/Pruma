package com.br.pruma.core.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

@Entity
@Table(name = "pos_obra")
@Data
public class PosObra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pos_obra_id")
    private Long id; // A chave primária deveria ser Long, como padrão JPA para ID auto-gerado.

    @ManyToOne(fetch = FetchType.LAZY) // Usamos FetchType.LAZY para performance
    @JoinColumn(name = "obra_id", referencedColumnName = "obra_id")
    @ToString.Exclude // Exclui do toString para evitar StackOverflowError
    @EqualsAndHashCode.Exclude // Exclui do equals/hashCode para evitar problemas
    private Obra obra; // O tipo do campo deve ser a entidade completa (Obra), e não o ID (Integer).

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "data_conclusao")
    @Temporal(TemporalType.DATE)
    private Date dataConclusao;
}