package com.br.pruma.modelos;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "anexo")
public class Anexo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "anexo_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "projeto_id", referencedColumnName = "projeto_id")
    private Projeto projeto;

    @Column(name = "tipo_anexo", length = 15)
    private String tipoAnexo;

    @Column(name = "caminho_arquivo", columnDefinition = "TEXT")
    private String caminhoArquivo;
}