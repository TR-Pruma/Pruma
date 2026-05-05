-- =============================================================================
-- V6 - Tabelas de domínio ausentes nas migrations anteriores
--
-- Entidades cobertas:
--   1. lojista_parceiro              (LojistaParceiro)
--   2. apadrinhamento_rede           (ApadrinhamentoRede)
--   3. metricas_desempenho_tecnico   (MetricasDesempenhoTecnico)
--   4. metricas_comportamento_operacional (MetricasComportamentoOperacional)
--   5. historico_score_tfe           (HistoricoScoreTFE)
--   6. proposta_credito              (PropostaCredito — dependência de ParcelaCredito)
--   7. parcela_credito               (ParcelaCredito)
--   8. permissao_usuario             (PermissaoUsuario)
-- =============================================================================

-- ─── lojista_parceiro ─────────────────────────────────────────────────────────
-- Fonte: LojistaParceiro extends AuditableEntity
-- AuditableEntity: created_at, updated_at, ativo, version
CREATE TABLE lojista_parceiro (
    lojista_id               INTEGER        GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    cnpj                     VARCHAR(14)    NOT NULL,
    razao_social             VARCHAR(255)   NOT NULL,
    nome_fantasia            VARCHAR(255),
    email                    VARCHAR(255),
    telefone                 VARCHAR(20),
    taxa_comissao_percentual NUMERIC(5, 2),
    categorias_produtos      VARCHAR(500),
    endereco_id              INTEGER        REFERENCES endereco(endereco_id),
    -- AuditableEntity
    created_at               TIMESTAMP      NOT NULL DEFAULT NOW(),
    updated_at               TIMESTAMP,
    ativo                    BOOLEAN        NOT NULL DEFAULT TRUE,
    version                  BIGINT         NOT NULL DEFAULT 0
);
CREATE UNIQUE INDEX idx_lojista_cnpj ON lojista_parceiro(cnpj);

-- ─── apadrinhamento_rede ──────────────────────────────────────────────────────
-- Fonte: ApadrinhamentoRede extends AuditableEntity
-- Grafo social de apadrinhamento — componente ψ_soc do TFE
-- padrinho_id e afilhado_id: FK para profissional_de_base
-- status: ATIVO | ENCERRADO
CREATE TABLE apadrinhamento_rede (
    apadrinhamento_id INTEGER    GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    padrinho_id       INTEGER    NOT NULL REFERENCES profissional_de_base(profissional_id),
    afilhado_id       INTEGER    NOT NULL REFERENCES profissional_de_base(profissional_id),
    data_inicio       DATE       NOT NULL,
    data_fim          DATE,                       -- NULL = relacionamento ainda ativo
    status            VARCHAR(20) NOT NULL DEFAULT 'ATIVO',
    -- AuditableEntity
    created_at        TIMESTAMP  NOT NULL DEFAULT NOW(),
    updated_at        TIMESTAMP,
    ativo             BOOLEAN    NOT NULL DEFAULT TRUE,
    version           BIGINT     NOT NULL DEFAULT 0
);
CREATE INDEX idx_apadrinhamento_padrinho ON apadrinhamento_rede(padrinho_id);
CREATE INDEX idx_apadrinhamento_afilhado ON apadrinhamento_rede(afilhado_id);
CREATE INDEX idx_apadrinhamento_status   ON apadrinhamento_rede(status);

-- ─── metricas_desempenho_tecnico ─────────────────────────────────────────────
-- Fonte: MetricasDesempenhoTecnico extends AuditableEntity
-- Componente ψ_tec do TFE — indicadores técnicos de execução em campo
CREATE TABLE metricas_desempenho_tecnico (
    metrica_tecnico_id            INTEGER          GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    profissional_id               INTEGER          NOT NULL REFERENCES profissional_de_base(profissional_id),
    desvio_pontualidade_horas     NUMERIC(10, 4),
    total_etapas_concluidas       INTEGER,
    total_etapas_planejadas       INTEGER,
    qualidade_media_fotos         NUMERIC(5, 4),
    percentual_fotos_validas_exif NUMERIC(5, 4),
    periodo_referencia_inicio     DATE,
    periodo_referencia_fim        DATE,
    -- AuditableEntity
    created_at                    TIMESTAMP        NOT NULL DEFAULT NOW(),
    updated_at                    TIMESTAMP,
    ativo                         BOOLEAN          NOT NULL DEFAULT TRUE,
    version                       BIGINT           NOT NULL DEFAULT 0
);
CREATE INDEX idx_metrica_tec_profissional ON metricas_desempenho_tecnico(profissional_id);

-- ─── metricas_comportamento_operacional ──────────────────────────────────────
-- Fonte: MetricasComportamentoOperacional extends AuditableEntity
-- Componente ψ_op do TFE — comportamento operacional / engajamento
CREATE TABLE metricas_comportamento_operacional (
    metrica_operacional_id         INTEGER          GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    profissional_id                INTEGER          NOT NULL REFERENCES profissional_de_base(profissional_id),
    frequencia_login_dias          INTEGER,
    tempo_medio_resposta_horas     NUMERIC(10, 4),
    indice_consistencia_documental NUMERIC(5, 4),
    total_solicitacoes_respondidas INTEGER,
    total_solicitacoes_recebidas   INTEGER,
    periodo_referencia_inicio      DATE,
    periodo_referencia_fim         DATE,
    -- AuditableEntity
    created_at                     TIMESTAMP        NOT NULL DEFAULT NOW(),
    updated_at                     TIMESTAMP,
    ativo                          BOOLEAN          NOT NULL DEFAULT TRUE,
    version                        BIGINT           NOT NULL DEFAULT 0
);
CREATE INDEX idx_metrica_op_profissional ON metricas_comportamento_operacional(profissional_id);

