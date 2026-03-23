package com.br.pruma.core.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Entidade de autenticação do sistema.
 * Herda auditoria de {@link AuditableEntity} (createdAt, updatedAt, ativo, version).
 * O campo {@code ativo} da superclasse é reutilizado para isEnabled().
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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
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
    private TipoUsuario tipo;

    // ── UserDetails ──────────────────────────────────────────────

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + tipo.name()));
    }

    @Override public String getPassword()              { return senha;       }
    @Override public String getUsername()              { return cpf;         }
    @Override public boolean isAccountNonExpired()     { return true;        }
    @Override public boolean isAccountNonLocked()      { return getAtivo();  }
    @Override public boolean isCredentialsNonExpired() { return true;        }
    @Override public boolean isEnabled()               { return getAtivo();  }
}
