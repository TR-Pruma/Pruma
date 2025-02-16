package com.br.pruma.modelos;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "logradouro")
@Data
public class Logradouro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_logradouro")
    private Integer id;

    @Column(name = "tipo", length = 255)
    private String tipo;
}
