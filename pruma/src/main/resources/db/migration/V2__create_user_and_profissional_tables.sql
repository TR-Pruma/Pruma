-- =============================================================================
-- V2 - Usuário, Cliente, ProfissionalDeBase e tabelas de perfil
-- =============================================================================

-- ─── usuario ──────────────────────────────────────────────────────────────────
CREATE TABLE usuario (
    usuario_id   INTEGER      GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    cpf          VARCHAR(14)  NOT NULL UNIQUE,
    senha        VARCHAR(255) NOT NULL,
    tipo         VARCHAR(30)  NOT NULL,
    created_at   TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at   TIMESTAMP    NOT NULL DEFAULT NOW(),
    ativo        BOOLEAN      NOT NULL DEFAULT TRUE,
    version      BIGINT       NOT NULL DEFAULT 0
);
CREATE INDEX idx_usuario_cpf  ON usuario(cpf);
CREATE INDEX idx_usuario_tipo ON usuario(tipo);

-- ─── cliente ──────────────────────────────────────────────────────────────────
CREATE TABLE cliente (
    cliente_id   INTEGER      GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    cliente_cpf  VARCHAR(14)  NOT NULL UNIQUE,
    endereco_id  INTEGER      REFERENCES endereco(endereco_id),
    created_at   TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at   TIMESTAMP    NOT NULL DEFAULT NOW(),
    ativo        BOOLEAN      NOT NULL DEFAULT TRUE,
    version      BIGINT       NOT NULL DEFAULT 0
);
CREATE INDEX idx_cliente_cpf ON cliente(cliente_cpf);

-- ─── cliente_tipo ─────────────────────────────────────────────────────────────
CREATE TABLE cliente_tipo (
    id_cliente_tipo  INTEGER     GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    descricao_cliente VARCHAR(100) NOT NULL,
    tipo_usuario     INTEGER     NOT NULL REFERENCES tipo_usuario(tipo_usuario),
    data_criacao     TIMESTAMP   NOT NULL DEFAULT NOW(),
    data_atualizacao TIMESTAMP   NOT NULL DEFAULT NOW(),
    versao           BIGINT      NOT NULL DEFAULT 0,
    ativo            BOOLEAN     NOT NULL DEFAULT TRUE
);

-- ─── permissao_usuario ────────────────────────────────────────────────────────
CREATE TABLE permissao_usuario (
    permissao_id INTEGER     GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    cliente_cpf  VARCHAR(14) NOT NULL REFERENCES cliente(cliente_cpf),
    tipo_usuario INTEGER     NOT NULL REFERENCES tipo_usuario(tipo_usuario),
    created_at   TIMESTAMP   NOT NULL DEFAULT NOW(),
    updated_at   TIMESTAMP   NOT NULL DEFAULT NOW(),
    ativo        BOOLEAN     NOT NULL DEFAULT TRUE,
    version      BIGINT      NOT NULL DEFAULT 0
);

-- ─── profissional_de_base ─────────────────────────────────────────────────────
CREATE TABLE profissional_de_base (
    profissional_id           INTEGER      GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    profissional_cpf          VARCHAR(14)  NOT NULL UNIQUE,
    nome                      VARCHAR(150) NOT NULL,
    especialidade             VARCHAR(100),
    telefone                  VARCHAR(30),
    telefone_whatsapp         VARCHAR(30),
    trabalhador_informal      BOOLEAN      NOT NULL DEFAULT FALSE,
    score_tfe                 NUMERIC(10,4),
    ultimo_login_app          TIMESTAMP,
    total_obras_concluidas    INTEGER      NOT NULL DEFAULT 0,
    padrinho_id               INTEGER      REFERENCES profissional_de_base(profissional_id),
    created_at                TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at                TIMESTAMP    NOT NULL DEFAULT NOW(),
    version                   BIGINT       NOT NULL DEFAULT 0
);
CREATE INDEX idx_profissional_cpf      ON profissional_de_base(profissional_cpf);
CREATE INDEX idx_profissional_padrinho ON profissional_de_base(padrinho_id);

