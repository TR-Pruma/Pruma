package com.br.pruma.modelos;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "cliente_tipo")
public class ClienteTipo {

    @Id
    @Column(name = "id_cliente_tipo")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "tipo_usuario", referencedColumnName = "tipo_usuario")
    private TipoUsuario tipoUsuario;

    @Column(name = "descricao_cliente", length = 255)
    private String descricaoCliente;
}
