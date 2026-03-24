package com.br.pruma.core.domain;

import com.br.pruma.core.domain.AuditableEntity;
import com.br.pruma.core.enums.StatusEquipamento;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@ToString(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "equipamento")
@SQLDelete(sql = "UPDATE equipamento SET ativo = false WHERE equipamento_id = ?")
@SQLRestriction("ativo = true")
public class Equipamento extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "equipamento_id", updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Integer id;

    @NotBlank
    @Column(name = "nome", length = 255, nullable = false)
    private String nome;

    @NotBlank
    @Column(name = "descricao", length = 255, nullable = false)
    private String descricao;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StatusEquipamento status;
}
