package com.br.pruma.modelos;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "mensagem_instantanea_aux")
@Data
public class MensagemInstantaneaAux {

    @Id
    @Column(name = "mensagem_id")
    private Integer mensagemId;

    @Column(name = "tipo_mensagem", length = 15)
    private String tipoMensagem;
}
