package com.br.pruma.core.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.math.BigDecimal;

@Entity
@Table(
        name = "lojista_parceiro",
        indexes = @Index(name = "idx_lojista_cnpj", columnList = "cnpj", unique = true)
)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@ToString(onlyExplicitlyIncluded = true)
@Schema(description = "Lojista parceiro do marketplace Pruma")
public class LojistaParceiro extends AuditableEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lojista_id", updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Integer id;

    @NotBlank @Size(max = 14)
    @Column(name = "cnpj", length = 14, nullable = false, unique = true)
    private String cnpj;

    @NotBlank @Size(max = 255)
    @Column(name = "razao_social", nullable = false)
    private String razaoSocial;

    @Size(max = 255)
    @Column(name = "nome_fantasia")
    private String nomeFantasia;

    @Size(max = 255)
    @Column(name = "email")
    private String email;

    @Size(max = 20)
    @Column(name = "telefone", length = 20)
    private String telefone;

    @Column(name = "taxa_comissao_percentual", precision = 5, scale = 2)
    @Schema(description = "Taxa de comissao em %", example = "5.50")
    private BigDecimal taxaComissaoPercentual;

    @Size(max = 500)
    @Column(name = "categorias_produtos", length = 500)
    private String categoriasProdutos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;
}