-- ─── profissional_especialidade ───────────────────────────────────────────────
CREATE TABLE profissional_especialidade (
    profissional_id  INTEGER NOT NULL REFERENCES profissional_de_base(profissional_id),
    especialidade_id INTEGER NOT NULL REFERENCES especialidade(especialidade_id),
    PRIMARY KEY (profissional_id, especialidade_id)
);

-- ─── apadrinhamento_rede ──────────────────────────────────────────────────────
CREATE TABLE apadrinhamento_rede (
    apadrinhamento_id BIGINT    GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    padrinho_id       INTEGER   NOT NULL REFERENCES profissional_de_base(profissional_id),
    afilhado_id       INTEGER   NOT NULL REFERENCES profissional_de_base(profissional_id),
    data_inicio       DATE      NOT NULL,
    data_fim          DATE,
    status            VARCHAR(20) NOT NULL DEFAULT 'ATIVO',
    created_at        TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at        TIMESTAMP NOT NULL DEFAULT NOW()
);
CREATE INDEX idx_apadrinhamento_padrinho ON apadrinhamento_rede(padrinho_id);
CREATE INDEX idx_apadrinhamento_afilhado ON apadrinhamento_rede(afilhado_id);
CREATE INDEX idx_apadrinhamento_status   ON apadrinhamento_rede(status);

-- ─── score_tfe ────────────────────────────────────────────────────────────────
CREATE TABLE score_tfe (
    score_tfe_id       BIGINT        GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    profissional_id    INTEGER       NOT NULL REFERENCES profissional_de_base(profissional_id),
    score_total        NUMERIC(10,4) NOT NULL,
    score_tecnico      NUMERIC(10,4),
    score_operacional  NUMERIC(10,4),
    score_social       NUMERIC(10,4),
    versao_algoritmo   VARCHAR(30),
    calculado_em       TIMESTAMP     NOT NULL DEFAULT NOW(),
    created_at         TIMESTAMP     NOT NULL DEFAULT NOW(),
    updated_at         TIMESTAMP     NOT NULL DEFAULT NOW(),
    ativo              BOOLEAN       NOT NULL DEFAULT TRUE,
    version            BIGINT        NOT NULL DEFAULT 0
);
CREATE INDEX idx_score_tfe_profissional ON score_tfe(profissional_id);

-- ─── historico_score_tfe ──────────────────────────────────────────────────────
CREATE TABLE historico_score_tfe (
    historico_score_id         BIGINT        GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    profissional_id            INTEGER       NOT NULL REFERENCES profissional_de_base(profissional_id),
    score_total                NUMERIC(10,4) NOT NULL,
    score_desempenho_tecnico   NUMERIC(10,4),
    score_comportamental       NUMERIC(10,4),
    score_social               NUMERIC(10,4),
    versao_algoritmo           VARCHAR(30),
    calculado_em               TIMESTAMP     NOT NULL DEFAULT NOW(),
    created_at                 TIMESTAMP     NOT NULL DEFAULT NOW()
);
CREATE INDEX idx_historico_score_profissional ON historico_score_tfe(profissional_id);

-- ─── metricas_desempenho_tecnico ──────────────────────────────────────────────
CREATE TABLE metricas_desempenho_tecnico (
    metrica_tecnico_id              BIGINT        GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    profissional_id                 INTEGER       NOT NULL REFERENCES profissional_de_base(profissional_id),
    desvio_pontualidade_horas       NUMERIC(10,2),
    total_etapas_concluidas         INTEGER       NOT NULL DEFAULT 0,
    total_etapas_planejadas         INTEGER       NOT NULL DEFAULT 0,
    qualidade_media_fotos           NUMERIC(5,2),
    percentual_fotos_validas_exif   NUMERIC(5,2),
    periodo_referencia_inicio       DATE,
    periodo_referencia_fim          DATE,
    created_at                      TIMESTAMP     NOT NULL DEFAULT NOW(),
    updated_at                      TIMESTAMP     NOT NULL DEFAULT NOW(),
    ativo                           BOOLEAN       NOT NULL DEFAULT TRUE,
    version                         BIGINT        NOT NULL DEFAULT 0
);

