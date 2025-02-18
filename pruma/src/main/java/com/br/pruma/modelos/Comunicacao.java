package com.br.pruma.modelos;

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
    private Projeto projeto;

    @ManyToOne
    @JoinColumn(name = "id_cliente", referencedColumnName = "cliente_cpf")
    private Cliente cliente;

    @Column(name = "tipo_remetente", length = 15)
    private String tipoRemetente;

    @Column(name = "mensagem", columnDefinition = "TEXT")
    private String mensagem;

    @Column(name = "data_hora")
    private LocalDateTime dataHora;
}
