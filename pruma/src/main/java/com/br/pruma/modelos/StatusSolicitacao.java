package com.br.pruma.modelos;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
public class StatusSolicitacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idStatusSolicitacao;

    private String descricaoSolicitacao;

}
