package com.br.pruma.core.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(
        name = "anexo",
        indexes = {
                @Index(name = "idx_anexo_projeto", columnList = "projeto_id"),
                @Index(name = "idx_anexo_tipo", columnList = "tipo_anexo")
        }
)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Schema(description = "Representa um anexo associado a um projeto")
public class Anexo extends AuditableEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "anexo_id", nullable = false, updatable = false)
    @EqualsAndHashCode.Include
    @ToString.Include
    @Schema(description = "Identificador único do anexo", example = "1")
    private Integer id;

    @NotNull(message = "Projeto é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "projeto_id", nullable = false)
    @ToString.Exclude
    @Schema(description = "Projeto ao qual o anexo está associado")
    private Projeto projeto;

    @NotBlank
    @Size(max = 15)
    @Column(name = "tipo_anexo", length = 15, nullable = false)
    @Schema(description = "Tipo do anexo (Ex: Imagem, Documento, Vídeo)", example = "Imagem")
    private String tipoAnexo;

    @NotBlank
    @Size(max = 1024)
    @Column(name = "caminho_arquivo", length = 1024, nullable = false)
    @Schema(description = "Caminho onde o arquivo está armazenado", example = "/uploads/documento.pdf")
    private String caminhoArquivo;

    @Size(max = 255)
    @Column(name = "nome_arquivo", length = 255)
    @Schema(description = "Nome original do arquivo", example = "documento.pdf")
    private String nomeArquivo;

    @Size(max = 100)
    @Column(name = "content_type", length = 100)
    @Schema(description = "Content type do arquivo", example = "application/pdf")
    private String contentType;

    @Column(name = "tamanho_bytes")
    @Schema(description = "Tamanho do arquivo em bytes", example = "102400")
    private Long tamanhoBytes;

    // ====== Métodos de domínio ======

    public void attachToProjeto(Projeto projeto) {
        this.projeto = projeto;
    }

    public boolean isImagem() {
        return tipoAnexo != null && tipoAnexo.trim().toLowerCase().contains("imagem");
    }
}
