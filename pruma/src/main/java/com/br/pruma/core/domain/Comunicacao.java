package com.br.pruma.core.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "comunicacao")
public class Comunicacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comunicacao_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "projeto_id", referencedColumnName = "projeto_id")
    private Integer projeto;

    @ManyToOne
    @JoinColumn(name = "id_cliente", referencedColumnName = "cliente_cpf")
    private Long cliente;

    @Column(name = "tipo_remetente", length = 15)
    private String tipoRemetente;

    @Column(name = "mensagem", columnDefinition = "TEXT")
    private String mensagem;

    @Column(name = "data_hora")
    private LocalDateTime dataHora;
}
