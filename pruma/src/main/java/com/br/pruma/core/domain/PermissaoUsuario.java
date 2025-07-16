package com.br.pruma.core.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "permissao_usuario")
@Data
public class PermissaoUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_cpf", referencedColumnName = "cliente_cpf")
    private Long cliente;

    @ManyToOne
    @JoinColumn(name = "tipo_usuario", referencedColumnName = "tipo_usuario")
    private Integer tipoUsuario;

    @Column(name = "permissao", length = 15)
    private String permissao;
}
