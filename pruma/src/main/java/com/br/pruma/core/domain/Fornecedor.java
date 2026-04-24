package com.br.pruma.core.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(
        name = "fornecedor",
        indexes = {
                @Index(name = "idx_fornecedor_nome", columnList = "nome"),
                @Index(name = "idx_fornecedor_cnpj", columnList = "cnpj", unique = true),
                @Index(name = "idx_fornecedor_marketplace", columnList = "parceiro_marketplace")
        }
)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class Fornecedor extends AuditableEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fornecedor_id", updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Integer id;

    @NotBlank(message = "Nome do fornecedor é obrigatório")
    @Size(max = 255)
    @Column(name = "nome", length = 255, nullable = false)
    @ToString.Include
    private String nome;

    @NotBlank(message = "CNPJ é obrigatório")
    @Pattern(
            regexp = "^(\\d{14}|\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2})$",
            message = "CNPJ inválido"
    )
    @Column(name = "cnpj", length = 18, nullable = false, unique = true)
    private String cnpj;

    @NotBlank(message = "Contato é obrigatório")
    @Size(max = 100)
    @Column(name = "contato", length = 100, nullable = false)
    private String contato;

    /**
     * Indica se este fornecedor é um lojista parceiro do Marketplace B2B.
     * Quando true, o algoritmo de roteamento do MOR pode direcioná-lo como destino de compra.
     */
    @Column(name = "parceiro_marketplace", nullable = false)
    @Builder.Default
    private Boolean parceiroMarketplace = false;

    /**
     * Taxa de comissão (take rate) aplicada a cada transação via marketplace.
     * Intervalo esperado: 0.03 a 0.08 (3% a 8% sobre GMV).
     */
    @DecimalMin(value = "0.00", message = "Taxa de comissão não pode ser negativa")
    @DecimalMax(value = "1.00", message = "Taxa de comissão não pode ultrapassar 100%")
    @Column(name = "taxa_comissao", precision = 5, scale = 4)
    private BigDecimal taxaComissao;

    /**
     * Categorias de produtos oferecidas — armazenado como JSON array (ex: ["cimento","areia"]).
     * Usado pelo algoritmo de roteamento do MOR para match com lista de materiais.
     */
    @Column(name = "categorias_produtos", columnDefinition = "TEXT")
    private String categoriasProdutos;

    /** Flag de ativação — fornecedores inativos são ignorados pelo roteamento MOR */
    @Column(name = "ativo", nullable = false)
    @Builder.Default
    private Boolean ativo = true;
}
