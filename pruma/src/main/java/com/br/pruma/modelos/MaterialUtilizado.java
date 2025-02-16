package com.br.pruma.modelos;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "material_utilizado")
@Data
public class MaterialUtilizado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "material_id", referencedColumnName = "material_id")
    private Material material;

    @ManyToOne
    @JoinColumn(name = "atividade_id", referencedColumnName = "atividade_id")
    private Atividade atividade;

    @Column(name = "quantidade_utilizada")
    private Integer quantidadeUtilizada;
}
