package com.br.pruma.modelos;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "fornecedor")
@Data
public class Fornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fornecedor_id")
    private Integer id;

    @Column(name = "nome", length = 255)
    private String nome;

    @Column(name = "cnpj", length = 255, unique = true)
    private String cnpj;

    @Column(name = "contato", length = 255)
    private String contato;
}
