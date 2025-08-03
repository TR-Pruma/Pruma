package com.br.pruma.core.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comunicacao_aux")
public class ComunicacaoAux {

    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comunicacao_id", nullable = false)
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

    @Column(nullable = false)
    private Boolean ativo = true;
}
