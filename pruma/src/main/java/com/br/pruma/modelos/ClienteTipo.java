package com.br.pruma.modelos;

import jakarta.persistence.*;



@Entity
public class ClienteTipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idClienteTipo;

    @ManyToOne
    @JoinColumn(name = "tipoUsuario", referencedColumnName = "tipoUsuario")
    private UsuarioTipo tipoUsuario;

    private String descricaoCliente;
}
