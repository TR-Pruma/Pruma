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
import java.util.Date;

@Entity
@Table(
        name    = "notificacao",
        indexes = {
                @Index(name = "idx_notificacao_cliente",      columnList = "cliente_cpf"),
                @Index(name = "idx_notificacao_tipo_usuario", columnList = "tipo_usuario")
        }
)
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@ToString(onlyExplicitlyIncluded = true)
public class Notificacao extends AuditableEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notificacao_id", updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Integer id;

    @NotNull(message = "Cliente é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name                 = "cliente_cpf",
            referencedColumnName = "cliente_cpf",
            nullable             = false
    )
    private Cliente cliente;

    @NotNull(message = "Tipo de usuário é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name                 = "tipo_usuario",
            referencedColumnName = "tipo_usuario",
            nullable             = false
    )
    private TipoUsuario tipoUsuario;

    @NotBlank(message = "Mensagem é obrigatória")
    @Size(max = 255, message = "Mensagem deve ter no máximo 255 caracteres")
    @Column(name = "mensagem", length = 255, nullable = false)
    @ToString.Include
    private String mensagem;

    @NotNull(message = "Data e hora é obrigatória")
    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

    @Column(name = "lida", nullable = false)
    private boolean lida = false;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

}