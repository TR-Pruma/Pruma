package com.br.pruma.modelos;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class ClienteTipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idClienteTipo;

    @ManyToOne
    @JoinColumn(name = "tipoUsuario", referencedColumnName = "id")
    private UsuarioTipo tipoUsuario;

    private String descricaoCliente;
}
