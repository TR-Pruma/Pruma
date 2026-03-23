package com.br.pruma.core.domain;

import com.br.pruma.core.enums.TipoMensagem;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * Entidade auxiliar com metadados de tipo para {@link MensagemInstantanea}.
 * Herda auditoria de {@link AuditableEntity}.
 */
@Entity
@Table(
        name = "mensagem_instantanea_aux",
        uniqueConstraints = @UniqueConstraint(
                name        = "uk_msginst_aux_mensagem",
                columnNames = "mensagem_id"
        ),
        indexes = @Index(
                name       = "idx_msginst_aux_tipo",
                columnList = "tipo_mensagem"
        )
)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class MensagemInstantaneaAux extends AuditableEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "mensagem_id", updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Integer mensagemId;

    @NotNull(message = "mensagem é obrigatória")
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId
    @JoinColumn(name = "mensagem_id", referencedColumnName = "mensagem_id", nullable = false)
    private MensagemInstantanea mensagem;

    @NotNull(message = "Tipo de mensagem é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_mensagem", length = 30, nullable = false)
    @ToString.Include
    private TipoMensagem tipoMensagem;
}
