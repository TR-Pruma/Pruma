package com.br.pruma.core.domain;

import com.br.pruma.config.UnidadeMedidaConverter;
import com.br.pruma.core.enums.UnidadeMedida;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(
        name = "insumo",
        indexes = {
                @Index(name = "idx_insumo_nome", columnList = "nome")
        }
)
public record Insumo(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "insumo_id", updatable = false)
        Integer id,

        @NotBlank
        @Size(max = 255)
        @Column(name = "nome", nullable = false, length = 255)
        String nome,

        @Size(max = 2000)
        @Column(name = "descricao", columnDefinition = "TEXT")
        String descricao,

        @NotNull
        @Convert(converter = UnidadeMedidaConverter.class)
        @Column(name = "unidade_medida", nullable = false, length = 15)
        UnidadeMedida unidadeMedida,

        @NotNull
        @Positive
        @Column(name = "custo", nullable = false, precision = 10, scale = 2)
        BigDecimal custo,

        @Version
        @Column(name = "version", nullable = false)
        Long version

) {
    // Construtor customizado para validar invariantes de domínio
    public Insumo {
        if (nome.isBlank()) {
            throw new IllegalArgumentException("Nome do insumo não pode ser vazio");
        }
        if (custo.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Custo deve ser maior que zero");
        }
    }

    // Factory method para criação — omite 'id' e 'version'
    public static Insumo of(String nome,
                            String descricao,
                            UnidadeMedida unidadeMedida,
                            BigDecimal custo) {
        return new Insumo(
                null,
                nome,
                descricao,
                unidadeMedida,
                custo,
                0L
        );
    }

    // Exemplo de método de atualização que retorna uma nova instância
    public Insumo withCusto(BigDecimal novoCusto) {
        return new Insumo(
                this.id,
                this.nome,
                this.descricao,
                this.unidadeMedida,
                novoCusto,
                this.version
        );
    }

    public Insumo withNome(String novoNome) {
        return new Insumo(
                this.id,
                novoNome,
                this.descricao,
                this.unidadeMedida,
                this.custo,
                this.version
        );
    }
}

