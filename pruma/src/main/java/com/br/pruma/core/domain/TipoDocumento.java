package com.br.pruma.core.domain;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tipo_documento")
@Data
public class TipoDocumento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tipo_id")
    private Integer id;

    @Column(name = "descricao", length = 100, nullable = false)
    private String descricao;
}
