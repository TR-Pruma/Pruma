package com.br.pruma.modelos;


import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Entity
@Table(name = "log_alteracao")
@Data
public class LogAlteracao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "projeto_id", referencedColumnName = "projeto_id")
    private Projeto projeto;

    @ManyToOne
    @JoinColumn(name = "cliente_cpf", referencedColumnName = "cliente_cpf")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "tipo_usuario", referencedColumnName = "tipo_usuario")
    private TipoUsuario tipoUsuario;

    @Column(name = "descricao", length = 255)
    private String descricao;

    @Column(name = "data_hora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHora;
}
