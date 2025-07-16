package com.br.pruma.core.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "usuario_tipo")
@Data
public class UsuarioTipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_tipo_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "usuario_id")
    private Integer usuario;

    @ManyToOne
    @JoinColumn(name = "tipo_usuario_id", referencedColumnName = "tipo_usuario_id")
    private TipoUsuario tipoUsuario;
}
