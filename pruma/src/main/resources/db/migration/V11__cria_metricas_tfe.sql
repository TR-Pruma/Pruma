-- V11: Insumos de cálculo do TFE por eixo
-- Sem essas tabelas o score em historico_score_tfe é resultado sem rastreabilidade.
-- Cada linha representa um período de apuração (mensal/semanal) de um profissional.
--
-- metricas_desempenho_tecnico  → eixo σ_tec (peso α₁ = 0.45)
-- metricas_comportamento_operacional → eixo σ_op (peso α₂ = 0.30)

CREATE TABLE metricas_desempenho_tecnico (
    metrica_tecnico_id              BIGINT          NOT NULL AUTO_INCREMENT,
    profissional_id                 INT             NOT NULL,
    -- Pontualidade: desvio padrão σ_t = √E[(T_real - T_planejado)²] em horas
    desvio_pontualidade_horas       DECIMAL(10,4)   NOT NULL DEFAULT 0,
    -- Histórico de conclusão: processo de contagem N(t)
    total_etapas_concluidas         INT             NOT NULL DEFAULT 0,
    total_etapas_planejadas         INT             NOT NULL DEFAULT 0,
    -- Qualidade de fotos: análise EXIF — nota 0.0000–1.0000
    qualidade_media_fotos           DECIMAL(5,4)    NOT NULL DEFAULT 0,
    percentual_fotos_validas_exif   DECIMAL(5,4)    NOT NULL DEFAULT 0,
    periodo_referencia_inicio       DATE            NOT NULL,
    periodo_referencia_fim          DATE            NOT NULL,
    created_at                      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at                      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    ativo                           TINYINT(1)      NOT NULL DEFAULT 1,
    version                         BIGINT          NOT NULL DEFAULT 0,
    PRIMARY KEY (metrica_tecnico_id),
    CONSTRAINT fk_metrica_tec_profissional FOREIGN KEY (profissional_id)
        REFERENCES profissional_de_base (profissional_id)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    INDEX idx_metrica_tec_profissional       (profissional_id),
    INDEX idx_metrica_tec_periodo           (profissional_id, periodo_referencia_inicio, periodo_referencia_fim)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE metricas_comportamento_operacional (
    metrica_operacional_id          BIGINT          NOT NULL AUTO_INCREMENT,
    profissional_id                 INT             NOT NULL,
    -- Login: processo de Poisson não homogêneo — λ_login(t)
    frequencia_login_dias           INT             NOT NULL DEFAULT 0 COMMENT 'Nº de dias com login no período',
    -- Tempo resposta: lei de potência P(T>t) ~ t^{-α}
    tempo_medio_resposta_horas      DECIMAL(10,4)   NOT NULL DEFAULT 0,
    -- Consistência documental: índice de Jaccard J(A,B) = |A∩B| / |A∪B|
    indice_consistencia_documental  DECIMAL(5,4)    NOT NULL DEFAULT 0,
    total_solicitacoes_respondidas  INT             NOT NULL DEFAULT 0,
    total_solicitacoes_recebidas    INT             NOT NULL DEFAULT 0,
    periodo_referencia_inicio       DATE            NOT NULL,
    periodo_referencia_fim          DATE            NOT NULL,
    created_at                      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at                      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    ativo                           TINYINT(1)      NOT NULL DEFAULT 1,
    version                         BIGINT          NOT NULL DEFAULT 0,
    PRIMARY KEY (metrica_operacional_id),
    CONSTRAINT fk_metrica_op_profissional FOREIGN KEY (profissional_id)
        REFERENCES profissional_de_base (profissional_id)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    INDEX idx_metrica_op_profissional   (profissional_id),
    INDEX idx_metrica_op_periodo        (profissional_id, periodo_referencia_inicio, periodo_referencia_fim)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