-- ─── metricas_comportamento_operacional ───────────────────────────────────────
CREATE TABLE metricas_comportamento_operacional (
    metrica_operacional_id          BIGINT        GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    profissional_id                 INTEGER       NOT NULL REFERENCES profissional_de_base(profissional_id),
    frequencia_login_dias           INTEGER,
    tempo_medio_resposta_horas      NUMERIC(10,2),
    indice_consistencia_documental  NUMERIC(5,2),
    total_solicitacoes_respondidas  INTEGER       NOT NULL DEFAULT 0,
    total_solicitacoes_recebidas    INTEGER       NOT NULL DEFAULT 0,
    periodo_referencia_inicio       DATE,
    periodo_referencia_fim          DATE,
    created_at                      TIMESTAMP     NOT NULL DEFAULT NOW(),
    updated_at                      TIMESTAMP     NOT NULL DEFAULT NOW(),
    ativo                           BOOLEAN       NOT NULL DEFAULT TRUE,
    version                         BIGINT        NOT NULL DEFAULT 0
);

-- ─── trajetoria_laboral ───────────────────────────────────────────────────────
-- FKs obra_id e projeto_id adicionadas em V3 via ALTER TABLE
CREATE TABLE trajetoria_laboral (
    trajetoria_id          BIGINT        GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    profissional_id        INTEGER       NOT NULL REFERENCES profissional_de_base(profissional_id),
    funcao_desempenhada    VARCHAR(150),
    data_inicio            DATE          NOT NULL,
    data_fim               DATE,
    obra_concluida         BOOLEAN       NOT NULL DEFAULT FALSE,
    avaliacao_recebida     NUMERIC(3,1),
    comentario_avaliacao   TEXT,
    created_at             TIMESTAMP     NOT NULL DEFAULT NOW(),
    updated_at             TIMESTAMP     NOT NULL DEFAULT NOW(),
    ativo                  BOOLEAN       NOT NULL DEFAULT TRUE,
    version                BIGINT        NOT NULL DEFAULT 0
);

-- ─── proposta_credito ─────────────────────────────────────────────────────────
CREATE TABLE proposta_credito (
    proposta_credito_id  BIGINT        GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    profissional_id      INTEGER       NOT NULL REFERENCES profissional_de_base(profissional_id),
    score_no_momento     NUMERIC(10,4),
    valor_solicitado     NUMERIC(15,2) NOT NULL,
    valor_aprovado       NUMERIC(15,2),
    taxa_juros           NUMERIC(5,4),
    numero_parcelas      INTEGER,
    status               VARCHAR(30)   NOT NULL DEFAULT 'PENDENTE',
    parceiro_financeiro  VARCHAR(150),
    data_vencimento      DATE,
    created_at           TIMESTAMP     NOT NULL DEFAULT NOW(),
    updated_at           TIMESTAMP     NOT NULL DEFAULT NOW(),
    version              BIGINT        NOT NULL DEFAULT 0
);
CREATE INDEX idx_proposta_credito_profissional ON proposta_credito(profissional_id);
CREATE INDEX idx_proposta_credito_status       ON proposta_credito(status);

-- ─── parcela_credito ──────────────────────────────────────────────────────────
CREATE TABLE parcela_credito (
    parcela_credito_id  BIGINT        GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    proposta_credito_id BIGINT        NOT NULL REFERENCES proposta_credito(proposta_credito_id),
    numero_parcela      INTEGER       NOT NULL,
    valor_parcela       NUMERIC(15,2) NOT NULL,
    data_vencimento     DATE          NOT NULL,
    data_pagamento      DATE,
    valor_pago          NUMERIC(15,2),
    status              VARCHAR(30)   NOT NULL DEFAULT 'PENDENTE',
    modalidade_pagamento VARCHAR(50),
    created_at          TIMESTAMP     NOT NULL DEFAULT NOW(),
    updated_at          TIMESTAMP     NOT NULL DEFAULT NOW()
);
CREATE INDEX idx_parcela_credito_proposta ON parcela_credito(proposta_credito_id);
