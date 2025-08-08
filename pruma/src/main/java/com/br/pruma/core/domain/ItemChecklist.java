package com.br.pruma.core.domain;

import com.br.pruma.core.enums.StatusItem;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

import java.io.Serializable;

@FilterDef(name = "ativoFilter", parameters = @ParamDef(name = "ativo", type = boolean.class))
@Filter(name = "ativoFilter", condition = "ativo = :ativo")

@Entity
@Getter
@Setter
@ToString(exclude = "checklist")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Table(name = "item_checklist",
    indexes = @Index(name = "idx_item_checklist_ordem", columnList = "ordem")
)
@SQLDelete(sql = "UPDATE item_checklist SET ativo = false WHERE item_id = ?")
@org.hibernate.annotations.Where(clause = "ativo = true")
@ApiModel(description = "Representa um item do checklist")
public class ItemChecklist implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    @ApiModelProperty(value = "Identificador único do item", example = "1")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "checklist_id", nullable = false)
    @NotNull(message = "O checklist é obrigatório")
    @ApiModelProperty(value = "Checklist ao qual o item pertence")
    private Checklist checklist;

    @NotBlank(message = "A descrição é obrigatória")
    @Column(name = "descricao", nullable = false)
    @ApiModelProperty(value = "Descrição do item", example = "Verificar documentação")
    private String descricao;

    @Column(name = "ordem", nullable = false)
    @ApiModelProperty(value = "Ordem do item no checklist", example = "1")
    private Integer ordem;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20, nullable = false)
    @NotNull(message = "O status é obrigatório")
    @ApiModelProperty(value = "Status do item", example = "PENDENTE")
    private StatusItem status = StatusItem.PENDENTE;

    @Column(name = "observacao")
    @ApiModelProperty(value = "Observações sobre o item", example = "Necessita revisão adicional")
    private String observacao;

    @Column(name = "ativo", nullable = false)
    @ApiModelProperty(value = "Indica se o item está ativo", example = "true")
    private boolean ativo = true;

    @PrePersist
    public void prePersist() {
        if (this.status == null) {
            this.status = StatusItem.PENDENTE;
        }
        this.ativo = true;
    }
}


