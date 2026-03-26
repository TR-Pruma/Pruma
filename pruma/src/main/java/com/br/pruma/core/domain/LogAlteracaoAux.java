package com.br.pruma.core.domain;

import com.br.pruma.core.enums.TipoAlteracao;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * Entidade auxiliar com metadados de tipo para {@link LogAlteracao}.
 * Herda auditoria de {@link AuditableEntity}.
 */
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
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class LogAlteracaoAux extends AuditableEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "log_id", updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Integer logId;

    @NotNull(message = "Log de alteração é obrigatório")
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId
    @JoinColumn(name = "log_id", nullable = false)
    private LogAlteracao log;

    @NotNull(message = "Tipo de alteração é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_alteracao", length = 30, nullable = false)
    @ToString.Include
    private TipoAlteracao tipoAlteracao;
}
