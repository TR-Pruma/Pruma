package com.br.pruma.core.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "empresa")
public class Empresa extends AuditableEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "empresa_cnpj", length = 14, nullable = false, unique = true)
    @EqualsAndHashCode.Include
    @ToString.Include
    @NotBlank(message = "CNPJ é obrigatório")
    private String cnpj;

    @Column(name = "razao_social", length = 50, nullable = false)
    @NotBlank(message = "Razão social é obrigatória")
    private String razaoSocial;

    @Column(name = "nome_fantasia", length = 50, nullable = false)
    @NotBlank(message = "Nome fantasia é obrigatório")
    private String nomeFantasia;

    @OneToMany(
            mappedBy = "empresa",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Builder.Default
    @ToString.Exclude
    private List<Endereco> enderecos = new ArrayList<>();

    // ---------- Métodos de domínio ----------

    public void addEndereco(Endereco endereco) {
        if (endereco == null) return;
        enderecos.add(endereco);
        endereco.setEmpresa(this);
    }

    public void removeEndereco(Endereco endereco) {
        if (endereco == null) return;
        enderecos.remove(endereco);
        endereco.setEmpresa(null);
    }

    public static Empresa ofCnpj(String cnpj) {
        if (cnpj == null || cnpj.isBlank()) return null;
        return Empresa.builder().cnpj(cnpj).build();
    }
}
