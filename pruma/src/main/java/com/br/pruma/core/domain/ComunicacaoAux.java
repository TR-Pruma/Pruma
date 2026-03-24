package com.br.pruma.core.domain;

import com.br.pruma.core.enums.TipoComunicacao;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;

import java.io.Serial;
import java.io.Serializable;

/**
 * Entidade auxiliar que estende {@link Comunicacao} com metadados de tipo.
 * Herda auditoria (createdAt, updatedAt, ativo, version) de {@link AuditableEntity}.
 */
@Entity
@Table(name = "comunicacao_aux")
@SQLDelete(sql = "UPDATE comunicacao_aux SET ativo = false WHERE comunicacao_id = ?")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class ComunicacaoAux extends AuditableEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "comunicacao_id", referencedColumnName = "comunicacao_id", nullable = false)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Comunicacao comunicacao;

    @NotNull(message = "Tipo de mensagem é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_mensagem", length = 30, nullable = false)
    @ToString.Include
    private TipoComunicacao tipoMensagem;
}
