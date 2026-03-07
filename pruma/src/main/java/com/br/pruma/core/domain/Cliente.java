package com.br.pruma.core.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "cliente")
@AttributeOverrides({
        @AttributeOverride(name = "createdAt",  column = @Column(name = "data_criacao",    nullable = false, updatable = false)),
        @AttributeOverride(name = "updatedAt",  column = @Column(name = "data_atualizacao")),
        @AttributeOverride(name = "version",    column = @Column(name = "versao"))
})
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@ToString(onlyExplicitlyIncluded = true)
public class Cliente extends AuditableEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cliente_id", updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Integer id;

    @Column(nullable = false, unique = true, length = 11)
    private String cpf;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String telefone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "endereco_id", nullable = false)
    @ToString.Exclude
    private Endereco endereco;

    @Column(nullable = false)
    private String senha;

    // ── UserDetails ──────────────────────────────────────────
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_CLIENTE"));
    }

    @Override public String  getPassword()              { return senha;       }
    @Override public String  getUsername()              { return cpf;         }
    @Override public boolean isAccountNonExpired()      { return true;        }
    @Override public boolean isAccountNonLocked()       { return getAtivo();  }
    @Override public boolean isCredentialsNonExpired()  { return true;        }
    @Override public boolean isEnabled()                { return getAtivo();  }
}
