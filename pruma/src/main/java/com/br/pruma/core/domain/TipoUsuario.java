package com.br.pruma.core.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Entidade que representa os tipos de usuário do sistema.
 * Persiste na tabela tipo_usuario, referenciada por ClienteTipo, Auditoria,
 * PermissaoUsuario, Notificacao, Lembrete, etc.
 */
@Entity
@Table(name = "tipo_usuario")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class TipoUsuario implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tipo_usuario", updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Integer id;

    @NotBlank(message = "Descri\u00e7\u00e3o é obrigat\u00f3ria")
    @Size(max = 255)
    @Column(name = "descricao", nullable = false, length = 255)
    @ToString.Include
    private String descricao;

    @Column(name = "ativo", nullable = false)
    @Builder.Default
    private Boolean ativo = true;

    @CreationTimestamp
    @Column(name = "data_criacao", updatable = false, nullable = false)
    private LocalDateTime dataCriacao;

    @UpdateTimestamp
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @Version
    @Column(name = "versao")
    private Long versao;
}
