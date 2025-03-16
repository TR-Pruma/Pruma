package com.br.pruma.modelos;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "endereco")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_endereco")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_lougradouro", referencedColumnName = "id_lougradouro")
    private Integer logradouro;

    @Column(name = "rua", length = 50)
    private String rua;

    @Column(name = "numero")
    private Integer numero;

    @Column(name = "complemento", length = 15)
    private String complemento;

    @Column(name = "bairro", length = 30)
    private String bairro;

    @Column(name = "cidade", length = 30)
    private String cidade;

    @Column(name = "estado", length = 50)
    private String estado;

    @Column(name = "pais", length = 15)
    private String pais;
}
