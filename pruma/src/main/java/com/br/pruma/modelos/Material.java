package com.br.pruma.modelos;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Table(name = "material")
@Data
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "material_id")
    private Integer id;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "quantidade")
    private Integer quantidade;

    @Column(name = "custo_unitario", precision = 18, scale = 2)
    private BigDecimal custoUnitario;
}
