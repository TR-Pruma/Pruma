package com.br.pruma.core.domain;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
@Entity
@Table(name = "solicitacao_mudanca")
@Data
public class SolicitacaoMudanca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "solicitacao_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "projeto_id", referencedColumnName = "projeto_id", nullable = false)
    private Projeto projeto;   // ✅ entidade

    @Column(name = "descricao")
    private String descricao;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "status_solicitacao_id", referencedColumnName = "status_solicitacao_id", nullable = false)
    private StatusSolicitacao statusSolicitacao;   // ✅ entidade

    @Temporal(TemporalType.DATE)
    @Column(name = "data_solicitacao")
    private Date dataSolicitacao;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_resposta")
    private Date dataResposta;
}