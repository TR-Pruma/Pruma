package com.br.pruma.core.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(
        name    = "log_alteracao",
        indexes = @Index(
                name       = "idx_logalteracao_projeto_datahora",
                columnList = "projeto_id,data_hora"
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
public class LogAlteracao implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id", updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Integer id;

    @NotNull(message = "Projeto é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "projeto_id", nullable = false)
    @ToString.Include(name = "projetoId")
    private Projeto projeto;

    @NotNull(message = "Cliente é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cliente_cpf", referencedColumnName = "cliente_cpf", nullable = false)
    @ToString.Include(name = "clienteCpf")
    private Cliente cliente;

    @NotNull(message = "Tipo de usuário é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tipo_usuario", referencedColumnName = "tipo_usuario", nullable = false)
    @ToString.Include(name = "tipoUsuarioId")
    private TipoUsuario tipoUsuario;

    @NotBlank(message = "Descrição do log é obrigatória")
    @Size(max = 255, message = "Descrição deve ter no máximo 255 caracteres")
    @Column(name = "descricao", length = 255, nullable = false)
    private String descricao;

    @CreationTimestamp
    @Column(name = "data_hora", nullable = false, updatable = false)
    private LocalDateTime dataHora;

    @OneToOne(
            mappedBy      = "log",
            fetch         = FetchType.LAZY,
            cascade       = CascadeType.ALL,
            orphanRemoval = true,
            optional      = false
    )
    private LogAlteracaoAux aux;

}
