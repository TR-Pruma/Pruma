package com.br.pruma.modelos;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "projeto_profissional")
@Data
public class ProjetoProfissional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "projeto_id", referencedColumnName = "projeto_id")
    private Integer projeto;

    @ManyToOne
    @JoinColumn(name = "profissional_id", referencedColumnName = "profissional_id")
    private Integer profissional;
}
