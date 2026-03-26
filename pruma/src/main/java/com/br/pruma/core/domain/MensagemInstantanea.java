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
        name    = "mensagem_instantanea",
        indexes = {
                @Index(name = "idx_msginst_cliente", columnList = "cliente_cpf"),
                @Index(name = "idx_msginst_tipo_usuario", columnList = "tipo_usuario")
        }
)
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class MensagemInstantanea implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mensagem_id", updatable = false, nullable = false)
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

    @NotBlank(message = "destinatarioId é obrigatório")
    @Size(max = 255, message = "destinatarioId deve ter no máximo 255 caracteres")
    @Column(name = "destinatario_id", columnDefinition = "TEXT", nullable = false)
    @ToString.Include
    private String destinatarioId;

    @NotBlank(message = "tipoDestinatario é obrigatório")
    @Size(max = 15, message = "tipoDestinatario deve ter no máximo 15 caracteres")
    @Column(name = "tipo_destinatario", length = 15, nullable = false)
    private String tipoDestinatario;

    @NotBlank(message = "conteudo é obrigatório")
    @Size(max = 4000, message = "conteudo deve ter no máximo 4000 caracteres")
    @Column(name = "conteudo", columnDefinition = "TEXT", nullable = false)
    private String conteudo;

    @NotNull(message = "dataHora é obrigatória")
    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

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
