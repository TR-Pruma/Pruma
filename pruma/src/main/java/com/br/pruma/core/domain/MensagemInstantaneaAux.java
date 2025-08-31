package com.br.pruma.core.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

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
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class MensagemInstantaneaAux implements Serializable {

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

    @NotBlank(message = "tipoMensagem é obrigatório")
    @Size(max = 15, message = "tipoMensagem deve ter no máximo 15 caracteres")
    @Column(name = "tipo_mensagem", length = 15, nullable = false)
    @ToString.Include
    private String tipoMensagem;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
