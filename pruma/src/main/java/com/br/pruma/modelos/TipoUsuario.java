package com.br.pruma.modelos;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tipo_usuario")
@Data
public class TipoUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tipo_usuario_id")
    private Integer id;

    @Column(name = "descricao", length = 100, nullable = false)
    private String descricao;
}
