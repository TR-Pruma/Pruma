package com.br.pruma.core.domain;

import com.br.pruma.config.UnidadeMedidaConverter;
import com.br.pruma.core.enums.UnidadeMedida;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(
        name = "insumo",
        indexes = {
                @Index(name = "idx_insumo_nome", columnList = "nome")
        }
)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@ToString(onlyExplicitlyIncluded = true)
public class Insumo extends AuditableEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "insumo_id", updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Integer id;

    @NotBlank
    @Size(max = 255)
    @Column(name = "nome", nullable = false, length = 255)
    private String nome;

    @Size(max = 2000)
    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @NotNull
    @Convert(converter = UnidadeMedidaConverter.class)
    @Column(name = "unidade_medida", nullable = false, length = 15)
    private UnidadeMedida unidadeMedida;

    @NotNull
    @Positive
    @Column(name = "custo", nullable = false, precision = 10, scale = 2)
    private BigDecimal custo;

    public void atualizarCusto(BigDecimal novoCusto) {
        if (novoCusto == null || novoCusto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Novo custo deve ser maior que zero");
        }
        this.custo = novoCusto;
    }

    public void atualizarNome(String novoNome) {
        if (novoNome == null || novoNome.isBlank()) {
            throw new IllegalArgumentException("Novo nome não pode ser vazio");
        }
        this.nome = novoNome;
    }
}