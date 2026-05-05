-- =============================================================================
-- V1 - Tabelas de base (sem FKs para outras entidades do domínio)
-- =============================================================================

-- ─── tipo_usuario ─────────────────────────────────────────────────────────────
CREATE TABLE tipo_usuario (
    tipo_usuario     SERIAL        PRIMARY KEY,
    descricao        VARCHAR(100)  NOT NULL,
    ativo            BOOLEAN       NOT NULL DEFAULT TRUE,
    data_criacao     TIMESTAMP     NOT NULL DEFAULT NOW(),
    data_atualizacao TIMESTAMP     NOT NULL DEFAULT NOW(),
    versao           BIGINT        NOT NULL DEFAULT 0
);

-- ─── tipo_documento ───────────────────────────────────────────────────────────
CREATE TABLE tipo_documento (
    tipo_documento_id SERIAL       PRIMARY KEY,
    descricao         VARCHAR(100) NOT NULL,
    ativo             BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at        TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at        TIMESTAMP    NOT NULL DEFAULT NOW()
);

-- ─── logradouro ───────────────────────────────────────────────────────────────
CREATE TABLE logradouro (
    id_logradouro SERIAL       PRIMARY KEY,
    tipo          VARCHAR(60)  NOT NULL,
    version       BIGINT       NOT NULL DEFAULT 0,
    created_at    TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at    TIMESTAMP    NOT NULL DEFAULT NOW()
);

-- ─── empresa ──────────────────────────────────────────────────────────────────
CREATE TABLE empresa (
    empresa_cnpj  VARCHAR(18)  PRIMARY KEY,
    razao_social  VARCHAR(150) NOT NULL,
    nome_fantasia VARCHAR(150),
    created_at    TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at    TIMESTAMP    NOT NULL DEFAULT NOW(),
    ativo         BOOLEAN      NOT NULL DEFAULT TRUE,
    version       BIGINT       NOT NULL DEFAULT 0
);

-- ─── endereco ─────────────────────────────────────────────────────────────────
CREATE TABLE endereco (
    endereco_id  SERIAL      PRIMARY KEY,
    empresa_cnpj VARCHAR(18) REFERENCES empresa(empresa_cnpj),
    created_at   TIMESTAMP   NOT NULL DEFAULT NOW(),
    updated_at   TIMESTAMP   NOT NULL DEFAULT NOW(),
    ativo        BOOLEAN     NOT NULL DEFAULT TRUE,
    version      BIGINT      NOT NULL DEFAULT 0
);

-- ─── categoria ────────────────────────────────────────────────────────────────
CREATE TABLE categoria (
    categoria_id INTEGER     GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nome         VARCHAR(100) NOT NULL,
    descricao    TEXT,
    created_at   TIMESTAMP   NOT NULL DEFAULT NOW(),
    updated_at   TIMESTAMP   NOT NULL DEFAULT NOW(),
    ativo        BOOLEAN     NOT NULL DEFAULT TRUE,
    version      BIGINT      NOT NULL DEFAULT 0
);

-- ─── projeto_categoria ────────────────────────────────────────────────────────
CREATE TABLE projeto_categoria (
    categoria_id INTEGER     GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nome         VARCHAR(100) NOT NULL,
    descricao    TEXT,
    created_at   TIMESTAMP   NOT NULL DEFAULT NOW(),
    updated_at   TIMESTAMP   NOT NULL DEFAULT NOW(),
    ativo        BOOLEAN     NOT NULL DEFAULT TRUE,
    version      BIGINT      NOT NULL DEFAULT 0
);

-- ─── especialidade ────────────────────────────────────────────────────────────
CREATE TABLE especialidade (
    especialidade_id INTEGER   GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nome             VARCHAR(100) NOT NULL,
    descricao        TEXT,
    created_at       TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at       TIMESTAMP NOT NULL DEFAULT NOW(),
    ativo            BOOLEAN   NOT NULL DEFAULT TRUE,
    version          BIGINT    NOT NULL DEFAULT 0
);

