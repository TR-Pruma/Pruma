package com.br.pruma.modelos;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "pinterest_interacao_aux")
@Data
public class PinterestInteracaoAux {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interacao_id")
    private Integer id;

    @Column(name = "tipo_interacao", length = 15)
    private String tipoInteracao;
}
