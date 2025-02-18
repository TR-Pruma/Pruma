package com.br.pruma.modelos;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Entity
@Table(name = "pinterest_interacao")
@Data
public class PinterestInteracao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interacao_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "cliente_cpf", referencedColumnName = "cliente_cpf")
    private Cliente cliente;

    @Column(name = "tipo_interacao", length = 50)
    private String tipoInteracao;

    @Column(name = "data_hora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHora;
}
