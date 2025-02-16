package com.br.pruma.modelos;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Entity
@Table(name = "pre_obra")
@Data
public class PreObra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pre_obra_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "obra_id", referencedColumnName = "obra_id")
    private Obra obra;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "data_inicio")
    @Temporal(TemporalType.DATE)
    private Date dataInicio;
}
