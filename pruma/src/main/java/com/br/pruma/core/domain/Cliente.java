package com.br.pruma.core.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "cliente")
public class Cliente {

    @Id
    @Column(name = "cliente_cpf")
    private Long cpf;

    @Column(name = "Nome", length = 30, nullable = false)
    private String nome;

    @Column(name = "Email", length = 30, nullable = false)
    private String email;

    @Column(name = "Telefone", nullable = false)
    private Long telefone;

    @ManyToOne
    @JoinColumn(name = "Id_endereco", referencedColumnName = "id_endereco")
    private Integer endereco;

    @Column(name = "Senha", length = 15)
    private String senha;
}

