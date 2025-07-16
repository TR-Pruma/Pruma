package com.br.pruma.core.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "status_solicitacao")
@Data
public class StatusSolicitacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "status_solicitacao_id")
    private Integer id;

    @Column(name = "descricao_solicitacao")
    private String descricaoSolicitacao;
}
