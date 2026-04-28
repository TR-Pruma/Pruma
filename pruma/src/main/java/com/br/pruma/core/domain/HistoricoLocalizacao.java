package com.br.pruma.core.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "historico_localizacao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id", callSuper = false)
@ToString(exclude = {"profissional", "projeto"})

public class HistoricoLocalizacao extends  AuditableEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "historico_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "profissional_cpf", referencedColumnName = "profissional_cpf", nullable = false)
    private ProfissionalDeBase profissional;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "projeto_id", referencedColumnName = "projeto_id", nullable = false)
    private Projeto projeto;

    @Column(name = "localizacao", length = 50, nullable = false)
    private String localizacao;

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

}
