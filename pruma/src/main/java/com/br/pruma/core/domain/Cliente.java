package com.br.pruma.core.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Entity
@Table(
        name = "cliente",
        indexes = {
                @Index(name = "idx_cliente_cpf",   columnList = "cliente_cpf"),
                @Index(name = "idx_cliente_email", columnList = "email")
        }
)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class Cliente extends AuditableEntity implements UserDetails, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cliente_id", updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Integer id;

    // Nome explícito necessário: outras entidades referenciam via
    // @JoinColumn(referencedColumnName = "cliente_cpf")
    @NotBlank(message = "CPF é obrigatório")
    @Column(name = "cliente_cpf", nullable = false, unique = true, length = 11)
    @ToString.Include
    private String cpf;

    @NotBlank(message = "Nome é obrigatório")
    @Column(nullable = false)
    private String nome;

    @NotBlank(message = "E-mail é obrigatório")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Telefone é obrigatório")
    @Column(nullable = false)
    private String telefone;

    @NotNull(message = "Endereço é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "endereco_id", nullable = false)
    @ToString.Exclude
    private Endereco endereco;

    @NotBlank(message = "Senha é obrigatória")
    @Column(nullable = false)
    private String senha;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_CLIENTE"));
    }

    @Override public String  getPassword()             { return senha;      }
    @Override public String  getUsername()             { return cpf;        }
    @Override public boolean isAccountNonExpired()     { return true;       }
    @Override public boolean isAccountNonLocked()      { return getAtivo(); }
    @Override public boolean isCredentialsNonExpired() { return true;       }
    @Override public boolean isEnabled()               { return getAtivo(); }
}
