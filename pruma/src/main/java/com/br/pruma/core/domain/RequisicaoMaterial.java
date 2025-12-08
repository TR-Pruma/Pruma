package com.br.pruma.core.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
@Entity
@Table(name = "requisicao_material")
@Data
public class RequisicaoMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "requisicao_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "obra_id", referencedColumnName = "obra_id", nullable = false)
    private Obra obra;   // ✅ entidade

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "material_id", referencedColumnName = "material_id", nullable = false)
    private Material material;   // ✅ entidade

    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_requisicao", nullable = false)
    private Date dataRequisicao;
}
