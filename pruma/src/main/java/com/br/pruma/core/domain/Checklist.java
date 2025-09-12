package com.br.pruma.core.domain;

import com.br.pruma.core.enums.StatusItem;
import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Index;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Entity
@Table(
        name    = "checklist",
        indexes = {
                @Index(name = "idx_checklist_projeto", columnList = "projeto_id"),
                @Index(name = "idx_checklist_nome",    columnList = "nome")
        }
)
@SQLDelete(sql = "UPDATE checklist SET ativo = false WHERE checklist_id = ?")
@FilterDef(
        name       = "ativoFilter",
        parameters = @ParamDef(name = "ativo", type = boolean.class)
)
@Filter(name = "ativoFilter", condition = "ativo = :ativo")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class Checklist implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "checklist_id", updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Integer id;

    @NotBlank(message = "O nome do checklist é obrigatório")
    @Size(min = 3, max = 50, message = "O nome deve ter entre 3 e 50 caracteres")
    @Column(name = "nome", length = 50, nullable = false)
    @ToString.Include
    private String nome;

    @NotNull(message = "O projeto é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "projeto_id", nullable = false)
    private Projeto projeto;

    @OneToMany(
            mappedBy      = "checklist",
            cascade       = CascadeType.ALL,
            orphanRemoval = true,
            fetch         = FetchType.LAZY
    )
    @OrderBy("ordem ASC")
    private List<ItemChecklist> itens = new ArrayList<>();

    @Column(name = "ativo", nullable = false)
    private boolean ativo = true;

    @CreationTimestamp
    @Column(name = "data_criacao", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "data_atualizacao", nullable = false)
    private LocalDateTime updatedAt;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    @PrePersist
    protected void prePersist() {
        this.ativo = true;
    }

    public void addItem(ItemChecklist item) {
        Objects.requireNonNull(item, "Item não pode ser nulo");
        item.setChecklist(this);
        item.setStatus(StatusItem.PENDENTE);
        item.setOrdem(itens.size() + 1);
        item.setAtivo(true);
        itens.add(item);
    }

    public void removeItem(ItemChecklist item) {
        if (itens.remove(item)) {
            item.setChecklist(null);
            item.setAtivo(false);
            reordenarItens();
        }
    }

    public void moverItem(ItemChecklist item, int novaOrdem) {
        if (!itens.contains(item)) {
            throw new IllegalArgumentException("Item não pertence a este checklist");
        }
        int tamanho = itens.size();
        if (novaOrdem < 1 || novaOrdem > tamanho) {
            throw new IllegalArgumentException("Ordem inválida");
        }
        int ordemAtual = item.getOrdem();
        if (novaOrdem == ordemAtual) {
            return;
        }

        for (ItemChecklist i : itens) {
            int o = i.getOrdem();
            if (i != item) {
                if (novaOrdem < ordemAtual && o >= novaOrdem && o < ordemAtual) {
                    i.setOrdem(o + 1);
                } else if (novaOrdem > ordemAtual && o <= novaOrdem && o > ordemAtual) {
                    i.setOrdem(o - 1);
                }
            }
        }
        item.setOrdem(novaOrdem);
    }

    private void reordenarItens() {
        List<ItemChecklist> ordenados = itens.stream()
                .sorted(Comparator.comparing(ItemChecklist::getOrdem))
                .collect(Collectors.toList());
        for (int idx = 0; idx < ordenados.size(); idx++) {
            ordenados.get(idx).setOrdem(idx + 1);
        }
    }

    public boolean isCompleto() {
        return !itens.isEmpty() &&
                itens.stream()
                        .allMatch(it -> it.getStatus() == StatusItem.CONCLUIDO
                                || it.getStatus() == StatusItem.CANCELADO);
    }

    public long getPercentualConcluido() {
        if (itens.isEmpty()) {
            return 0L;
        }
        long concluidos = itens.stream()
                .filter(it -> it.getStatus() == StatusItem.CONCLUIDO)
                .count();
        return (concluidos * 100L) / itens.size();
    }
}