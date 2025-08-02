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

    @ManyToOne
    @JoinColumn(name = "projeto_id", referencedColumnName = "projeto_id")
    private Integer projeto;

    @Column(name = "descricao")
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "status_solicitacao_id", referencedColumnName = "status_solicitacao_id")
    private Integer statusSolicitacao;

    @Column(name = "data_solicitacao")
    @Temporal(TemporalType.DATE)
    private Date dataSolicitacao;

    @Column(name = "data_resposta")
    @Temporal(TemporalType.DATE)
    private Date dataResposta;
}
