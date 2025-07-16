package com.br.pruma.core.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;


@Data
@Entity
@Table(name = "documento")
public class Documento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "documento_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "projeto_id", referencedColumnName = "projeto_id")
    private Integer projeto;

    @ManyToOne
    @JoinColumn(name = "id_tipo_documento", referencedColumnName = "id_tipo_documento")
    private Integer tipoDocumento;

    @Column(name = "caminho_arquivo", length = 50)
    private String caminhoArquivo;

    @Column(name = "data_upload")
    private LocalDate dataUpload;
}
