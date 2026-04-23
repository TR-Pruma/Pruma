package com.br.pruma.core.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "imagem_projeto",
        indexes = {
                @Index(name = "idx_imagem_projeto_id",    columnList = "projeto_id"),
                @Index(name = "idx_imagem_exif_invalida", columnList = "exif_manipulada")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@ToString(exclude = "projeto")
public class ImagemProjeto extends AuditableEntity{

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

    // -------------------------------------------------------------------------
    // Metadados EXIF — validação de integridade e anti-fraude de campo
    // -------------------------------------------------------------------------

    /** Latitude extraída do EXIF da foto */
    @Column(name = "exif_latitude")
    private Double exifLatitude;

    /** Longitude extraída do EXIF da foto */
    @Column(name = "exif_longitude")
    private Double exifLongitude;

    /** Timestamp registrado pelo dispositivo no momento da captura (EXIF DateTimeOriginal) */
    @Column(name = "exif_timestamp")
    private LocalDateTime exifTimestamp;

    /**
     * Flag setada pelo serviço de validação EXIF (projeto principal).
     * true = metadados suspeitos de manipulação; false = íntegros; null = não verificado ainda.
     */
    @Column(name = "exif_manipulada")
    private Boolean exifManipulada;

    /** Hash SHA-256 do arquivo original — garante imutabilidade do binário após upload */
    @Column(name = "hash_sha256", length = 64)
    private String hashSha256;
}
