package com.br.pruma.core.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "profissional_especialidade")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class ProfissionalEspecialidade implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private ProfissionalEspecialidadeId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("profissionalId")
    @JoinColumn(name = "profissional_id")
    private ProfissionalDeBase profissional;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("especialidadeId")
    @JoinColumn(name = "especialidade_id")
    private Especialidade especialidade;
}
