package com.br.pruma.core.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "requisicao_material")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class RequisicaoMaterial implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "requisicao_id", updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Integer id;

    /**
     * Associação com Obra.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "obra_id", referencedColumnName = "obra_id", nullable = false)
    @ToString.Exclude
    private Obra obra;

    /**
     * Associação com Material.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "material_id", referencedColumnName = "material_id", nullable = false)
    @ToString.Exclude
    private Material material;

    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;

    /**
     * Data lógica da requisição.
     * Usa LocalDate para representar apenas a data.
     */
    @Column(name = "data_requisicao", nullable = false)
    private LocalDate dataRequisicao;

    /**
     * Timestamp automático de criação no banco.
     */
    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDate createdAt;
}
