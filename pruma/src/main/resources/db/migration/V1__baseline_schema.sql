-- =============================================================================
-- PRUMA - Baseline Migration
-- Versao  : V1
-- Descricao: Schema inicial gerado das entidades JPA (baseline do banco existente)
--
-- IMPORTANTE: Se voce ja possui o banco criado pelo ddl-auto=update,
-- o Flyway vai usar baseline-on-migrate=true para marcar esta migration
-- como ja aplicada sem re-executar o SQL.
--
-- Se for um banco NOVO (ex: CI, novo dev, producao), este script cria
-- todo o schema do zero.
-- =============================================================================

SET FOREIGN_KEY_CHECKS = 0;

-- -----------------------------------------------------------------------------
-- tipo_usuario (referencia usada por usuario via enum, mantida para legibilidade)
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS tipo_usuario (
    tipo_usuario_id INT            NOT NULL AUTO_INCREMENT,
    nome            VARCHAR(50)    NOT NULL,
    descricao       VARCHAR(255),
    created_at      DATETIME(6)    NOT NULL,
    updated_at      DATETIME(6),
    ativo           TINYINT(1)     NOT NULL DEFAULT 1,
    version         BIGINT,
    PRIMARY KEY (tipo_usuario_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -----------------------------------------------------------------------------
-- usuario
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS usuario (
    usuario_id  INT            NOT NULL AUTO_INCREMENT,
    cpf         VARCHAR(11)    NOT NULL,
    senha       VARCHAR(255)   NOT NULL,
    tipo        VARCHAR(20)    NOT NULL,
    created_at  DATETIME(6)    NOT NULL,
    updated_at  DATETIME(6),
    ativo       TINYINT(1)     NOT NULL DEFAULT 1,
    version     BIGINT,
    PRIMARY KEY (usuario_id),
    CONSTRAINT uk_usuario_cpf UNIQUE (cpf),
    INDEX idx_usuario_cpf (cpf)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -----------------------------------------------------------------------------
-- empresa
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS empresa (
    empresa_id  INT            NOT NULL AUTO_INCREMENT,
    cnpj        VARCHAR(14)    NOT NULL,
    nome        VARCHAR(255)   NOT NULL,
    email       VARCHAR(255),
    telefone    VARCHAR(20),
    created_at  DATETIME(6)    NOT NULL,
    updated_at  DATETIME(6),
    ativo       TINYINT(1)     NOT NULL DEFAULT 1,
    version     BIGINT,
    PRIMARY KEY (empresa_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -----------------------------------------------------------------------------
-- endereco
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS endereco (
    endereco_id INT            NOT NULL AUTO_INCREMENT,
    cep         VARCHAR(8)     NOT NULL,
    logradouro  VARCHAR(255)   NOT NULL,
    numero      VARCHAR(20),
    complemento VARCHAR(255),
    bairro      VARCHAR(100),
    cidade      VARCHAR(100)   NOT NULL,
    estado      VARCHAR(2)     NOT NULL,
    created_at  DATETIME(6)    NOT NULL,
    updated_at  DATETIME(6),
    ativo       TINYINT(1)     NOT NULL DEFAULT 1,
    version     BIGINT,
    PRIMARY KEY (endereco_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -----------------------------------------------------------------------------
-- logradouro
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS logradouro (
    logradouro_id INT           NOT NULL AUTO_INCREMENT,
    cep           VARCHAR(8)    NOT NULL,
    logradouro    VARCHAR(255)  NOT NULL,
    bairro        VARCHAR(100),
    cidade        VARCHAR(100)  NOT NULL,
    estado        VARCHAR(2)    NOT NULL,
    created_at    DATETIME(6)   NOT NULL,
    updated_at    DATETIME(6),
    ativo         TINYINT(1)    NOT NULL DEFAULT 1,
    version       BIGINT,
    PRIMARY KEY (logradouro_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -----------------------------------------------------------------------------
-- cliente
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS cliente (
    cliente_id  INT            NOT NULL AUTO_INCREMENT,
    cpf         VARCHAR(11),
    cnpj        VARCHAR(14),
    nome        VARCHAR(255)   NOT NULL,
    email       VARCHAR(255),
    telefone    VARCHAR(20),
    endereco_id INT,
    created_at  DATETIME(6)    NOT NULL,
    updated_at  DATETIME(6),
    ativo       TINYINT(1)     NOT NULL DEFAULT 1,
    version     BIGINT,
    PRIMARY KEY (cliente_id),
    CONSTRAINT fk_cliente_endereco FOREIGN KEY (endereco_id) REFERENCES endereco (endereco_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -----------------------------------------------------------------------------
-- cliente_tipo
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS cliente_tipo (
    cliente_tipo_id INT          NOT NULL AUTO_INCREMENT,
    descricao       VARCHAR(100) NOT NULL,
    created_at      DATETIME(6)  NOT NULL,
    updated_at      DATETIME(6),
    ativo           TINYINT(1)   NOT NULL DEFAULT 1,
    version         BIGINT,
    PRIMARY KEY (cliente_tipo_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -----------------------------------------------------------------------------
-- categoria
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS categoria (
    categoria_id INT           NOT NULL AUTO_INCREMENT,
    nome         VARCHAR(100)  NOT NULL,
    descricao    VARCHAR(255),
    created_at   DATETIME(6)   NOT NULL,
    updated_at   DATETIME(6),
    ativo        TINYINT(1)    NOT NULL DEFAULT 1,
    version      BIGINT,
    PRIMARY KEY (categoria_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -----------------------------------------------------------------------------
-- fornecedor
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS fornecedor (
    fornecedor_id INT           NOT NULL AUTO_INCREMENT,
    cnpj          VARCHAR(14),
    cpf           VARCHAR(11),
    nome          VARCHAR(255)  NOT NULL,
    email         VARCHAR(255),
    telefone      VARCHAR(20),
    created_at    DATETIME(6)   NOT NULL,
    updated_at    DATETIME(6),
    ativo         TINYINT(1)    NOT NULL DEFAULT 1,
    version       BIGINT,
    PRIMARY KEY (fornecedor_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -----------------------------------------------------------------------------
-- equipamento
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS equipamento (
    equipamento_id INT           NOT NULL AUTO_INCREMENT,
    nome           VARCHAR(255)  NOT NULL,
    descricao      VARCHAR(500),
    created_at     DATETIME(6)   NOT NULL,
    updated_at     DATETIME(6),
    ativo          TINYINT(1)    NOT NULL DEFAULT 1,
    version        BIGINT,
    PRIMARY KEY (equipamento_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -----------------------------------------------------------------------------
-- status_equipamento
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS status_equipamento (
    status_equipamento_id INT          NOT NULL AUTO_INCREMENT,
    descricao             VARCHAR(100) NOT NULL,
    created_at            DATETIME(6)  NOT NULL,
    updated_at            DATETIME(6),
    ativo                 TINYINT(1)   NOT NULL DEFAULT 1,
    version               BIGINT,
    PRIMARY KEY (status_equipamento_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -----------------------------------------------------------------------------
-- material
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS material (
    material_id  INT           NOT NULL AUTO_INCREMENT,
    nome         VARCHAR(255)  NOT NULL,
    descricao    VARCHAR(500),
    unidade      VARCHAR(20),
    preco_unitario DECIMAL(15,2),
    estoque      DECIMAL(15,3),
    categoria_id INT,
    created_at   DATETIME(6)   NOT NULL,
    updated_at   DATETIME(6),
    ativo        TINYINT(1)    NOT NULL DEFAULT 1,
    version      BIGINT,
    PRIMARY KEY (material_id),
    CONSTRAINT fk_material_categoria FOREIGN KEY (categoria_id) REFERENCES categoria (categoria_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -----------------------------------------------------------------------------
-- insumo
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS insumo (
    insumo_id    INT           NOT NULL AUTO_INCREMENT,
    nome         VARCHAR(255)  NOT NULL,
    descricao    VARCHAR(500),
    unidade      VARCHAR(20),
    preco_unitario DECIMAL(15,2),
    categoria_id INT,
    created_at   DATETIME(6)   NOT NULL,
    updated_at   DATETIME(6),
    ativo        TINYINT(1)    NOT NULL DEFAULT 1,
    version      BIGINT,
    PRIMARY KEY (insumo_id),
    CONSTRAINT fk_insumo_categoria FOREIGN KEY (categoria_id) REFERENCES categoria (categoria_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -----------------------------------------------------------------------------
-- insumo_fornecedor
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS insumo_fornecedor (
    insumo_id     INT NOT NULL,
    fornecedor_id INT NOT NULL,
    PRIMARY KEY (insumo_id, fornecedor_id),
    CONSTRAINT fk_if_insumo    FOREIGN KEY (insumo_id)     REFERENCES insumo     (insumo_id),
    CONSTRAINT fk_if_fornecedor FOREIGN KEY (fornecedor_id) REFERENCES fornecedor (fornecedor_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -----------------------------------------------------------------------------
-- tipo_documento
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS tipo_documento (
    tipo_documento_id INT          NOT NULL AUTO_INCREMENT,
    descricao         VARCHAR(100) NOT NULL,
    created_at        DATETIME(6)  NOT NULL,
    updated_at        DATETIME(6),
    ativo             TINYINT(1)   NOT NULL DEFAULT 1,
    version           BIGINT,
    PRIMARY KEY (tipo_documento_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -----------------------------------------------------------------------------
-- projeto
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS projeto (
    projeto_id   INT            NOT NULL AUTO_INCREMENT,
    nome         VARCHAR(255)   NOT NULL,
    descricao    TEXT,
    status       VARCHAR(50),
    data_inicio  DATE,
    data_fim     DATE,
    cliente_id   INT,
    usuario_id   INT,
    endereco_id  INT,
    created_at   DATETIME(6)    NOT NULL,
    updated_at   DATETIME(6),
    ativo        TINYINT(1)     NOT NULL DEFAULT 1,
    version      BIGINT,
    PRIMARY KEY (projeto_id),
    CONSTRAINT fk_projeto_cliente  FOREIGN KEY (cliente_id)  REFERENCES cliente  (cliente_id),
    CONSTRAINT fk_projeto_usuario  FOREIGN KEY (usuario_id)  REFERENCES usuario  (usuario_id),
    CONSTRAINT fk_projeto_endereco FOREIGN KEY (endereco_id) REFERENCES endereco (endereco_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -----------------------------------------------------------------------------
-- projeto_categoria
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS projeto_categoria (
    projeto_id   INT NOT NULL,
    categoria_id INT NOT NULL,
    PRIMARY KEY (projeto_id, categoria_id),
    CONSTRAINT fk_pc_projeto   FOREIGN KEY (projeto_id)   REFERENCES projeto   (projeto_id),
    CONSTRAINT fk_pc_categoria FOREIGN KEY (categoria_id) REFERENCES categoria (categoria_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -----------------------------------------------------------------------------
-- profissional_de_base
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS profissional_de_base (
    profissional_id INT           NOT NULL AUTO_INCREMENT,
    nome            VARCHAR(255)  NOT NULL,
    cpf             VARCHAR(11),
    email           VARCHAR(255),
    telefone        VARCHAR(20),
    especialidade   VARCHAR(100),
    usuario_id      INT,
    created_at      DATETIME(6)   NOT NULL,
    updated_at      DATETIME(6),
    ativo           TINYINT(1)    NOT NULL DEFAULT 1,
    version         BIGINT,
    PRIMARY KEY (profissional_id),
    CONSTRAINT fk_prof_usuario FOREIGN KEY (usuario_id) REFERENCES usuario (usuario_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -----------------------------------------------------------------------------
-- tecnico_de_obras
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS tecnico_de_obras (
    tecnico_id      INT           NOT NULL AUTO_INCREMENT,
    nome            VARCHAR(255)  NOT NULL,
    cpf             VARCHAR(11),
    crea            VARCHAR(20),
    usuario_id      INT,
    created_at      DATETIME(6)   NOT NULL,
    updated_at      DATETIME(6),
    ativo           TINYINT(1)    NOT NULL DEFAULT 1,
    version         BIGINT,
    PRIMARY KEY (tecnico_id),
    CONSTRAINT fk_tecnico_usuario FOREIGN KEY (usuario_id) REFERENCES usuario (usuario_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -----------------------------------------------------------------------------
-- projeto_profissional
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS projeto_profissional (
    projeto_profissional_id INT NOT NULL AUTO_INCREMENT,
    projeto_id              INT NOT NULL,
    profissional_id         INT NOT NULL,
    papel                   VARCHAR(50),
    created_at              DATETIME(6) NOT NULL,
    updated_at              DATETIME(6),
    ativo                   TINYINT(1)  NOT NULL DEFAULT 1,
    version                 BIGINT,
    PRIMARY KEY (projeto_profissional_id),
    CONSTRAINT fk_pp_projeto      FOREIGN KEY (projeto_id)      REFERENCES projeto             (projeto_id),
    CONSTRAINT fk_pp_profissional FOREIGN KEY (profissional_id) REFERENCES profissional_de_base (profissional_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -----------------------------------------------------------------------------
-- pre_obra
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS pre_obra (
    pre_obra_id INT           NOT NULL AUTO_INCREMENT,
    projeto_id  INT,
    descricao   TEXT,
    status      VARCHAR(50),
    created_at  DATETIME(6)   NOT NULL,
    updated_at  DATETIME(6),
    ativo       TINYINT(1)    NOT NULL DEFAULT 1,
    version     BIGINT,
    PRIMARY KEY (pre_obra_id),
    CONSTRAINT fk_pre_obra_projeto FOREIGN KEY (projeto_id) REFERENCES projeto (projeto_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -----------------------------------------------------------------------------
-- obra
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS obra (
    obra_id     INT           NOT NULL AUTO_INCREMENT,
    projeto_id  INT,
    descricao   TEXT,
    status      VARCHAR(50),
    data_inicio DATE,
    data_fim    DATE,
    created_at  DATETIME(6)   NOT NULL,
    updated_at  DATETIME(6),
    ativo       TINYINT(1)    NOT NULL DEFAULT 1,
    version     BIGINT,
    PRIMARY KEY (obra_id),
    CONSTRAINT fk_obra_projeto FOREIGN KEY (projeto_id) REFERENCES projeto (projeto_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -----------------------------------------------------------------------------
-- pos_obra
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS pos_obra (
    pos_obra_id INT           NOT NULL AUTO_INCREMENT,
    obra_id     INT,
    descricao   TEXT,
    created_at  DATETIME(6)   NOT NULL,
    updated_at  DATETIME(6),
    ativo       TINYINT(1)    NOT NULL DEFAULT 1,
    version     BIGINT,
    PRIMARY KEY (pos_obra_id),
    CONSTRAINT fk_pos_obra_obra FOREIGN KEY (obra_id) REFERENCES obra (obra_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -----------------------------------------------------------------------------
-- orcamento
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS orcamento (
    orcamento_id INT           NOT NULL AUTO_INCREMENT,
    projeto_id   INT,
    valor_total  DECIMAL(15,2),
    status       VARCHAR(50),
    data_emissao DATE,
    created_at   DATETIME(6)   NOT NULL,
    updated_at   DATETIME(6),
    ativo        TINYINT(1)    NOT NULL DEFAULT 1,
    version      BIGINT,
    PRIMARY KEY (orcamento_id),
    CONSTRAINT fk_orcamento_projeto FOREIGN KEY (projeto_id) REFERENCES projeto (projeto_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -----------------------------------------------------------------------------
-- item_orcamento
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS item_orcamento (
    item_orcamento_id INT           NOT NULL AUTO_INCREMENT,
    orcamento_id      INT,
    descricao         VARCHAR(500),
    quantidade        DECIMAL(15,3),
    preco_unitario    DECIMAL(15,2),
    valor_total       DECIMAL(15,2),
    created_at        DATETIME(6)   NOT NULL,
    updated_at        DATETIME(6),
    ativo             TINYINT(1)    NOT NULL DEFAULT 1,
    version           BIGINT,
    PRIMARY KEY (item_orcamento_id),
    CONSTRAINT fk_item_orc_orcamento FOREIGN KEY (orcamento_id) REFERENCES orcamento (orcamento_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -----------------------------------------------------------------------------
-- pagamento
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS pagamento (
    pagamento_id INT           NOT NULL AUTO_INCREMENT,
    projeto_id   INT,
    valor        DECIMAL(15,2),
    status       VARCHAR(50),
    data_pagamento DATE,
    created_at   DATETIME(6)   NOT NULL,
    updated_at   DATETIME(6),
    ativo        TINYINT(1)    NOT NULL DEFAULT 1,
    version      BIGINT,
    PRIMARY KEY (pagamento_id),
    CONSTRAINT fk_pagamento_projeto FOREIGN KEY (projeto_id) REFERENCES projeto (projeto_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -----------------------------------------------------------------------------
-- sub_contrato
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS sub_contrato (
    sub_contrato_id INT           NOT NULL AUTO_INCREMENT,
    projeto_id      INT,
    fornecedor_id   INT,
    valor           DECIMAL(15,2),
    status          VARCHAR(50),
    created_at      DATETIME(6)   NOT NULL,
    updated_at      DATETIME(6),
    ativo           TINYINT(1)    NOT NULL DEFAULT 1,
    version         BIGINT,
    PRIMARY KEY (sub_contrato_id),
    CONSTRAINT fk_sc_projeto    FOREIGN KEY (projeto_id)   REFERENCES projeto    (projeto_id),
    CONSTRAINT fk_sc_fornecedor FOREIGN KEY (fornecedor_id) REFERENCES fornecedor (fornecedor_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -----------------------------------------------------------------------------
-- cronograma
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS cronograma (
    cronograma_id INT           NOT NULL AUTO_INCREMENT,
    projeto_id    INT,
    descricao     VARCHAR(255),
    created_at    DATETIME(6)   NOT NULL,
    updated_at    DATETIME(6),
    ativo         TINYINT(1)    NOT NULL DEFAULT 1,
    version       BIGINT,
    PRIMARY KEY (cronograma_id),
    CONSTRAINT fk_cronograma_projeto FOREIGN KEY (projeto_id) REFERENCES projeto (projeto_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -----------------------------------------------------------------------------
-- fase_cronograma
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS fase_cronograma (
    fase_cronograma_id INT          NOT NULL AUTO_INCREMENT,
    cronograma_id      INT,
    nome               VARCHAR(100),
    data_inicio        DATE,
    data_fim           DATE,
    created_at         DATETIME(6)  NOT NULL,
    updated_at         DATETIME(6),
    ativo              TINYINT(1)   NOT NULL DEFAULT 1,
    version            BIGINT,
    PRIMARY KEY (fase_cronograma_id),
    CONSTRAINT fk_fase_cronograma FOREIGN KEY (cronograma_id) REFERENCES cronograma (cronograma_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -----------------------------------------------------------------------------
-- atividade
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS atividade (
    atividade_id INT           NOT NULL AUTO_INCREMENT,
    obra_id      INT,
    descricao    VARCHAR(500),
    status       VARCHAR(50),
    responsavel  VARCHAR(255),
    data_inicio  DATE,
    data_fim     DATE,
    created_at   DATETIME(6)   NOT NULL,
    updated_at   DATETIME(6),
    ativo        TINYINT(1)    NOT NULL DEFAULT 1,
    version      BIGINT,
    PRIMARY KEY (atividade_id),
    CONSTRAINT fk_atividade_obra FOREIGN KEY (obra_id) REFERENCES obra (obra_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -----------------------------------------------------------------------------
-- tarefa
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS tarefa (
    tarefa_id    INT           NOT NULL AUTO_INCREMENT,
    atividade_id INT,
    descricao    VARCHAR(500),
    status       VARCHAR(50),
    created_at   DATETIME(6)   NOT NULL,
    updated_at   DATETIME(6),
    ativo        TINYINT(1)    NOT NULL DEFAULT 1,
    version      BIGINT,
    PRIMARY KEY (tarefa_id),
    CONSTRAINT fk_tarefa_atividade FOREIGN KEY (atividade_id) REFERENCES atividade (atividade_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -----------------------------------------------------------------------------
-- checklist
-- ATENCAO: projeto_id (FK para projeto), data_criacao, data_atualizacao
-- alinhado com entidade Checklist.java
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS checklist (
    checklist_id    INT           NOT NULL AUTO_INCREMENT,
    projeto_id      INT           NOT NULL,
    nome            VARCHAR(50)   NOT NULL,
    ativo           TINYINT(1)    NOT NULL DEFAULT 1,
    data_criacao    DATETIME(6)   NOT NULL,
    data_atualizacao DATETIME(6)  NOT NULL,
    version         BIGINT        NOT NULL DEFAULT 0,
    PRIMARY KEY (checklist_id),
    INDEX idx_checklist_projeto (projeto_id),
    INDEX idx_checklist_nome (nome),
    CONSTRAINT fk_checklist_projeto FOREIGN KEY (projeto_id) REFERENCES projeto (projeto_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -----------------------------------------------------------------------------
-- item_checklist
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS item_checklist (
    item_checklist_id INT           NOT NULL AUTO_INCREMENT,
    checklist_id      INT,
    descricao         VARCHAR(500),
    concluido         TINYINT(1)    NOT NULL DEFAULT 0,
    created_at        DATETIME(6)   NOT NULL,
    updated_at        DATETIME(6),
    ativo             TINYINT(1)    NOT NULL DEFAULT 1,
    version           BIGINT,
    PRIMARY KEY (item_checklist_id),
    CONSTRAINT fk_ic_checklist FOREIGN KEY (checklist_id) REFERENCES checklist (checklist_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -----------------------------------------------------------------------------
-- material_utilizado
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS material_utilizado (
    material_utilizado_id INT           NOT NULL AUTO_INCREMENT,
    obra_id               INT,
    material_id           INT,
    quantidade            DECIMAL(15,3),
    created_at            DATETIME(6)   NOT NULL,
    updated_at            DATETIME(6),
    ativo                 TINYINT(1)    NOT NULL DEFAULT 1,
    version               BIGINT,
    PRIMARY KEY (material_utilizado_id),
    CONSTRAINT fk_mu_obra     FOREIGN KEY (obra_id)     REFERENCES obra     (obra_id),
    CONSTRAINT fk_mu_material FOREIGN KEY (material_id) REFERENCES material (material_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -----------------------------------------------------------------------------
-- requisicao_material
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS requisicao_material (
    requisicao_material_id INT           NOT NULL AUTO_INCREMENT,
    obra_id                INT,
    material_id            INT,
    quantidade             DECIMAL(15,3),
    status                 VARCHAR(50),
    created_at             DATETIME(6)   NOT NULL,
    updated_at             DATETIME(6),
    ativo                  TINYINT(1)    NOT NULL DEFAULT 1,
    version                BIGINT,
    PRIMARY KEY (requisicao_material_id),
    CONSTRAINT fk_rm_obra     FOREIGN KEY (obra_id)     REFERENCES obra     (obra_id),
    CONSTRAINT fk_rm_material FOREIGN KEY (material_id) REFERENCES material (material_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -----------------------------------------------------------------------------
-- equipamento_projeto
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS equipamento_projeto (
    equipamento_projeto_id INT NOT NULL AUTO_INCREMENT,
    equipamento_id         INT NOT NULL,
    projeto_id             INT NOT NULL,
    data_inicio            DATE,
    data_fim               DATE,
    created_at             DATETIME(6) NOT NULL,
    updated_at             DATETIME(6),
    ativo                  TINYINT(1)  NOT NULL DEFAULT 1,
    version                BIGINT,
    PRIMARY KEY (equipamento_projeto_id),
    CONSTRAINT fk_ep_equipamento FOREIGN KEY (equipamento_id) REFERENCES equipamento (equipamento_id),
    CONSTRAINT fk_ep_projeto     FOREIGN KEY (projeto_id)     REFERENCES projeto     (projeto_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -----------------------------------------------------------------------------
-- documento
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS documento (
    documento_id      INT           NOT NULL AUTO_INCREMENT,
    nome              VARCHAR(255)  NOT NULL,
    caminho           VARCHAR(500),
    tipo_documento_id INT,
    projeto_id        INT,
    usuario_id        INT,
    created_at        DATETIME(6)   NOT NULL,
    updated_at        DATETIME(6),
    ativo             TINYINT(1)    NOT NULL DEFAULT 1,
    version           BIGINT,
    PRIMARY KEY (documento_id),
    CONSTRAINT fk_doc_tipo    FOREIGN KEY (tipo_documento_id) REFERENCES tipo_documento (tipo_documento_id),
    CONSTRAINT fk_doc_projeto FOREIGN KEY (projeto_id)        REFERENCES projeto        (projeto_id),
    CONSTRAINT fk_doc_usuario FOREIGN KEY (usuario_id)        REFERENCES usuario        (usuario_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -----------------------------------------------------------------------------
-- anexo
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS anexo (
    anexo_id     INT           NOT NULL AUTO_INCREMENT,
    nome         VARCHAR(255)  NOT NULL,
    caminho      VARCHAR(500),
    tamanho      BIGINT,
    tipo_mime    VARCHAR(100),
    documento_id INT,
    created_at   DATETIME(6)   NOT NULL,
    updated_at   DATETIME(6),
    ativo        TINYINT(1)    NOT NULL DEFAULT 1,
    version      BIGINT,
    PRIMARY KEY (anexo_id),
    CONSTRAINT fk_anexo_documento FOREIGN KEY (documento_id) REFERENCES documento (documento_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -----------------------------------------------------------------------------
-- assinatura_digital
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS assinatura_digital (
    assinatura_digital_id INT           NOT NULL AUTO_INCREMENT,
    documento_id          INT,
    usuario_id            INT,
    hash                  VARCHAR(500),
    data_assinatura       DATETIME(6),
    created_at            DATETIME(6)   NOT NULL,
    updated_at            DATETIME(6),
    ativo                 TINYINT(1)    NOT NULL DEFAULT 1,
    version               BIGINT,
    PRIMARY KEY (assinatura_digital_id),
    CONSTRAINT fk_assin_documento FOREIGN KEY (documento_id) REFERENCES documento (documento_id),
    CONSTRAINT fk_assin_usuario   FOREIGN KEY (usuario_id)   REFERENCES usuario   (usuario_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -----------------------------------------------------------------------------
-- notificacao
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS notificacao (
    notificacao_id INT           NOT NULL AUTO_INCREMENT,
    usuario_id     INT,
    mensagem       TEXT,
    lida           TINYINT(1)    NOT NULL DEFAULT 0,
    created_at     DATETIME(6)   NOT NULL,
    updated_at     DATETIME(6),
    ativo          TINYINT(1)    NOT NULL DEFAULT 1,
    version        BIGINT,
    PRIMARY KEY (notificacao_id),
    CONSTRAINT fk_notif_usuario FOREIGN KEY (usuario_id) REFERENCES usuario (usuario_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -----------------------------------------------------------------------------
-- lembrete
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS lembrete (
    lembrete_id  INT           NOT NULL AUTO_INCREMENT,
    usuario_id   INT,
    descricao    TEXT,
    data_lembrete DATETIME(6),
    concluido    TINYINT(1)    NOT NULL DEFAULT 0,
    created_at   DATETIME(6)   NOT NULL,
    updated_at   DATETIME(6),
    ativo        TINYINT(1)    NOT NULL DEFAULT 1,
    version      BIGINT,
    PRIMARY KEY (lembrete_id),
    CONSTRAINT fk_lembrete_usuario FOREIGN KEY (usuario_id) REFERENCES usuario (usuario_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -----------------------------------------------------------------------------
-- auditoria
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS auditoria (
    auditoria_id INT           NOT NULL AUTO_INCREMENT,
    usuario_id   INT,
    acao         VARCHAR(100),
    entidade     VARCHAR(100),
    entidade_id  INT,
    detalhe      TEXT,
    created_at   DATETIME(6)   NOT NULL,
    updated_at   DATETIME(6),
    ativo        TINYINT(1)    NOT NULL DEFAULT 1,
    version      BIGINT,
    PRIMARY KEY (auditoria_id),
    CONSTRAINT fk_auditoria_usuario FOREIGN KEY (usuario_id) REFERENCES usuario (usuario_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -----------------------------------------------------------------------------
-- log_alteracao
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS log_alteracao (
    log_alteracao_id INT           NOT NULL AUTO_INCREMENT,
    usuario_id       INT,
    entidade         VARCHAR(100),
    entidade_id      INT,
    campo            VARCHAR(100),
    valor_anterior   TEXT,
    valor_novo       TEXT,
    created_at       DATETIME(6)   NOT NULL,
    updated_at       DATETIME(6),
    ativo            TINYINT(1)    NOT NULL DEFAULT 1,
    version          BIGINT,
    PRIMARY KEY (log_alteracao_id),
    CONSTRAINT fk_log_usuario FOREIGN KEY (usuario_id) REFERENCES usuario (usuario_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -----------------------------------------------------------------------------
-- mensagem_instantanea
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS mensagem_instantanea (
    mensagem_instantanea_id INT           NOT NULL AUTO_INCREMENT,
    remetente_id            INT,
    destinatario_id         INT,
    conteudo                TEXT,
    lida                    TINYINT(1)    NOT NULL DEFAULT 0,
    created_at              DATETIME(6)   NOT NULL,
    updated_at              DATETIME(6),
    ativo                   TINYINT(1)    NOT NULL DEFAULT 1,
    version                 BIGINT,
    PRIMARY KEY (mensagem_instantanea_id),
    CONSTRAINT fk_msg_remetente     FOREIGN KEY (remetente_id)     REFERENCES usuario (usuario_id),
    CONSTRAINT fk_msg_destinatario  FOREIGN KEY (destinatario_id)  REFERENCES usuario (usuario_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -----------------------------------------------------------------------------
-- comunicacao
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS comunicacao (
    comunicacao_id INT           NOT NULL AUTO_INCREMENT,
    projeto_id     INT,
    usuario_id     INT,
    assunto        VARCHAR(255),
    conteudo       TEXT,
    created_at     DATETIME(6)   NOT NULL,
    updated_at     DATETIME(6),
    ativo          TINYINT(1)    NOT NULL DEFAULT 1,
    version        BIGINT,
    PRIMARY KEY (comunicacao_id),
    CONSTRAINT fk_com_projeto FOREIGN KEY (projeto_id) REFERENCES projeto  (projeto_id),
    CONSTRAINT fk_com_usuario FOREIGN KEY (usuario_id) REFERENCES usuario  (usuario_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -----------------------------------------------------------------------------
-- feedback
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS feedback (
    feedback_id INT           NOT NULL AUTO_INCREMENT,
    projeto_id  INT,
    usuario_id  INT,
    comentario  TEXT,
    nota        INT,
    created_at  DATETIME(6)   NOT NULL,
    updated_at  DATETIME(6),
    ativo       TINYINT(1)    NOT NULL DEFAULT 1,
    version     BIGINT,
    PRIMARY KEY (feedback_id),
    CONSTRAINT fk_fb_projeto FOREIGN KEY (projeto_id) REFERENCES projeto (projeto_id),
    CONSTRAINT fk_fb_usuario FOREIGN KEY (usuario_id) REFERENCES usuario (usuario_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -----------------------------------------------------------------------------
-- avaliacao
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS avaliacao (
    avaliacao_id    INT           NOT NULL AUTO_INCREMENT,
    projeto_id      INT,
    avaliador_id    INT,
    avaliado_id     INT,
    nota            INT,
    comentario      TEXT,
    created_at      DATETIME(6)   NOT NULL,
    updated_at      DATETIME(6),
    ativo           TINYINT(1)    NOT NULL DEFAULT 1,
    version         BIGINT,
    PRIMARY KEY (avaliacao_id),
    CONSTRAINT fk_aval_projeto   FOREIGN KEY (projeto_id)   REFERENCES projeto  (projeto_id),
    CONSTRAINT fk_aval_avaliador FOREIGN KEY (avaliador_id) REFERENCES usuario  (usuario_id),
    CONSTRAINT fk_aval_avaliado  FOREIGN KEY (avaliado_id)  REFERENCES usuario  (usuario_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -----------------------------------------------------------------------------
-- relatorio
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS relatorio (
    relatorio_id INT           NOT NULL AUTO_INCREMENT,
    projeto_id   INT,
    tipo         VARCHAR(50),
    caminho      VARCHAR(500),
    created_at   DATETIME(6)   NOT NULL,
    updated_at   DATETIME(6),
    ativo        TINYINT(1)    NOT NULL DEFAULT 1,
    version      BIGINT,
    PRIMARY KEY (relatorio_id),
    CONSTRAINT fk_rel_projeto FOREIGN KEY (projeto_id) REFERENCES projeto (projeto_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -----------------------------------------------------------------------------
-- solicitacao_mudanca
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS solicitacao_mudanca (
    solicitacao_mudanca_id INT           NOT NULL AUTO_INCREMENT,
    projeto_id             INT,
    solicitante_id         INT,
    descricao              TEXT,
    status                 VARCHAR(50),
    created_at             DATETIME(6)   NOT NULL,
    updated_at             DATETIME(6),
    ativo                  TINYINT(1)    NOT NULL DEFAULT 1,
    version                BIGINT,
    PRIMARY KEY (solicitacao_mudanca_id),
    CONSTRAINT fk_sm_projeto     FOREIGN KEY (projeto_id)     REFERENCES projeto (projeto_id),
    CONSTRAINT fk_sm_solicitante FOREIGN KEY (solicitante_id) REFERENCES usuario (usuario_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -----------------------------------------------------------------------------
-- inspecao
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS inspecao (
    inspecao_id INT           NOT NULL AUTO_INCREMENT,
    obra_id     INT,
    responsavel VARCHAR(255),
    resultado   TEXT,
    data        DATE,
    created_at  DATETIME(6)   NOT NULL,
    updated_at  DATETIME(6),
    ativo       TINYINT(1)    NOT NULL DEFAULT 1,
    version     BIGINT,
    PRIMARY KEY (inspecao_id),
    CONSTRAINT fk_insp_obra FOREIGN KEY (obra_id) REFERENCES obra (obra_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -----------------------------------------------------------------------------
-- historico_localizacao
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS historico_localizacao (
    historico_localizacao_id INT           NOT NULL AUTO_INCREMENT,
    obra_id                  INT,
    latitude                 DECIMAL(10,7),
    longitude                DECIMAL(10,7),
    capturado_em             DATETIME(6),
    created_at               DATETIME(6)   NOT NULL,
    updated_at               DATETIME(6),
    ativo                    TINYINT(1)    NOT NULL DEFAULT 1,
    version                  BIGINT,
    PRIMARY KEY (historico_localizacao_id),
    CONSTRAINT fk_hl_obra FOREIGN KEY (obra_id) REFERENCES obra (obra_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -----------------------------------------------------------------------------
-- imagem_projeto
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS imagem_projeto (
    imagem_projeto_id INT           NOT NULL AUTO_INCREMENT,
    projeto_id        INT,
    caminho           VARCHAR(500),
    created_at        DATETIME(6)   NOT NULL,
    updated_at        DATETIME(6),
    ativo             TINYINT(1)    NOT NULL DEFAULT 1,
    version           BIGINT,
    PRIMARY KEY (imagem_projeto_id),
    CONSTRAINT fk_img_projeto FOREIGN KEY (projeto_id) REFERENCES projeto (projeto_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -----------------------------------------------------------------------------
-- termo_garantia
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS termo_garantia (
    termo_garantia_id INT           NOT NULL AUTO_INCREMENT,
    projeto_id        INT,
    descricao         TEXT,
    data_inicio       DATE,
    data_fim          DATE,
    created_at        DATETIME(6)   NOT NULL,
    updated_at        DATETIME(6),
    ativo             TINYINT(1)    NOT NULL DEFAULT 1,
    version           BIGINT,
    PRIMARY KEY (termo_garantia_id),
    CONSTRAINT fk_tg_projeto FOREIGN KEY (projeto_id) REFERENCES projeto (projeto_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -----------------------------------------------------------------------------
-- permissao_usuario
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS permissao_usuario (
    permissao_usuario_id INT           NOT NULL AUTO_INCREMENT,
    usuario_id           INT,
    permissao            VARCHAR(100),
    created_at           DATETIME(6)   NOT NULL,
    updated_at           DATETIME(6),
    ativo                TINYINT(1)    NOT NULL DEFAULT 1,
    version              BIGINT,
    PRIMARY KEY (permissao_usuario_id),
    CONSTRAINT fk_pu_usuario FOREIGN KEY (usuario_id) REFERENCES usuario (usuario_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

SET FOREIGN_KEY_CHECKS = 1;