-- ─── equipamento ──────────────────────────────────────────────────────────────
CREATE TABLE equipamento (
    equipamento_id INTEGER      GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nome           VARCHAR(150) NOT NULL,
    descricao      TEXT,
    status         VARCHAR(30)  NOT NULL DEFAULT 'DISPONIVEL',
    created_at     TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at     TIMESTAMP    NOT NULL DEFAULT NOW(),
    ativo          BOOLEAN      NOT NULL DEFAULT TRUE,
    version        BIGINT       NOT NULL DEFAULT 0
);

-- ─── material ─────────────────────────────────────────────────────────────────
CREATE TABLE material (
    material_id    INTEGER      GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    descricao      VARCHAR(255) NOT NULL,
    quantidade     INTEGER      NOT NULL DEFAULT 0,
    custo_unitario NUMERIC(15,2),
    version        BIGINT       NOT NULL DEFAULT 0,
    created_at     TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at     TIMESTAMP    NOT NULL DEFAULT NOW()
);

-- ─── insumo ───────────────────────────────────────────────────────────────────
CREATE TABLE insumo (
    insumo_id      INTEGER      GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nome           VARCHAR(150) NOT NULL,
    descricao      TEXT,
    unidade_medida VARCHAR(30),
    custo          NUMERIC(15,2),
    created_at     TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at     TIMESTAMP    NOT NULL DEFAULT NOW(),
    ativo          BOOLEAN      NOT NULL DEFAULT TRUE,
    version        BIGINT       NOT NULL DEFAULT 0
);

-- ─── fornecedor ───────────────────────────────────────────────────────────────
CREATE TABLE fornecedor (
    fornecedor_id          INTEGER      GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nome                   VARCHAR(150) NOT NULL,
    cnpj                   VARCHAR(18)  UNIQUE,
    contato                VARCHAR(150),
    parceiro_marketplace   BOOLEAN      NOT NULL DEFAULT FALSE,
    taxa_comissao          NUMERIC(5,2),
    categorias_produtos    TEXT,
    ativo                  BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at             TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at             TIMESTAMP    NOT NULL DEFAULT NOW(),
    version                BIGINT       NOT NULL DEFAULT 0
);

-- ─── lojista_parceiro ─────────────────────────────────────────────────────────
CREATE TABLE lojista_parceiro (
    lojista_id                  INTEGER      GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    cnpj                        VARCHAR(18)  NOT NULL UNIQUE,
    razao_social                VARCHAR(150) NOT NULL,
    nome_fantasia               VARCHAR(150),
    email                       VARCHAR(150),
    telefone                    VARCHAR(30),
    taxa_comissao_percentual    NUMERIC(5,2) NOT NULL DEFAULT 0,
    categorias_produtos         TEXT,
    endereco_id                 INTEGER      REFERENCES endereco(endereco_id),
    created_at                  TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at                  TIMESTAMP    NOT NULL DEFAULT NOW(),
    ativo                       BOOLEAN      NOT NULL DEFAULT TRUE,
    version                     BIGINT       NOT NULL DEFAULT 0
);

-- ─── tecnico_de_obras ─────────────────────────────────────────────────────────
CREATE TABLE tecnico_de_obras (
    tecnico_id   INTEGER      GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nome         VARCHAR(150) NOT NULL,
    especialidade VARCHAR(100),
    telefone     VARCHAR(30),
    created_at   TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at   TIMESTAMP    NOT NULL DEFAULT NOW(),
    ativo        BOOLEAN      NOT NULL DEFAULT TRUE,
    version      BIGINT       NOT NULL DEFAULT 0
);

-- ─── status_equipamento ───────────────────────────────────────────────────────
CREATE TABLE status_equipamento (
    status_id   SERIAL       PRIMARY KEY,
    descricao   VARCHAR(50)  NOT NULL UNIQUE,
    ativo       BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at  TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMP    NOT NULL DEFAULT NOW()
);

-- ─── status_solicitacao ───────────────────────────────────────────────────────
CREATE TABLE status_solicitacao (
    status_solicitacao_id SERIAL      PRIMARY KEY,
    descricao             VARCHAR(80) NOT NULL UNIQUE,
    ativo                 BOOLEAN     NOT NULL DEFAULT TRUE,
    created_at            TIMESTAMP   NOT NULL DEFAULT NOW(),
    updated_at            TIMESTAMP   NOT NULL DEFAULT NOW()
);
