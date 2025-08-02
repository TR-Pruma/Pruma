package com.br.pruma.core.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "notificacao")
@Data
public class Notificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notificacao_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "cliente_cpf", referencedColumnName = "cliente_cpf")
    private Long cliente;

    @ManyToOne
    @JoinColumn(name = "tipo_usuario", referencedColumnName = "tipo_usuario")
    private Integer tipoUsuario;

    @Column(name = "mensagem", length = 255)
    private String mensagem;

    @Column(name = "data_hora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHora;

    @Column(name = "lida")
    private Boolean lida;
}