package com.br.pruma.modelos;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "log_alteracao_aux")
@Data
public class LogAlteracaoAux {

    @Id
    @Column(name = "log_id")
    private Integer logId;

    @Column(name = "tipo_alteracao", length = 15)
    private String tipoAlteracao;
}
