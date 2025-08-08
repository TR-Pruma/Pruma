package com.br.pruma.core.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "tipo_usuario")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class TipoUsuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_usuario")
    private Integer id;

    @NotBlank(message = "A descrição do tipo de usuário é obrigatória")
    @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres")
    @Column(name = "descricao", length = 255, nullable = false)
    private String descricao;
    @CreationTimestamp
    @Column(name = "data_criacao", updatable = false)
    private LocalDateTime dataCriacao;

    @UpdateTimestamp
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @Version
    @Column(name = "versao")
    private Long versao;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo = true;

    @PrePersist
    protected void prePersist() {
        if (ativo == null) {
            ativo = true;
        }
    }
}

