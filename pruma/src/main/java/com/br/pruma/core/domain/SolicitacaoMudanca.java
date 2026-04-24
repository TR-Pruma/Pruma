package com.br.pruma.core.domain;



import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "solicitacao_mudanca")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class SolicitacaoMudanca  extends AuditableEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "solicitacao_id", updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Integer id;

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
     * Associação com Status da Solicitação.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "status_solicitacao_id", referencedColumnName = "status_solicitacao_id", nullable = false)
    @ToString.Exclude
    private StatusSolicitacao statusSolicitacao;

    /**
     * Data lógica da solicitação.
     * Usa LocalDate para representar apenas a data.
     */
    @Column(name = "data_solicitacao", nullable = false)
    private LocalDate dataSolicitacao;

    /**
     * Data de resposta da solicitação.
     * Usa LocalDate para representar apenas a data.
     */
    @Column(name = "data_resposta")
    private LocalDate dataResposta;
}
