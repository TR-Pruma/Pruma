package com.br.pruma.core.domain;

import com.br.pruma.config.AuditableEntity;
import com.br.pruma.core.enums.StatusEquipamento;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "equipamento")
@SQLDelete(sql = "UPDATE equipamento SET ativo = false WHERE equipamento_id = ?")
@Where(clause = "ativo = true")
public class Equipamento extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "equipamento_id")
    private Integer id;

    @Column(name = "nome", length = 255, nullable = false)
    @NotBlank
    private String nome;

    @Column(name = "descricao", length = 255, nullable = false)
    @NotBlank
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    @NotNull
    private StatusEquipamento status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Equipamento)) return false;
        return id != null && id.equals(((Equipamento) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}