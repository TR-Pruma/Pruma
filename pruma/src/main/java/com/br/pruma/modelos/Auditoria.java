package com.br.pruma.modelos;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "auditoria")
public class Auditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auditoria_id")
    private Integer id;

    @Column(name = "cliente_cpf", columnDefinition = "TEXT")
    private String clienteCpf;

    @ManyToOne
    @JoinColumn(name = "tipo_usuario", referencedColumnName = "tipo_usuario")
    private TipoUsuario tipoUsuario;

    @Column(name = "acao", columnDefinition = "TEXT")
    private String acao;

    @Column(name = "data_hora")
    private LocalDateTime dataHora;
}
