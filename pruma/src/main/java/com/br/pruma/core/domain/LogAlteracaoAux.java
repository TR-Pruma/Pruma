package com.br.pruma.core.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(
        name = "log_alteracao_aux",
        uniqueConstraints = @UniqueConstraint(
                name        = "uk_log_alteracao_aux_tipo",
                columnNames = "tipo_alteracao"
        )
)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class LogAlteracaoAux implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * PK e FK para o LogAlteracao pai.
     * @MapsId sincroniza este campo com o PK herdado de LogAlteracao.
     */
    @Id
    @Column(name = "log_id", updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Integer logId;

    /**
     * Associação back-reference ao LogAlteracao.
     */
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId
    @JoinColumn(name = "log_id", nullable = false)
    private LogAlteracao log;

    @NotBlank(message = "tipoAlteracao é obrigatório")
    @Size(max = 15, message = "tipoAlteracao deve ter no máximo 15 caracteres")
    @Column(name = "tipo_alteracao", length = 15, nullable = false)
    @ToString.Include
    private String tipoAlteracao;
}


