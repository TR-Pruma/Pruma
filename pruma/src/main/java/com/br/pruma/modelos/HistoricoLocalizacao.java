package com.br.pruma.modelos;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Entity
@Table(name = "historico_localizacao")
@Data
public class HistoricoLocalizacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "historico_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "profissional_cpf", referencedColumnName = "profissional_cpf")
    private ProfissionalDeBase profissional;

    @ManyToOne
    @JoinColumn(name = "projeto_id", referencedColumnName = "projeto_id")
    private Projeto projeto;

    @Column(name = "localizacao", length = 50)
    private String localizacao;

    @Column(name = "data_hora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHora;
}
