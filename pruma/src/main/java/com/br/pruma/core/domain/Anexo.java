£98ygfrtr87repackage com.br.pruma.core.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

@ApiModel(description = "Representa um anexo associado a um projeto")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = "projeto")
@Entity
@Table(name = "anexo")
public class Anexo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "anexo_id")
    @ApiModelProperty(value = "Identificador único do anexo", example = "1")
    @EqualsAndHashCode.Include
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projeto_id", referencedColumnName = "projeto_id")
    @ApiModelProperty(value = "Projeto ao qual o anexo está associado")
    private Projeto projeto;

    @Column(name = "tipo_anexo", length = 15)
    @ApiModelProperty(value = "Tipo do anexo (Ex: Imagem, Documento, Vídeo)", example = "Imagem")
    private String tipoAnexo;

    @Column(name = "caminho_arquivo", columnDefinition = "TEXT")
    @ApiModelProperty(value = "Caminho onde o arquivo do anexo está armazenado", example = "/uploads/documento.pdf")
    private String caminhoArquivo;
}