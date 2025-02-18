package com.br.pruma.modelos;

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

    @ManyToOne
    @JoinColumn(name = "obra_id", referencedColumnName = "obra_id")
    private Obra obra;

    @ManyToOne
    @JoinColumn(name = "material_id", referencedColumnName = "material_id")
    private Material material;

    @Column(name = "quantidade")
    private Integer quantidade;

    @Column(name = "data_requisicao")
    @Temporal(TemporalType.DATE)
    private Date dataRequisicao;
}