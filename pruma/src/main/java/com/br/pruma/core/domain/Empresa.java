package com.br.pruma.core.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;


@Getter
@Setter
@Builder
@Entity
@Table(name = "empresa")
public class Empresa {

    @Id
    @Column(name = "empresa_cnpj", length = 14, nullable = false, unique = true)
    private String cnpj;

    @Column(name = "razao_social", length = 50, nullable = false)
    private String razaoSocial;

    @Column(name = "nome_fantasia", length = 50, nullable = false)
    private String nomeFantasia;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Endereco> enderecos;

    @Version
    private Long version;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Empresa)) return false;
        Empresa empresa = (Empresa) o;
        return Objects.equals(cnpj, empresa.cnpj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cnpj);
    }
}
