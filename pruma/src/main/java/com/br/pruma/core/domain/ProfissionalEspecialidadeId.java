package com.br.pruma.core.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProfissionalEspecialidadeId implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "profissional_id")
    private Integer profissionalId;

    @Column(name = "especialidade_id")
    private Integer especialidadeId;
}
