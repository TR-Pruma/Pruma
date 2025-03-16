package com.br.pruma.modelos;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Entity
@Table(name = "inspecao")
@Data
public class Inspecao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inspecao_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "projeto_id", referencedColumnName = "projeto_id")
    private Integer projeto;

    @ManyToOne
    @JoinColumn(name = "tecnico_id", referencedColumnName = "profissional_cpf")
    private Long tecnico;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "data_inspecao")
    @Temporal(TemporalType.DATE)
    private Date dataInspecao;

    @Column(name = "resultado", length = 15)
    private String resultado;
}
