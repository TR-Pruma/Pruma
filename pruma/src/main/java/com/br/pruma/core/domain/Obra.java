package com.br.pruma.core.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
@Entity
@Table(
        name    = "obra",
        indexes = {
                @Index(name = "idx_obra_projeto",     columnList = "projeto_id"),
                @Index(name = "idx_obra_data_inicio", columnList = "data_inicio")
        }
)
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class Obra implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "obra_id", updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Integer id;

    @NotNull(message = "Projeto é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "projeto_id", nullable = false)
    private Projeto projeto;

    @NotBlank(message = "Descrição é obrigatória")
    @Column(name = "descricao", columnDefinition = "TEXT", nullable = false)
    private String descricao;

    @NotNull(message = "Data de início é obrigatória")
    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @Column(name = "data_fim")
    private LocalDate dataFim;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;
}