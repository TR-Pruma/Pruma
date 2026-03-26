package com.br.pruma.core.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "imagem_projeto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = "projeto")
public class ImagemProjeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "imagem_id")
    @EqualsAndHashCode.Include
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "projeto_id", referencedColumnName = "projeto_id", nullable = false)
    private Projeto projeto;

    @NotBlank
    @Column(name = "caminho_arquivo", length = 255, nullable = false)
    private String caminhoArquivo;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @NotNull
    @Column(name = "data_upload", nullable = false)
    private LocalDate dataUpload;
}