package com.br.pruma.core.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "projeto",
        indexes = {
                @Index(name = "idx_projeto_nome", columnList = "nome"),
                @Index(name = "idx_projeto_data_criacao", columnList = "data_criacao")
        }
)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA requires a no-args constructor with at least protected visibility
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class Projeto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "projeto_id", updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Integer id;

    @Column(name = "nome", length = 100, nullable = false)
    @NotBlank(message = "Nome do projeto é obrigatório")
    @Size(max = 100)
    private String nome;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    /**
     * Data de criação do projeto no sentido de negócio (p.ex. quando foi iniciado).
     */
    @Column(name = "data_criacao")
    private LocalDate dataCriacao;

    /**
     * Relação com obras pertencentes a este projeto.
     * Fetch LAZY para evitar carregamento desnecessário; cascade para manter integridade do agregado.
     */
    @OneToMany(mappedBy = "projeto", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @Builder.Default
    private List<Obra> obras = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    // ----------------- Domain helpers -----------------

    /**
     * Atualiza apenas os campos não-nulos e válidos do patch.
     */
    public void applyPatch(Projeto patch) {
        if (patch == null) return;
        if (patch.getNome() != null && !patch.getNome().isBlank()) this.setNome(patch.getNome());
        if (patch.getDescricao() != null) this.setDescricao(patch.getDescricao());
        if (patch.getDataCriacao() != null) this.setDataCriacao(patch.getDataCriacao());
    }

    /**
     * Adiciona uma obra ao projeto e faz o binding bidirecional.
     */
    public void addObra(Obra obra) {
        if (obra == null) return;
        obras.add(obra);
        obra.setProjeto(this);
    }

    /**
     * Remove uma obra do projeto e limpa o relacionamento.
     */
    public void removeObra(Obra obra) {
        if (obra == null) return;
        obras.remove(obra);
        obra.setProjeto(null);
    }

    /**
     * Conveniência para mappers (MapStruct) quando for necessário criar uma referência
     * apenas com o identificador (por exemplo @MapsId ou associações).
     */
    public static Projeto ofId(Integer id) {
        if (id == null) return null;
        return Projeto.builder().id(id).build();
    }
}