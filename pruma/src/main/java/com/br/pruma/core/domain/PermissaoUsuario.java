package com.br.pruma.core.domain;


import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@Table(name = "permissao_usuario")
public class PermissaoUsuario extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Relacionamento correto com a entidade Cliente
     * Assumindo que a PK da tabela cliente é cliente_cpf (String).
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cliente_cpf", referencedColumnName = "cliente_cpf")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Cliente cliente;

    /**
     * Relacionamento correto com a entidade TipoUsuario
     * Assumindo que a PK da tabela tipo_usuario é tipo_usuario (Integer).
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tipo_usuario", referencedColumnName = "tipo_usuario")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private TipoUsuario tipoUsuario;

    @Column(length = 15, nullable = false)
    private String permissao;
}