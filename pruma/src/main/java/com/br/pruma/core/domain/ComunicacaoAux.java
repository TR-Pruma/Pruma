package com.br.pruma.core.domain;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "comunicacao_aux")
public class ComunicacaoAux {

    @Id
    @OneToOne
    @JoinColumn(name = "comunicacao_id", referencedColumnName = "comunicacao_id")
    private Integer comunicacao;

    @Column(name = "tipo_mensagem", length = 15)
    private String tipoMensagem;
}
