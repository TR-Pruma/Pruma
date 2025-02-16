package com.br.pruma.modelos;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "assinatura_digital")
public class AssinaturaDigital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assinatura_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "cliente_cpf", referencedColumnName = "cliente_cpf")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "tipo_usuario", referencedColumnName = "tipo_usuario")
    private TipoUsuario tipoUsuario;

    @ManyToOne
    @JoinColumn(name = "documento_id", referencedColumnName = "documento_id")
    private Documento documento;

    @Column(name = "data_hora")
    private LocalDateTime dataHora;
}
