package com.br.pruma.core.domain;

import com.br.pruma.core.enums.StatusItem;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Entity
@Getter
@Setter
@ToString(exclude = {"projeto", "itens"})
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Table(name = "checklist",
    indexes = {
        @Index(name = "idx_checklist_projeto", columnList = "projeto_id"),
        @Index(name = "idx_checklist_nome", columnList = "nome")
    }
)
@SQLDelete(sql = "UPDATE checklist SET ativo = false WHERE checklist_id = ?")
@FilterDef(name = "ativoFilter", parameters = @ParamDef(name = "ativo", type = boolean.class))
@Filter(name = "ativoFilter", condition = "ativo = :ativo")
@ApiModel(description = "Representa um checklist de projeto")
public class Checklist implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "checklist_id")
    @ApiModelProperty(value = "Identificador único do checklist", example = "1")
    private Integer id;

    @NotBlank(message = "O nome do checklist é obrigatório")
    @Size(min = 3, max = 50, message = "O nome deve ter entre 3 e 50 caracteres")
    @Column(name = "nome", length = 50, nullable = false)
    @ApiModelProperty(value = "Nome do checklist", example = "Checklist de Qualidade")
    private String nome;

    @NotNull(message = "O projeto é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "projeto_id", nullable = false)
    @ApiModelProperty(value = "Projeto ao qual o checklist pertence")
    private Projeto projeto;

    @OneToMany(mappedBy = "checklist", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("ordem ASC")
    @ApiModelProperty(value = "Itens do checklist")
    private List<ItemChecklist> itens = new ArrayList<>();

    @Column(name = "ativo", nullable = false)
    @ApiModelProperty(value = "Indica se o checklist está ativo", example = "true")
    private boolean ativo = true;

    @CreationTimestamp
    @Column(name = "data_criacao", nullable = false, updatable = false)
    @ApiModelProperty(value = "Data de criação do checklist")
    private LocalDateTime dataCriacao;

    @UpdateTimestamp
    @Column(name = "data_atualizacao")
    @ApiModelProperty(value = "Data da última atualização do checklist")
    private LocalDateTime dataAtualizacao;

    @Version
    @Column(name = "version")
    private Long version;

    @PrePersist
    public void prePersist() {
        this.ativo = true;
        if (this.dataCriacao == null) {
            this.dataCriacao = LocalDateTime.now();
        }
    }

    public void addItem(ItemChecklist item) {
        if (item == null) {
            throw new IllegalArgumentException("Item não pode ser nulo");
        }
        item.setChecklist(this);
        item.setStatus(StatusItem.PENDENTE);
        item.setOrdem(this.itens.size() + 1);
        item.setAtivo(true);
        this.itens.add(item);
    }

    public void removeItem(ItemChecklist item) {
        if (item != null && this.itens.remove(item)) {
            item.setChecklist(null);
            item.setAtivo(false);
            reordenarItens();
        }
    }

    public void moverItem(ItemChecklist item, int novaOrdem) {
        if (!itens.contains(item)) {
            throw new IllegalArgumentException("Item não pertence a este checklist");
        }
        if (novaOrdem < 1 || novaOrdem > itens.size()) {
            throw new IllegalArgumentException("Ordem inválida");
        }
        int ordemAtual = item.getOrdem();
        if (novaOrdem == ordemAtual) {
            return;
        }
        
        itens.stream()
            .filter(i -> i != item)
            .forEach(i -> {
                if (novaOrdem < ordemAtual && i.getOrdem() >= novaOrdem && i.getOrdem() < ordemAtual) {
                    i.setOrdem(i.getOrdem() + 1);
                } else if (novaOrdem > ordemAtual && i.getOrdem() <= novaOrdem && i.getOrdem() > ordemAtual) {
                    i.setOrdem(i.getOrdem() - 1);
                }
            });
        item.setOrdem(novaOrdem);
    }

    private void reordenarItens() {
        int ordem = 1;
        for (ItemChecklist item : itens.stream()
                .sorted(Comparator.comparing(ItemChecklist::getOrdem))
                .toList()) {
            item.setOrdem(ordem++);
        }
    }

    public boolean isCompleto() {
        return !itens.isEmpty() && 
               itens.stream()
                    .allMatch(item -> StatusItem.CONCLUIDO.equals(item.getStatus()) || 
                                    StatusItem.CANCELADO.equals(item.getStatus()));
    }

    public long getPercentualConcluido() {
        if (itens.isEmpty()) {
            return 0L;
        }
        long concluidos = itens.stream()
                .filter(item -> StatusItem.CONCLUIDO.equals(item.getStatus()))
                .count();
        return (concluidos * 100L) / itens.size();
    }
}
