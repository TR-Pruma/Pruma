package com.br.pruma.core.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comunicacao_aux")
@SQLDelete(sql = "UPDATE comunicacao_aux SET ativo = false WHERE comunicacao_id = ?")
@Where(clause = "ativo = true")
public class ComunicacaoAux {

    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comunicacao_id", referencedColumnName = "comunicacao_id")
    private Comunicacao comunicacao;

    @Column(name = "tipo_mensagem", length = 15, nullable = false)
    private String tipoMensagem;

    @CreationTimestamp
    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @UpdateTimestamp
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @Version
    @Column(name = "versao")
    private Long versao;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo = true;
}
