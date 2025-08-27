package com.br.pruma.core.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(
        name    = "lembrete",
        indexes = @Index(
                name       = "idx_lembrete_cliente_data_hora",
                columnList = "cliente_cpf,data_hora"
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
public class Lembrete implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lembrete_id", updatable = false)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Integer id;

    @NotNull(message = "Cliente é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "cliente_cpf",
            referencedColumnName = "cliente_cpf",
            nullable = false
    )
    @ToString.Include(name = "clienteCpf")
    private Cliente cliente;

    @NotNull(message = "Tipo de usuário é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "tipo_usuario",
            referencedColumnName = "tipo_usuario",
            nullable = false
    )
    @ToString.Include(name = "tipoUsuario")
    private TipoUsuario tipoUsuario;

    @NotBlank(message = "Mensagem é obrigatória")
    @Size(max = 2000, message = "Mensagem deve ter no máximo 2000 caracteres")
    @Column(name = "mensagem", columnDefinition = "TEXT", nullable = false)
    private String mensagem;

    @NotNull(message = "Data e hora são obrigatórias")
    @FutureOrPresent(message = "Data e hora não podem ser no passado")
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
