package com.br.pruma.core.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "equipamento_projeto")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"equipamento", "projeto"})
@SQLDelete(sql = "UPDATE equipamento_projeto SET ativo = false WHERE id = ?")
@Where(clause = "ativo = true")
public class EquipamentoProjeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "equipamento_id", referencedColumnName = "equipamento_id", nullable = false)
    private Equipamento equipamento;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "projeto_id", referencedColumnName = "projeto_id", nullable = false)
    private Projeto projeto;

    @NotNull
    @Column(name = "data_alocacao", nullable = false)
    private LocalDate dataAlocacao;

    @CreationTimestamp
    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @UpdateTimestamp
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @Version
    @Column(name = "versao")
    private Long versao;

    @Column(nullable = false)
    private Boolean ativo = true;
}