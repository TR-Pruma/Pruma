package com.br.pruma.core.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "profissional_de_base",
        indexes = {
                @Index(name = "idx_profissional_cpf",           columnList = "profissional_cpf"),
                @Index(name = "idx_profissional_nome",          columnList = "nome"),
                @Index(name = "idx_profissional_especialidade", columnList = "especialidade"),
                @Index(name = "idx_profissional_padrinho",      columnList = "padrinho_id")
        }
)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class ProfissionalDeBase implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profissional_id", updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Integer id;

    // Nome explicito necessario: HistoricoLocalizacao e Inspecao referenciam
    // esta coluna via @JoinColumn(referencedColumnName = "profissional_cpf")
    @NotBlank(message = "CPF é obrigatório")
    @Pattern(regexp = "^\\d{11}$", message = "CPF deve conter 11 dígitos numéricos")
    @Column(name = "profissional_cpf", nullable = false, unique = true, length = 11)
    private String cpf;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100)
    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @Size(max = 50)
    @Column(name = "especialidade", length = 50)
    private String especialidade;

    @Size(max = 20)
    @Column(name = "telefone", length = 20)
    @ToString.Exclude
    private String telefone;

    /** Canal principal de entrada MOR — número WhatsApp com DDI (ex: 5511999990000) */
    @Size(max = 20)
    @Column(name = "telefone_whatsapp", length = 20)
    @ToString.Exclude
    private String telefoneWhatsapp;

    /** Flag que identifica trabalhador informal (pedreiro, pintor, eletricista, etc.) */
    @Column(name = "trabalhador_informal", nullable = false)
    @Builder.Default
    private Boolean trabalhadorInformal = false;

    /**
     * Snapshot do último score TFE calculado pelo engine externo.
     * Somente escrito via endpoint protegido ROLE_ENGINE — nunca calculado aqui.
     */
    @Column(name = "score_tfe", precision = 5, scale = 4)
    private BigDecimal scoreTfe;

    /** Timestamp do último login registrado — alimenta componente ψ_op do TFE */
    @Column(name = "ultimo_login_app")
    private LocalDateTime ultimoLoginApp;

    /** Contador desnormalizado de obras concluídas — atualizado pelo serviço de obra */
    @Column(name = "total_obras_concluidas", nullable = false)
    @Builder.Default
    private Integer totalObrasConcluidas = 0;

    /**
     * Padrinho deste profissional no grafo social.
     * A relação completa (histórico, datas, status) fica em ApadrinhamentoRede.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "padrinho_id")
    private ProfissionalDeBase padrinho;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    public void applyPatch(ProfissionalDeBase patch) {
        if (patch == null) return;
        if (patch.getNome() != null && !patch.getNome().isBlank()) this.setNome(patch.getNome());
        if (patch.getCpf()  != null && !patch.getCpf().isBlank())  this.setCpf(patch.getCpf());
        if (patch.getEspecialidade()    != null) this.setEspecialidade(patch.getEspecialidade());
        if (patch.getTelefone()         != null) this.setTelefone(patch.getTelefone());
        if (patch.getTelefoneWhatsapp() != null) this.setTelefoneWhatsapp(patch.getTelefoneWhatsapp());
        if (patch.getTrabalhadorInformal() != null) this.setTrabalhadorInformal(patch.getTrabalhadorInformal());
        if (patch.getPadrinho()         != null) this.setPadrinho(patch.getPadrinho());
    }

    public static ProfissionalDeBase ofId(Integer id) {
        if (id == null) return null;
        return ProfissionalDeBase.builder().id(id).build();
    }
}
