package com.br.pruma.core.domain;

import com.br.pruma.core.enums.TipoUsuarioEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Entidade de autenticacao do sistema.
 * Herda auditoria de {@link AuditableEntity} (createdAt, updatedAt, ativo, version).
 * O campo {@code ativo} da superclasse e reutilizado para isEnabled().
 *
 * {@code tipo} usa {@link TipoUsuarioEnum} (enum simples) pois e armazenado como
 * coluna STRING, sem FK para a tabela tipo_usuario.
 */
@Entity
@Table(
        name = "usuario",
        indexes = {
                @Index(name = "idx_usuario_cpf", columnList = "cpf", unique = true)
        }
)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class Usuario extends AuditableEntity implements UserDetails, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id", updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Integer id;

    @Column(name = "cpf", unique = true, nullable = false, length = 11)
    private String cpf;

    @Column(name = "senha", nullable = false)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false, length = 20)
    private TipoUsuarioEnum tipo;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + tipo.name()));
    }

    @Override public String  getPassword()             { return senha;      }
    @Override public String  getUsername()             { return cpf;        }
    @Override public boolean isAccountNonExpired()     { return true;       }
    @Override public boolean isAccountNonLocked()      { return getAtivo(); }
    @Override public boolean isCredentialsNonExpired() { return true;       }
    @Override public boolean isEnabled()               { return getAtivo(); }

    /**
     * Factory method para criar uma referencia lazy por ID.
     * Utilizado em mappers (ex: AuditoriaMapper) para evitar queries desnecessarias.
     */
    public static Usuario ofId(Integer id) {
        if (id == null) return null;
        return Usuario.builder().id(id).build();
    }
}