-- ─── historico_score_tfe ──────────────────────────────────────────────────────
-- Fonte: HistoricoScoreTFE implements Serializable (imutável — sem AuditableEntity)
-- Série temporal do Trust Flow Engine. Registros NUNCA atualizados, só inseridos.
-- Gerados pelo engine externo com ROLE_ENGINE.
CREATE TABLE historico_score_tfe (
    historico_score_id      BIGINT         GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    profissional_id         INTEGER        NOT NULL REFERENCES profissional_de_base(profissional_id),
    score_total             NUMERIC(5, 4)  NOT NULL,     -- Φ(w) resultado ponderado final
    score_desempenho_tecnico NUMERIC(5, 4),              -- ψ_tec  (peso 0.45)
    score_comportamental    NUMERIC(5, 4),               -- ψ_op
    score_social            NUMERIC(5, 4),               -- ψ_soc  (peso 0.50)
    versao_algoritmo        VARCHAR(20),                 -- rastreabilidade de versão do engine
    calculado_em            TIMESTAMP      NOT NULL,
    created_at              TIMESTAMP      NOT NULL DEFAULT NOW()
    -- Sem updated_at, ativo e version — entidade imutável por design
);
CREATE INDEX idx_score_tfe_profissional ON historico_score_tfe(profissional_id);
CREATE INDEX idx_score_tfe_calculado_em ON historico_score_tfe(calculado_em);

-- ─── proposta_credito ─────────────────────────────────────────────────────────
-- Dependência de parcela_credito (referenciada via @ManyToOne → PropostaCredito).
-- Classe PropostaCredito não encontrada no domínio atual — criada aqui como tabela
-- base mínima para garantir integridade referencial de parcela_credito.
CREATE TABLE proposta_credito (
    proposta_credito_id BIGINT         GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    profissional_id     INTEGER        REFERENCES profissional_de_base(profissional_id),
    valor_solicitado    NUMERIC(18, 2),
    valor_aprovado      NUMERIC(18, 2),
    taxa_juros          NUMERIC(5, 4),
    numero_parcelas     INTEGER,
    status              VARCHAR(30)    NOT NULL DEFAULT 'PENDENTE',
    data_solicitacao    DATE           NOT NULL DEFAULT CURRENT_DATE,
    data_aprovacao      DATE,
    created_at          TIMESTAMP      NOT NULL DEFAULT NOW(),
    updated_at          TIMESTAMP,
    ativo               BOOLEAN        NOT NULL DEFAULT TRUE,
    version             BIGINT         NOT NULL DEFAULT 0
);
CREATE INDEX idx_proposta_credito_profissional ON proposta_credito(profissional_id);
CREATE INDEX idx_proposta_credito_status       ON proposta_credito(status);

-- ─── parcela_credito ──────────────────────────────────────────────────────────
-- Fonte: ParcelaCredito implements Serializable
-- Parcelas de uma proposta de crédito. Sem AuditableEntity — lifecylce manual via @PrePersist/@PreUpdate.
-- status: ex. PENDENTE | PAGO | ATRASADO | CANCELADO
CREATE TABLE parcela_credito (
    parcela_credito_id  BIGINT         GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    proposta_credito_id BIGINT         NOT NULL REFERENCES proposta_credito(proposta_credito_id),
    numero_parcela      INTEGER,
    valor_parcela       NUMERIC(18, 2),
    data_vencimento     DATE,
    data_pagamento      DATE,
    valor_pago          NUMERIC(18, 2),
    status              VARCHAR(20),
    modalidade_pagamento VARCHAR(30),
    created_at          TIMESTAMP      NOT NULL DEFAULT NOW(),
    updated_at          TIMESTAMP
);
CREATE INDEX idx_parcela_proposta ON parcela_credito(proposta_credito_id);

-- ─── permissao_usuario ────────────────────────────────────────────────────────
-- Fonte: PermissaoUsuario extends AuditableEntity
-- Vincula um Cliente a um TipoUsuario com uma permissão específica (string, max 15).
CREATE TABLE permissao_usuario (
    id          BIGINT      GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    cliente_cpf VARCHAR(14) NOT NULL REFERENCES cliente(cliente_cpf),
    tipo_usuario INTEGER    NOT NULL REFERENCES tipo_usuario(tipo_usuario),
    permissao   VARCHAR(15) NOT NULL,
    -- AuditableEntity
    created_at  TIMESTAMP   NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMP,
    ativo       BOOLEAN     NOT NULL DEFAULT TRUE,
    version     BIGINT      NOT NULL DEFAULT 0
);
CREATE INDEX idx_permissao_usuario_cliente ON permissao_usuario(cliente_cpf);
CREATE INDEX idx_permissao_usuario_tipo    ON permissao_usuario(tipo_usuario);
