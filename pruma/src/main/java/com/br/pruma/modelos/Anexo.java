package com.br.pruma.modelos;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.persistence.*;
import lombok.Data;

@ApiModel(description = "Representa um anexo associado a um projeto")
@Data
@Entity
@Table(name = "anexo")
public class Anexo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "anexo_id")
    @ApiModelProperty(value = "Identificador único do anexo", example = "1")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "projeto_id", referencedColumnName = "projeto_id")
    @ApiModelProperty(value = "Identificador do projeto ao qual o anexo está associado", example = "101")
    private Integer projeto;

    @Column(name = "tipo_anexo", length = 15)
    @ApiModelProperty(value = "Tipo do anexo (Ex: Imagem, Documento, Vídeo)", example = "Imagem")
    private String tipoAnexo;

    @Column(name = "caminho_arquivo", columnDefinition = "TEXT")
    @ApiModelProperty(value = "Caminho onde o arquivo do anexo está armazenado", example = "/uploads/documento.pdf")
    private String caminhoArquivo;
}