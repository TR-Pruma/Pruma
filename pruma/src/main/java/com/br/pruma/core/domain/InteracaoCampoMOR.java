package com.br.pruma.core.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Registra cada entrada bruta processada pelo MOR (Módulo de Otimização de Recursos)
 * originada via WhatsApp. O MOR processa no projeto principal e persiste o resultado
 * estruturado aqui como registro imutável de campo.
 */
@Entity
@Table(
        name = "interacao_campo_mor",
        indexes = {
                @Index(name = "idx_mor_profissional",   columnList = "profissional_id"),
                @Index(name = "idx_mor_obra",           columnList = "obra_id"),
                @Index(name = "idx_mor_status",         columnList = "status_processamento"),
                @Index(name = "idx_mor_timestamp",      columnList = "timestamp_campo")
        }
)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class InteracaoCampoMOR implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interacao_id", updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    private Long id;

    @NotNull(message = "Profissional é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "profissional_id", nullable = false)
    private ProfissionalDeBase profissional;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "obra_id")
    private Obra obra;

    /** FOTO | AUDIO | TEXTO */
    @NotNull
    @Column(name = "tipo_midia", length = 10, nullable = false)
    private String tipoMidia;

    /** Path no storage (S3/GCS) do arquivo bruto recebido */
    @Column(name = "url_midia", length = 512)
    private String urlMidia;

    /** Geolocalização no momento do envio — formato "lat,lng" */
    @Column(name = "geolocalizacao", length = 50)
    private String geolocalizacao;

    /** Timestamp capturado pelo dispositivo do trabalhador no momento do envio */
    @NotNull
    @Column(name = "timestamp_campo", nullable = false)
    private LocalDateTime timestampCampo;

    /** Resultado da validação EXIF para fotos (null = não aplicável ou não verificado) */
    @Column(name = "exif_valido")
    private Boolean exifValido;

    /** SHA-256 do arquivo original — garante imutabilidade do registro de campo */
    @Column(name = "hash_integridade", length = 64)
    private String hashIntegridade;

    /** PENDENTE | PROCESSADO | REJEITADO */
    @Column(name = "status_processamento", length = 20, nullable = false)
    @Builder.Default
    private String statusProcessamento = "PENDENTE";

    /** JSON com dados estruturados extraídos pelo parser MOR (materiais, medições, etc.) */
    @Column(name = "dados_extraidos", columnDefinition = "TEXT")
    private String dadosExtraidos;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
