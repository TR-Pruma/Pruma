package com.br.pruma.core.domain;

import com.br.pruma.core.enums.StatusEquipamento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "equipamento")
public class Equipamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "equipamento_id")
    private Integer id;

    @Column(name = "nome", length = 255, nullable = false)
    private String nome;

    @Column(name = "descricao", length = 255, nullable = false)
    private String descricao;

    @ManyToOne
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StatusEquipamento status;

    @CreationTimestamp
    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @UpdateTimestamp
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @Column(nullable = false)
    private Boolean ativo = true;
}