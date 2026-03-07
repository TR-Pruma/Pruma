package com.br.pruma.core.domain;

import com.br.pruma.config.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cliente")
@AttributeOverrides({
        @AttributeOverride(name = "createdAt", column = @Column(name = "data_criacao", nullable = false, updatable = false)),
        @AttributeOverride(name = "updatedAt", column = @Column(name = "data_atualizacao")),
        @AttributeOverride(name = "version", column = @Column(name = "versao"))
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cliente extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
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
    private Endereco endereco;

    @Column(nullable = false)
    private String senha;
}

