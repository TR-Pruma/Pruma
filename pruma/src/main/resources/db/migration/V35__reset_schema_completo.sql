-- =============================================================================
-- V35: RESET TOTAL DO SCHEMA
-- Dropa todas as tabelas (ordem inversa de dependencia) e recria do zero,
-- 100% alinhado com as entidades Java do branch feature/novos_services.
-- Seguro porque nao ha dados nas tabelas de desenvolvimento.
-- =============================================================================

SET FOREIGN_KEY_CHECKS = 0;

-- -----------------------------------------------------------------------
-- DROP todas as tabelas (novas e antigas)
-- -----------------------------------------------------------------------
DROP TABLE IF EXISTS insumo_fornecedor_aux;
DROP TABLE IF EXISTS insumo_fornecedor;
DROP TABLE IF EXISTS comunicacao_aux;
DROP TABLE IF EXISTS equipamento_projeto_aux;
DROP TABLE IF EXISTS mensagem_instantanea_aux;
DROP TABLE IF EXISTS log_alteracao_aux;
DROP TABLE IF EXISTS interacao_campo_mor;
DROP TABLE IF EXISTS historico_score_tfe;
DROP TABLE IF EXISTS metricas_comportamento_operacional;
DROP TABLE IF EXISTS metricas_desempenho_tecnico;
DROP TABLE IF EXISTS score_tfe;
DROP TABLE IF EXISTS apadrinhamento_rede;
DROP TABLE IF EXISTS trajetoria_laboral;
DROP TABLE IF EXISTS proposta_credito;
DROP TABLE IF EXISTS parcela_credito;
DROP TABLE IF EXISTS fatiamento_pagamento;
DROP TABLE IF EXISTS item_pedido_marketplace;
DROP TABLE IF EXISTS pedido_marketplace;
DROP TABLE IF EXISTS lojista_parceiro;
DROP TABLE IF EXISTS profissional_especialidade;
DROP TABLE IF EXISTS especialidade;
DROP TABLE IF EXISTS projeto_categoria;
DROP TABLE IF EXISTS projeto_profissional;
DROP TABLE IF EXISTS comunicacao;
DROP TABLE IF EXISTS cronograma;
DROP TABLE IF EXISTS fase_cronograma;
DROP TABLE IF EXISTS atividade;
DROP TABLE IF EXISTS tarefa;
DROP TABLE IF EXISTS item_checklist;
DROP TABLE IF EXISTS checklist;
DROP TABLE IF EXISTS material_utilizado;
DROP TABLE IF EXISTS requisicao_material;
DROP TABLE IF EXISTS equipamento_projeto;
DROP TABLE IF EXISTS inspecao;
DROP TABLE IF EXISTS historico_localizacao;
DROP TABLE IF EXISTS imagem_projeto;
DROP TABLE IF EXISTS assinatura_digital;
DROP TABLE IF EXISTS anexo;
DROP TABLE IF EXISTS documento;
DROP TABLE IF EXISTS solicitacao_mudanca;
DROP TABLE IF EXISTS avaliacao;
DROP TABLE IF EXISTS feedback;
DROP TABLE IF EXISTS pagamento;
DROP TABLE IF EXISTS item_orcamento;
DROP TABLE IF EXISTS orcamento;
DROP TABLE IF EXISTS sub_contrato;
DROP TABLE IF EXISTS termo_garantia;
DROP TABLE IF EXISTS relatorio;
DROP TABLE IF EXISTS log_alteracao;
DROP TABLE IF EXISTS auditoria;
DROP TABLE IF EXISTS notificacao;
DROP TABLE IF EXISTS lembrete;
DROP TABLE IF EXISTS mensagem_instantanea;
DROP TABLE IF EXISTS permissao_usuario;
DROP TABLE IF EXISTS pre_obra;
DROP TABLE IF EXISTS pos_obra;
DROP TABLE IF EXISTS obra;
DROP TABLE IF EXISTS projeto;
DROP TABLE IF EXISTS profissional_de_base;
DROP TABLE IF EXISTS tecnico_de_obras;
DROP TABLE IF EXISTS cliente;
DROP TABLE IF EXISTS endereco;
DROP TABLE IF EXISTS material;
DROP TABLE IF EXISTS insumo;
DROP TABLE IF EXISTS equipamento;
DROP TABLE IF EXISTS status_equipamento;
DROP TABLE IF EXISTS categoria;
DROP TABLE IF EXISTS fornecedor;
DROP TABLE IF EXISTS tipo_documento;
DROP TABLE IF EXISTS cliente_tipo;
DROP TABLE IF EXISTS logradouro;
DROP TABLE IF EXISTS empresa;
DROP TABLE IF EXISTS tipo_usuario;
DROP TABLE IF EXISTS usuario;

-- =============================================================================
-- RECRIACAO (ordem de dependencia)
-- =============================================================================

CREATE TABLE tipo_usuario (
    tipo_usuario_id INT         NOT NULL AUTO_INCREMENT,
    nome            VARCHAR(50) NOT NULL,
    descricao       VARCHAR(255),
    created_at      DATETIME(6) NOT NULL,
    updated_at      DATETIME(6),
    ativo           TINYINT(1)  NOT NULL DEFAULT 1,
    version         BIGINT,
    PRIMARY KEY (tipo_usuario_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE usuario (
    usuario_id INT          NOT NULL AUTO_INCREMENT,
    cpf        VARCHAR(11)  NOT NULL,
    senha      VARCHAR(255) NOT NULL,
    tipo       VARCHAR(20)  NOT NULL,
    created_at DATETIME(6)  NOT NULL,
    updated_at DATETIME(6),
    ativo      TINYINT(1)   NOT NULL DEFAULT 1,
    version    BIGINT,
    PRIMARY KEY (usuario_id),
    CONSTRAINT uk_usuario_cpf UNIQUE (cpf),
    INDEX idx_usuario_cpf (cpf)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE empresa (
    empresa_id INT          NOT NULL AUTO_INCREMENT,
    cnpj       VARCHAR(14)  NOT NULL,
    nome       VARCHAR(255) NOT NULL,
    email      VARCHAR(255),
    telefone   VARCHAR(20),
    created_at DATETIME(6)  NOT NULL,
    updated_at DATETIME(6),
    ativo      TINYINT(1)   NOT NULL DEFAULT 1,
    version    BIGINT,
    PRIMARY KEY (empresa_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE logradouro (
    logradouro_id INT          NOT NULL AUTO_INCREMENT,
    cep           VARCHAR(8)   NOT NULL,
    logradouro    VARCHAR(255) NOT NULL,
    bairro        VARCHAR(100),
    cidade        VARCHAR(100) NOT NULL,
    estado        VARCHAR(2)   NOT NULL,
    created_at    DATETIME(6)  NOT NULL,
    updated_at    DATETIME(6),
    ativo         TINYINT(1)   NOT NULL DEFAULT 1,
    version       BIGINT,
    PRIMARY KEY (logradouro_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE endereco (
    endereco_id INT          NOT NULL AUTO_INCREMENT,
    cep         VARCHAR(8)   NOT NULL,
    logradouro  VARCHAR(255) NOT NULL,
    numero      VARCHAR(20),
    complemento VARCHAR(255),
    bairro      VARCHAR(100),
    cidade      VARCHAR(100) NOT NULL,
    estado      VARCHAR(2)   NOT NULL,
    created_at  DATETIME(6)  NOT NULL,
    updated_at  DATETIME(6),
    ativo       TINYINT(1)   NOT NULL DEFAULT 1,
    version     BIGINT,
    PRIMARY KEY (endereco_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE cliente_tipo (
    cliente_tipo_id INT          NOT NULL AUTO_INCREMENT,
    descricao       VARCHAR(100) NOT NULL,
    created_at      DATETIME(6)  NOT NULL,
    updated_at      DATETIME(6),
    ativo           TINYINT(1)   NOT NULL DEFAULT 1,
    version         BIGINT,
    PRIMARY KEY (cliente_tipo_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE cliente (
    cliente_id  INT          NOT NULL AUTO_INCREMENT,
    cpf         VARCHAR(11),
    cnpj        VARCHAR(14),
    nome        VARCHAR(255) NOT NULL,
    email       VARCHAR(255),
    telefone    VARCHAR(20),
    endereco_id INT,
    created_at  DATETIME(6)  NOT NULL,
    updated_at  DATETIME(6),
    ativo       TINYINT(1)   NOT NULL DEFAULT 1,
    version     BIGINT,
    PRIMARY KEY (cliente_id),
    CONSTRAINT fk_cliente_endereco FOREIGN KEY (endereco_id) REFERENCES endereco (endereco_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE categoria (
    categoria_id INT          NOT NULL AUTO_INCREMENT,
    nome         VARCHAR(100) NOT NULL,
    descricao    VARCHAR(255),
    created_at   DATETIME(6)  NOT NULL,
    updated_at   DATETIME(6),
    ativo        TINYINT(1)   NOT NULL DEFAULT 1,
    version      BIGINT,
    PRIMARY KEY (categoria_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE fornecedor (
    fornecedor_id INT          NOT NULL AUTO_INCREMENT,
    cnpj          VARCHAR(14),
    cpf           VARCHAR(11),
    nome          VARCHAR(255) NOT NULL,
    email         VARCHAR(255),
    telefone      VARCHAR(20),
    created_at    DATETIME(6)  NOT NULL,
    updated_at    DATETIME(6),
    ativo         TINYINT(1)   NOT NULL DEFAULT 1,
    version       BIGINT,
    PRIMARY KEY (fornecedor_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE equipamento (
    equipamento_id INT          NOT NULL AUTO_INCREMENT,
    nome           VARCHAR(255) NOT NULL,
    descricao      VARCHAR(500),
    created_at     DATETIME(6)  NOT NULL,
    updated_at     DATETIME(6),
    ativo          TINYINT(1)   NOT NULL DEFAULT 1,
    version        BIGINT,
    PRIMARY KEY (equipamento_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE status_equipamento (
    status_equipamento_id INT          NOT NULL AUTO_INCREMENT,
    descricao             VARCHAR(100) NOT NULL,
    created_at            DATETIME(6)  NOT NULL,
    updated_at            DATETIME(6),
    ativo                 TINYINT(1)   NOT NULL DEFAULT 1,
    version               BIGINT,
    PRIMARY KEY (status_equipamento_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE material (
    material_id    INT            NOT NULL AUTO_INCREMENT,
    nome           VARCHAR(255)   NOT NULL,
    descricao      VARCHAR(500),
    unidade        VARCHAR(20),
    preco_unitario DECIMAL(15,2),
    estoque        DECIMAL(15,3),
    categoria_id   INT,
    created_at     DATETIME(6)    NOT NULL,
    updated_at     DATETIME(6),
    ativo          TINYINT(1)     NOT NULL DEFAULT 1,
    version        BIGINT,
    PRIMARY KEY (material_id),
    CONSTRAINT fk_material_categoria FOREIGN KEY (categoria_id) REFERENCES categoria (categoria_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE insumo (
    insumo_id      INT            NOT NULL AUTO_INCREMENT,
    nome           VARCHAR(255)   NOT NULL,
    descricao      VARCHAR(500),
    unidade        VARCHAR(20),
    preco_unitario DECIMAL(15,2),
    categoria_id   INT,
    created_at     DATETIME(6)    NOT NULL,
    updated_at     DATETIME(6),
    ativo          TINYINT(1)     NOT NULL DEFAULT 1,
    version        BIGINT,
    PRIMARY KEY (insumo_id),
    CONSTRAINT fk_insumo_categoria FOREIGN KEY (categoria_id) REFERENCES categoria (categoria_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE insumo_fornecedor (
    insumo_id     INT NOT NULL,
    fornecedor_id INT NOT NULL,
    PRIMARY KEY (insumo_id, fornecedor_id),
    CONSTRAINT fk_if_insumo     FOREIGN KEY (insumo_id)     REFERENCES insumo     (insumo_id),
    CONSTRAINT fk_if_fornecedor FOREIGN KEY (fornecedor_id) REFERENCES fornecedor (fornecedor_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Nova tabela: insumo_fornecedor_aux (InsumoFornecedorAux.java - PK composta)
CREATE TABLE insumo_fornecedor_aux (
    insumo_id         INT          NOT NULL,
    fornecedor_id     INT          NOT NULL,
    preco_negociado   DECIMAL(15,2),
    prazo_entrega_dias INT,
    observacoes       VARCHAR(500),
    created_at        DATETIME(6)  NOT NULL,
    updated_at        DATETIME(6),
    ativo             TINYINT(1)   NOT NULL DEFAULT 1,
    version           BIGINT,
    PRIMARY KEY (insumo_id, fornecedor_id),
    CONSTRAINT fk_ifa_insumo     FOREIGN KEY (insumo_id)     REFERENCES insumo     (insumo_id),
    CONSTRAINT fk_ifa_fornecedor FOREIGN KEY (fornecedor_id) REFERENCES fornecedor (fornecedor_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE tipo_documento (
    tipo_documento_id INT          NOT NULL AUTO_INCREMENT,
    descricao         VARCHAR(100) NOT NULL,
    created_at        DATETIME(6)  NOT NULL,
    updated_at        DATETIME(6),
    ativo             TINYINT(1)   NOT NULL DEFAULT 1,
    version           BIGINT,
    PRIMARY KEY (tipo_documento_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE especialidade (
    especialidade_id INT          NOT NULL AUTO_INCREMENT,
    nome             VARCHAR(100) NOT NULL,
    created_at       DATETIME(6)  NOT NULL,
    updated_at       DATETIME(6),
    ativo            TINYINT(1)   NOT NULL DEFAULT 1,
    version          BIGINT,
    PRIMARY KEY (especialidade_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE profissional_de_base (
    profissional_id INT          NOT NULL AUTO_INCREMENT,
    nome            VARCHAR(255) NOT NULL,
    cpf             VARCHAR(11),
    email           VARCHAR(255),
    telefone        VARCHAR(20),
    especialidade   VARCHAR(100),
    usuario_id      INT,
    created_at      DATETIME(6)  NOT NULL,
    updated_at      DATETIME(6),
    ativo           TINYINT(1)   NOT NULL DEFAULT 1,
    version         BIGINT,
    PRIMARY KEY (profissional_id),
    CONSTRAINT fk_prof_usuario FOREIGN KEY (usuario_id) REFERENCES usuario (usuario_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE profissional_especialidade (
    profissional_id  INT NOT NULL,
    especialidade_id INT NOT NULL,
    PRIMARY KEY (profissional_id, especialidade_id),
    CONSTRAINT fk_pe_profissional  FOREIGN KEY (profissional_id)  REFERENCES profissional_de_base (profissional_id),
    CONSTRAINT fk_pe_especialidade FOREIGN KEY (especialidade_id) REFERENCES especialidade        (especialidade_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE tecnico_de_obras (
    tecnico_id INT          NOT NULL AUTO_INCREMENT,
    nome       VARCHAR(255) NOT NULL,
    cpf        VARCHAR(11),
    crea       VARCHAR(20),
    usuario_id INT,
    created_at DATETIME(6)  NOT NULL,
    updated_at DATETIME(6),
    ativo      TINYINT(1)   NOT NULL DEFAULT 1,
    version    BIGINT,
    PRIMARY KEY (tecnico_id),
    CONSTRAINT fk_tecnico_usuario FOREIGN KEY (usuario_id) REFERENCES usuario (usuario_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE projeto (
    projeto_id  INT          NOT NULL AUTO_INCREMENT,
    nome        VARCHAR(255) NOT NULL,
    descricao   TEXT,
    status      VARCHAR(50),
    data_inicio DATE,
    data_fim    DATE,
    cliente_id  INT,
    usuario_id  INT,
    endereco_id INT,
    created_at  DATETIME(6)  NOT NULL,
    updated_at  DATETIME(6),
    ativo       TINYINT(1)   NOT NULL DEFAULT 1,
    version     BIGINT,
    PRIMARY KEY (projeto_id),
    CONSTRAINT fk_projeto_cliente  FOREIGN KEY (cliente_id)  REFERENCES cliente  (cliente_id),
    CONSTRAINT fk_projeto_usuario  FOREIGN KEY (usuario_id)  REFERENCES usuario  (usuario_id),
    CONSTRAINT fk_projeto_endereco FOREIGN KEY (endereco_id) REFERENCES endereco (endereco_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE projeto_categoria (
    projeto_id   INT NOT NULL,
    categoria_id INT NOT NULL,
    PRIMARY KEY (projeto_id, categoria_id),
    CONSTRAINT fk_pc_projeto   FOREIGN KEY (projeto_id)   REFERENCES projeto   (projeto_id),
    CONSTRAINT fk_pc_categoria FOREIGN KEY (categoria_id) REFERENCES categoria (categoria_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE projeto_profissional (
    projeto_profissional_id INT         NOT NULL AUTO_INCREMENT,
    projeto_id              INT         NOT NULL,
    profissional_id         INT         NOT NULL,
    papel                   VARCHAR(50),
    created_at              DATETIME(6) NOT NULL,
    updated_at              DATETIME(6),
    ativo                   TINYINT(1)  NOT NULL DEFAULT 1,
    version                 BIGINT,
    PRIMARY KEY (projeto_profissional_id),
    CONSTRAINT fk_pp_projeto      FOREIGN KEY (projeto_id)      REFERENCES projeto             (projeto_id),
    CONSTRAINT fk_pp_profissional FOREIGN KEY (profissional_id) REFERENCES profissional_de_base (profissional_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE pre_obra (
    pre_obra_id INT         NOT NULL AUTO_INCREMENT,
    projeto_id  INT,
    descricao   TEXT,
    status      VARCHAR(50),
    created_at  DATETIME(6) NOT NULL,
    updated_at  DATETIME(6),
    ativo       TINYINT(1)  NOT NULL DEFAULT 1,
    version     BIGINT,
    PRIMARY KEY (pre_obra_id),
    CONSTRAINT fk_pre_obra_projeto FOREIGN KEY (projeto_id) REFERENCES projeto (projeto_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE obra (
    obra_id     INT         NOT NULL AUTO_INCREMENT,
    projeto_id  INT,
    descricao   TEXT,
    status      VARCHAR(50),
    data_inicio DATE,
    data_fim    DATE,
    created_at  DATETIME(6) NOT NULL,
    updated_at  DATETIME(6),
    ativo       TINYINT(1)  NOT NULL DEFAULT 1,
    version     BIGINT,
    PRIMARY KEY (obra_id),
    CONSTRAINT fk_obra_projeto FOREIGN KEY (projeto_id) REFERENCES projeto (projeto_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE pos_obra (
    pos_obra_id INT         NOT NULL AUTO_INCREMENT,
    obra_id     INT,
    descricao   TEXT,
    created_at  DATETIME(6) NOT NULL,
    updated_at  DATETIME(6),
    ativo       TINYINT(1)  NOT NULL DEFAULT 1,
    version     BIGINT,
    PRIMARY KEY (pos_obra_id),
    CONSTRAINT fk_pos_obra_obra FOREIGN KEY (obra_id) REFERENCES obra (obra_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE orcamento (
    orcamento_id INT            NOT NULL AUTO_INCREMENT,
    projeto_id   INT,
    valor_total  DECIMAL(15,2),
    status       VARCHAR(50),
    data_emissao DATE,
    created_at   DATETIME(6)    NOT NULL,
    updated_at   DATETIME(6),
    ativo        TINYINT(1)     NOT NULL DEFAULT 1,
    version      BIGINT,
    PRIMARY KEY (orcamento_id),
    CONSTRAINT fk_orcamento_projeto FOREIGN KEY (projeto_id) REFERENCES projeto (projeto_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE item_orcamento (
    item_orcamento_id INT            NOT NULL AUTO_INCREMENT,
    orcamento_id      INT,
    descricao         VARCHAR(500),
    quantidade        DECIMAL(15,3),
    preco_unitario    DECIMAL(15,2),
    valor_total       DECIMAL(15,2),
    created_at        DATETIME(6)    NOT NULL,
    updated_at        DATETIME(6),
    ativo             TINYINT(1)     NOT NULL DEFAULT 1,
    version           BIGINT,
    PRIMARY KEY (item_orcamento_id),
    CONSTRAINT fk_item_orc_orcamento FOREIGN KEY (orcamento_id) REFERENCES orcamento (orcamento_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE pagamento (
    pagamento_id   INT            NOT NULL AUTO_INCREMENT,
    projeto_id     INT,
    valor          DECIMAL(15,2),
    status         VARCHAR(50),
    data_pagamento DATE,
    created_at     DATETIME(6)    NOT NULL,
    updated_at     DATETIME(6),
    ativo          TINYINT(1)     NOT NULL DEFAULT 1,
    version        BIGINT,
    PRIMARY KEY (pagamento_id),
    CONSTRAINT fk_pagamento_projeto FOREIGN KEY (projeto_id) REFERENCES projeto (projeto_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE sub_contrato (
    sub_contrato_id INT            NOT NULL AUTO_INCREMENT,
    projeto_id      INT,
    fornecedor_id   INT,
    valor           DECIMAL(15,2),
    status          VARCHAR(50),
    created_at      DATETIME(6)    NOT NULL,
    updated_at      DATETIME(6),
    ativo           TINYINT(1)     NOT NULL DEFAULT 1,
    version         BIGINT,
    PRIMARY KEY (sub_contrato_id),
    CONSTRAINT fk_sc_projeto    FOREIGN KEY (projeto_id)   REFERENCES projeto    (projeto_id),
    CONSTRAINT fk_sc_fornecedor FOREIGN KEY (fornecedor_id) REFERENCES fornecedor (fornecedor_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- cronograma - alinhado com Cronograma.java
CREATE TABLE cronograma (
    cronograma_id INT          NOT NULL AUTO_INCREMENT,
    nome          VARCHAR(255) NOT NULL,
    projeto_id    INT          NOT NULL,
    data_inicio   DATE         NOT NULL,
    data_fim      DATE         NOT NULL,
    created_at    DATETIME(6)  NOT NULL,
    updated_at    DATETIME(6),
    ativo         TINYINT(1)   NOT NULL DEFAULT 1,
    version       BIGINT,
    PRIMARY KEY (cronograma_id),
    CONSTRAINT uk_cronograma_projeto_periodo UNIQUE (projeto_id, data_inicio, data_fim),
    INDEX idx_cronograma_projeto (projeto_id),
    INDEX idx_cronograma_periodo (data_inicio, data_fim),
    CONSTRAINT fk_cronograma_projeto FOREIGN KEY (projeto_id) REFERENCES projeto (projeto_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE fase_cronograma (
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

CREATE TABLE atividade (
    atividade_id INT          NOT NULL AUTO_INCREMENT,
    obra_id      INT,
    descricao    VARCHAR(500),
    status       VARCHAR(50),
    responsavel  VARCHAR(255),
    data_inicio  DATE,
    data_fim     DATE,
    created_at   DATETIME(6)  NOT NULL,
    updated_at   DATETIME(6),
    ativo        TINYINT(1)   NOT NULL DEFAULT 1,
    version      BIGINT,
    PRIMARY KEY (atividade_id),
    CONSTRAINT fk_atividade_obra FOREIGN KEY (obra_id) REFERENCES obra (obra_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE tarefa (
    tarefa_id    INT         NOT NULL AUTO_INCREMENT,
    atividade_id INT,
    descricao    VARCHAR(500),
    status       VARCHAR(50),
    created_at   DATETIME(6) NOT NULL,
    updated_at   DATETIME(6),
    ativo        TINYINT(1)  NOT NULL DEFAULT 1,
    version      BIGINT,
    PRIMARY KEY (tarefa_id),
    CONSTRAINT fk_tarefa_atividade FOREIGN KEY (atividade_id) REFERENCES atividade (atividade_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE checklist (
    checklist_id     INT         NOT NULL AUTO_INCREMENT,
    projeto_id       INT         NOT NULL,
    nome             VARCHAR(50) NOT NULL,
    ativo            TINYINT(1)  NOT NULL DEFAULT 1,
    data_criacao     DATETIME(6) NOT NULL,
    data_atualizacao DATETIME(6) NOT NULL,
    version          BIGINT      NOT NULL DEFAULT 0,
    PRIMARY KEY (checklist_id),
    INDEX idx_checklist_projeto (projeto_id),
    INDEX idx_checklist_nome    (nome),
    CONSTRAINT fk_checklist_projeto FOREIGN KEY (projeto_id) REFERENCES projeto (projeto_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE item_checklist (
    item_checklist_id INT         NOT NULL AUTO_INCREMENT,
    checklist_id      INT,
    descricao         VARCHAR(500),
    concluido         TINYINT(1)  NOT NULL DEFAULT 0,
    created_at        DATETIME(6) NOT NULL,
    updated_at        DATETIME(6),
    ativo             TINYINT(1)  NOT NULL DEFAULT 1,
    version           BIGINT,
    PRIMARY KEY (item_checklist_id),
    CONSTRAINT fk_ic_checklist FOREIGN KEY (checklist_id) REFERENCES checklist (checklist_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE material_utilizado (
    material_utilizado_id INT            NOT NULL AUTO_INCREMENT,
    obra_id               INT,
    material_id           INT,
    quantidade            DECIMAL(15,3),
    created_at            DATETIME(6)    NOT NULL,
    updated_at            DATETIME(6),
    ativo                 TINYINT(1)     NOT NULL DEFAULT 1,
    version               BIGINT,
    PRIMARY KEY (material_utilizado_id),
    CONSTRAINT fk_mu_obra     FOREIGN KEY (obra_id)     REFERENCES obra     (obra_id),
    CONSTRAINT fk_mu_material FOREIGN KEY (material_id) REFERENCES material (material_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE requisicao_material (
    requisicao_material_id INT            NOT NULL AUTO_INCREMENT,
    obra_id                INT,
    material_id            INT,
    quantidade             DECIMAL(15,3),
    status                 VARCHAR(50),
    created_at             DATETIME(6)    NOT NULL,
    updated_at             DATETIME(6),
    ativo                  TINYINT(1)     NOT NULL DEFAULT 1,
    version                BIGINT,
    PRIMARY KEY (requisicao_material_id),
    CONSTRAINT fk_rm_obra     FOREIGN KEY (obra_id)     REFERENCES obra     (obra_id),
    CONSTRAINT fk_rm_material FOREIGN KEY (material_id) REFERENCES material (material_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE equipamento_projeto (
    equipamento_projeto_id INT         NOT NULL AUTO_INCREMENT,
    equipamento_id         INT         NOT NULL,
    projeto_id             INT         NOT NULL,
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

-- Nova tabela: equipamento_projeto_aux (EquipamentoProjetoAux.java)
CREATE TABLE equipamento_projeto_aux (
    equipamento_projeto_id INT          NOT NULL,
    tipo_uso               VARCHAR(50),
    observacoes            VARCHAR(500),
    created_at             DATETIME(6)  NOT NULL,
    updated_at             DATETIME(6),
    ativo                  TINYINT(1)   NOT NULL DEFAULT 1,
    version                BIGINT,
    PRIMARY KEY (equipamento_projeto_id),
    CONSTRAINT fk_epa_ep FOREIGN KEY (equipamento_projeto_id) REFERENCES equipamento_projeto (equipamento_projeto_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE documento (
    documento_id      INT          NOT NULL AUTO_INCREMENT,
    nome              VARCHAR(255) NOT NULL,
    caminho           VARCHAR(500),
    tipo_documento_id INT,
    projeto_id        INT,
    usuario_id        INT,
    created_at        DATETIME(6)  NOT NULL,
    updated_at        DATETIME(6),
    ativo             TINYINT(1)   NOT NULL DEFAULT 1,
    version           BIGINT,
    PRIMARY KEY (documento_id),
    CONSTRAINT fk_doc_tipo    FOREIGN KEY (tipo_documento_id) REFERENCES tipo_documento (tipo_documento_id),
    CONSTRAINT fk_doc_projeto FOREIGN KEY (projeto_id)        REFERENCES projeto        (projeto_id),
    CONSTRAINT fk_doc_usuario FOREIGN KEY (usuario_id)        REFERENCES usuario        (usuario_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE anexo (
    anexo_id     INT          NOT NULL AUTO_INCREMENT,
    nome         VARCHAR(255) NOT NULL,
    caminho      VARCHAR(500),
    tamanho      BIGINT,
    tipo_mime    VARCHAR(100),
    documento_id INT,
    created_at   DATETIME(6)  NOT NULL,
    updated_at   DATETIME(6),
    ativo        TINYINT(1)   NOT NULL DEFAULT 1,
    version      BIGINT,
    PRIMARY KEY (anexo_id),
    CONSTRAINT fk_anexo_documento FOREIGN KEY (documento_id) REFERENCES documento (documento_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE assinatura_digital (
    assinatura_digital_id INT         NOT NULL AUTO_INCREMENT,
    documento_id          INT,
    usuario_id            INT,
    hash                  VARCHAR(500),
    data_assinatura       DATETIME(6),
    created_at            DATETIME(6) NOT NULL,
    updated_at            DATETIME(6),
    ativo                 TINYINT(1)  NOT NULL DEFAULT 1,
    version               BIGINT,
    PRIMARY KEY (assinatura_digital_id),
    CONSTRAINT fk_assin_documento FOREIGN KEY (documento_id) REFERENCES documento (documento_id),
    CONSTRAINT fk_assin_usuario   FOREIGN KEY (usuario_id)   REFERENCES usuario   (usuario_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE notificacao (
    notificacao_id INT         NOT NULL AUTO_INCREMENT,
    usuario_id     INT,
    mensagem       TEXT,
    lida           TINYINT(1)  NOT NULL DEFAULT 0,
    created_at     DATETIME(6) NOT NULL,
    updated_at     DATETIME(6),
    ativo          TINYINT(1)  NOT NULL DEFAULT 1,
    version        BIGINT,
    PRIMARY KEY (notificacao_id),
    CONSTRAINT fk_notif_usuario FOREIGN KEY (usuario_id) REFERENCES usuario (usuario_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE lembrete (
    lembrete_id   INT         NOT NULL AUTO_INCREMENT,
    usuario_id    INT,
    descricao     TEXT,
    data_lembrete DATETIME(6),
    concluido     TINYINT(1)  NOT NULL DEFAULT 0,
    created_at    DATETIME(6) NOT NULL,
    updated_at    DATETIME(6),
    ativo         TINYINT(1)  NOT NULL DEFAULT 1,
    version       BIGINT,
    PRIMARY KEY (lembrete_id),
    CONSTRAINT fk_lembrete_usuario FOREIGN KEY (usuario_id) REFERENCES usuario (usuario_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE auditoria (
    auditoria_id INT          NOT NULL AUTO_INCREMENT,
    usuario_id   INT,
    acao         VARCHAR(100),
    entidade     VARCHAR(100),
    entidade_id  INT,
    detalhe      TEXT,
    created_at   DATETIME(6)  NOT NULL,
    updated_at   DATETIME(6),
    ativo        TINYINT(1)   NOT NULL DEFAULT 1,
    version      BIGINT,
    PRIMARY KEY (auditoria_id),
    CONSTRAINT fk_auditoria_usuario FOREIGN KEY (usuario_id) REFERENCES usuario (usuario_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE log_alteracao (
    log_alteracao_id INT          NOT NULL AUTO_INCREMENT,
    usuario_id       INT,
    entidade         VARCHAR(100),
    entidade_id      INT,
    campo            VARCHAR(100),
    valor_anterior   TEXT,
    valor_novo       TEXT,
    created_at       DATETIME(6)  NOT NULL,
    updated_at       DATETIME(6),
    ativo            TINYINT(1)   NOT NULL DEFAULT 1,
    version          BIGINT,
    PRIMARY KEY (log_alteracao_id),
    CONSTRAINT fk_log_usuario FOREIGN KEY (usuario_id) REFERENCES usuario (usuario_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Nova tabela: log_alteracao_aux (LogAlteracaoAux.java)
CREATE TABLE log_alteracao_aux (
    log_alteracao_id INT         NOT NULL,
    tipo_operacao    VARCHAR(30),
    ip_origem        VARCHAR(45),
    created_at       DATETIME(6) NOT NULL,
    updated_at       DATETIME(6),
    ativo            TINYINT(1)  NOT NULL DEFAULT 1,
    version          BIGINT,
    PRIMARY KEY (log_alteracao_id),
    CONSTRAINT fk_laa_log FOREIGN KEY (log_alteracao_id) REFERENCES log_alteracao (log_alteracao_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE mensagem_instantanea (
    mensagem_instantanea_id INT         NOT NULL AUTO_INCREMENT,
    remetente_id            INT,
    destinatario_id         INT,
    conteudo                TEXT,
    lida                    TINYINT(1)  NOT NULL DEFAULT 0,
    created_at              DATETIME(6) NOT NULL,
    updated_at              DATETIME(6),
    ativo                   TINYINT(1)  NOT NULL DEFAULT 1,
    version                 BIGINT,
    PRIMARY KEY (mensagem_instantanea_id),
    CONSTRAINT fk_msg_remetente    FOREIGN KEY (remetente_id)    REFERENCES usuario (usuario_id),
    CONSTRAINT fk_msg_destinatario FOREIGN KEY (destinatario_id) REFERENCES usuario (usuario_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Nova tabela: mensagem_instantanea_aux (MensagemInstantaneaAux.java)
CREATE TABLE mensagem_instantanea_aux (
    mensagem_instantanea_id INT         NOT NULL,
    tipo_mensagem           VARCHAR(30),
    editada                 TINYINT(1)  NOT NULL DEFAULT 0,
    created_at              DATETIME(6) NOT NULL,
    updated_at              DATETIME(6),
    ativo                   TINYINT(1)  NOT NULL DEFAULT 1,
    version                 BIGINT,
    PRIMARY KEY (mensagem_instantanea_id),
    CONSTRAINT fk_mia_msg FOREIGN KEY (mensagem_instantanea_id) REFERENCES mensagem_instantanea (mensagem_instantanea_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- comunicacao - alinhado com Comunicacao.java (novo modelo)
CREATE TABLE comunicacao (
    comunicacao_id  INT          NOT NULL AUTO_INCREMENT,
    projeto_id      INT          NOT NULL,
    cliente_id      INT          NOT NULL,
    mensagem        TEXT         NOT NULL,
    tipo_remetente  VARCHAR(15)  NOT NULL,
    created_at      DATETIME(6)  NOT NULL,
    updated_at      DATETIME(6),
    ativo           TINYINT(1)   NOT NULL DEFAULT 1,
    version         BIGINT,
    PRIMARY KEY (comunicacao_id),
    INDEX idx_com_projeto   (projeto_id),
    INDEX idx_com_cliente   (cliente_id),
    INDEX idx_com_remetente (tipo_remetente),
    CONSTRAINT fk_com_projeto FOREIGN KEY (projeto_id) REFERENCES projeto (projeto_id),
    CONSTRAINT fk_com_cliente FOREIGN KEY (cliente_id) REFERENCES cliente (cliente_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- comunicacao_aux (ComunicacaoAux.java - @MapsId 1:1)
CREATE TABLE comunicacao_aux (
    comunicacao_id INT         NOT NULL,
    tipo_mensagem  VARCHAR(30) NOT NULL,
    created_at     DATETIME(6) NOT NULL,
    updated_at     DATETIME(6),
    ativo          TINYINT(1)  NOT NULL DEFAULT 1,
    version        BIGINT,
    PRIMARY KEY (comunicacao_id),
    CONSTRAINT fk_ca_comunicacao FOREIGN KEY (comunicacao_id) REFERENCES comunicacao (comunicacao_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE feedback (
    feedback_id INT         NOT NULL AUTO_INCREMENT,
    projeto_id  INT,
    usuario_id  INT,
    comentario  TEXT,
    nota        INT,
    created_at  DATETIME(6) NOT NULL,
    updated_at  DATETIME(6),
    ativo       TINYINT(1)  NOT NULL DEFAULT 1,
    version     BIGINT,
    PRIMARY KEY (feedback_id),
    CONSTRAINT fk_fb_projeto FOREIGN KEY (projeto_id) REFERENCES projeto (projeto_id),
    CONSTRAINT fk_fb_usuario FOREIGN KEY (usuario_id) REFERENCES usuario (usuario_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE avaliacao (
    avaliacao_id INT         NOT NULL AUTO_INCREMENT,
    projeto_id   INT,
    avaliador_id INT,
    avaliado_id  INT,
    nota         INT,
    comentario   TEXT,
    created_at   DATETIME(6) NOT NULL,
    updated_at   DATETIME(6),
    ativo        TINYINT(1)  NOT NULL DEFAULT 1,
    version      BIGINT,
    PRIMARY KEY (avaliacao_id),
    CONSTRAINT fk_aval_projeto   FOREIGN KEY (projeto_id)   REFERENCES projeto (projeto_id),
    CONSTRAINT fk_aval_avaliador FOREIGN KEY (avaliador_id) REFERENCES usuario (usuario_id),
    CONSTRAINT fk_aval_avaliado  FOREIGN KEY (avaliado_id)  REFERENCES usuario (usuario_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE relatorio (
    relatorio_id INT         NOT NULL AUTO_INCREMENT,
    projeto_id   INT,
    tipo         VARCHAR(50),
    caminho      VARCHAR(500),
    created_at   DATETIME(6) NOT NULL,
    updated_at   DATETIME(6),
    ativo        TINYINT(1)  NOT NULL DEFAULT 1,
    version      BIGINT,
    PRIMARY KEY (relatorio_id),
    CONSTRAINT fk_rel_projeto FOREIGN KEY (projeto_id) REFERENCES projeto (projeto_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE solicitacao_mudanca (
    solicitacao_mudanca_id INT         NOT NULL AUTO_INCREMENT,
    projeto_id             INT,
    solicitante_id         INT,
    descricao              TEXT,
    status                 VARCHAR(50),
    created_at             DATETIME(6) NOT NULL,
    updated_at             DATETIME(6),
    ativo                  TINYINT(1)  NOT NULL DEFAULT 1,
    version                BIGINT,
    PRIMARY KEY (solicitacao_mudanca_id),
    CONSTRAINT fk_sm_projeto     FOREIGN KEY (projeto_id)     REFERENCES projeto (projeto_id),
    CONSTRAINT fk_sm_solicitante FOREIGN KEY (solicitante_id) REFERENCES usuario (usuario_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE inspecao (
    inspecao_id INT         NOT NULL AUTO_INCREMENT,
    obra_id     INT,
    responsavel VARCHAR(255),
    resultado   TEXT,
    data        DATE,
    created_at  DATETIME(6) NOT NULL,
    updated_at  DATETIME(6),
    ativo       TINYINT(1)  NOT NULL DEFAULT 1,
    version     BIGINT,
    PRIMARY KEY (inspecao_id),
    CONSTRAINT fk_insp_obra FOREIGN KEY (obra_id) REFERENCES obra (obra_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE historico_localizacao (
    historico_localizacao_id INT            NOT NULL AUTO_INCREMENT,
    obra_id                  INT,
    latitude                 DECIMAL(10,7),
    longitude                DECIMAL(10,7),
    capturado_em             DATETIME(6),
    created_at               DATETIME(6)    NOT NULL,
    updated_at               DATETIME(6),
    ativo                    TINYINT(1)     NOT NULL DEFAULT 1,
    version                  BIGINT,
    PRIMARY KEY (historico_localizacao_id),
    CONSTRAINT fk_hl_obra FOREIGN KEY (obra_id) REFERENCES obra (obra_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE imagem_projeto (
    imagem_projeto_id INT         NOT NULL AUTO_INCREMENT,
    projeto_id        INT,
    caminho           VARCHAR(500),
    created_at        DATETIME(6) NOT NULL,
    updated_at        DATETIME(6),
    ativo             TINYINT(1)  NOT NULL DEFAULT 1,
    version           BIGINT,
    PRIMARY KEY (imagem_projeto_id),
    CONSTRAINT fk_img_projeto FOREIGN KEY (projeto_id) REFERENCES projeto (projeto_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE termo_garantia (
    termo_garantia_id INT         NOT NULL AUTO_INCREMENT,
    projeto_id        INT,
    descricao         TEXT,
    data_inicio       DATE,
    data_fim          DATE,
    created_at        DATETIME(6) NOT NULL,
    updated_at        DATETIME(6),
    ativo             TINYINT(1)  NOT NULL DEFAULT 1,
    version           BIGINT,
    PRIMARY KEY (termo_garantia_id),
    CONSTRAINT fk_tg_projeto FOREIGN KEY (projeto_id) REFERENCES projeto (projeto_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE permissao_usuario (
    permissao_usuario_id INT         NOT NULL AUTO_INCREMENT,
    usuario_id           INT,
    permissao            VARCHAR(100),
    created_at           DATETIME(6) NOT NULL,
    updated_at           DATETIME(6),
    ativo                TINYINT(1)  NOT NULL DEFAULT 1,
    version              BIGINT,
    PRIMARY KEY (permissao_usuario_id),
    CONSTRAINT fk_pu_usuario FOREIGN KEY (usuario_id) REFERENCES usuario (usuario_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =============================================================================
-- TABELAS NOVAS (existem apenas no branch feature/novos_services)
-- =============================================================================

-- lojista_parceiro (LojistaParceiro.java)
CREATE TABLE lojista_parceiro (
    lojista_parceiro_id INT          NOT NULL AUTO_INCREMENT,
    nome                VARCHAR(255) NOT NULL,
    cnpj                VARCHAR(14),
    email               VARCHAR(255),
    telefone            VARCHAR(20),
    usuario_id          INT,
    created_at          DATETIME(6)  NOT NULL,
    updated_at          DATETIME(6),
    ativo               TINYINT(1)   NOT NULL DEFAULT 1,
    version             BIGINT,
    PRIMARY KEY (lojista_parceiro_id),
    CONSTRAINT fk_lp_usuario FOREIGN KEY (usuario_id) REFERENCES usuario (usuario_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- pedido_marketplace (PedidoMarketplace.java)
CREATE TABLE pedido_marketplace (
    pedido_marketplace_id INT            NOT NULL AUTO_INCREMENT,
    projeto_id            INT,
    lojista_parceiro_id   INT,
    status                VARCHAR(50),
    valor_total           DECIMAL(15,2),
    created_at            DATETIME(6)    NOT NULL,
    updated_at            DATETIME(6),
    ativo                 TINYINT(1)     NOT NULL DEFAULT 1,
    version               BIGINT,
    PRIMARY KEY (pedido_marketplace_id),
    CONSTRAINT fk_pm_projeto  FOREIGN KEY (projeto_id)          REFERENCES projeto          (projeto_id),
    CONSTRAINT fk_pm_lojista  FOREIGN KEY (lojista_parceiro_id) REFERENCES lojista_parceiro (lojista_parceiro_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- item_pedido_marketplace (ItemPedidoMarketplace.java)
CREATE TABLE item_pedido_marketplace (
    item_pedido_marketplace_id INT            NOT NULL AUTO_INCREMENT,
    pedido_marketplace_id      INT,
    material_id                INT,
    quantidade                 DECIMAL(15,3),
    preco_unitario             DECIMAL(15,2),
    valor_total                DECIMAL(15,2),
    created_at                 DATETIME(6)    NOT NULL,
    updated_at                 DATETIME(6),
    ativo                      TINYINT(1)     NOT NULL DEFAULT 1,
    version                    BIGINT,
    PRIMARY KEY (item_pedido_marketplace_id),
    CONSTRAINT fk_ipm_pedido   FOREIGN KEY (pedido_marketplace_id) REFERENCES pedido_marketplace (pedido_marketplace_id),
    CONSTRAINT fk_ipm_material FOREIGN KEY (material_id)           REFERENCES material           (material_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- proposta_credito (PropostaCredito.java)
CREATE TABLE proposta_credito (
    proposta_credito_id INT            NOT NULL AUTO_INCREMENT,
    projeto_id          INT,
    cliente_id          INT,
    valor_solicitado    DECIMAL(15,2),
    status              VARCHAR(50),
    created_at          DATETIME(6)    NOT NULL,
    updated_at          DATETIME(6),
    ativo               TINYINT(1)     NOT NULL DEFAULT 1,
    version             BIGINT,
    PRIMARY KEY (proposta_credito_id),
    CONSTRAINT fk_prc_projeto FOREIGN KEY (projeto_id) REFERENCES projeto (projeto_id),
    CONSTRAINT fk_prc_cliente FOREIGN KEY (cliente_id) REFERENCES cliente (cliente_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- parcela_credito (ParcelaCredito.java)
CREATE TABLE parcela_credito (
    parcela_credito_id  INT            NOT NULL AUTO_INCREMENT,
    proposta_credito_id INT,
    numero_parcela      INT,
    valor               DECIMAL(15,2),
    data_vencimento     DATE,
    status              VARCHAR(50),
    created_at          DATETIME(6)    NOT NULL,
    updated_at          DATETIME(6),
    ativo               TINYINT(1)     NOT NULL DEFAULT 1,
    version             BIGINT,
    PRIMARY KEY (parcela_credito_id),
    CONSTRAINT fk_parc_proposta FOREIGN KEY (proposta_credito_id) REFERENCES proposta_credito (proposta_credito_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- fatiamento_pagamento (FatiamentoPagamento.java)
CREATE TABLE fatiamento_pagamento (
    fatiamento_pagamento_id INT            NOT NULL AUTO_INCREMENT,
    pagamento_id            INT,
    numero_parcela          INT,
    valor                   DECIMAL(15,2),
    data_vencimento         DATE,
    status                  VARCHAR(50),
    created_at              DATETIME(6)    NOT NULL,
    updated_at              DATETIME(6),
    ativo                   TINYINT(1)     NOT NULL DEFAULT 1,
    version                 BIGINT,
    PRIMARY KEY (fatiamento_pagamento_id),
    CONSTRAINT fk_fp_pagamento FOREIGN KEY (pagamento_id) REFERENCES pagamento (pagamento_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- score_tfe (ScoreTfe.java)
CREATE TABLE score_tfe (
    score_tfe_id    INT            NOT NULL AUTO_INCREMENT,
    profissional_id INT,
    score           DECIMAL(5,2),
    data_calculo    DATE,
    created_at      DATETIME(6)    NOT NULL,
    updated_at      DATETIME(6),
    ativo           TINYINT(1)     NOT NULL DEFAULT 1,
    version         BIGINT,
    PRIMARY KEY (score_tfe_id),
    CONSTRAINT fk_st_profissional FOREIGN KEY (profissional_id) REFERENCES profissional_de_base (profissional_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- historico_score_tfe (HistoricoScoreTFE.java)
CREATE TABLE historico_score_tfe (
    historico_score_tfe_id INT            NOT NULL AUTO_INCREMENT,
    score_tfe_id           INT,
    score_anterior         DECIMAL(5,2),
    score_novo             DECIMAL(5,2),
    motivo                 VARCHAR(255),
    created_at             DATETIME(6)    NOT NULL,
    updated_at             DATETIME(6),
    ativo                  TINYINT(1)     NOT NULL DEFAULT 1,
    version                BIGINT,
    PRIMARY KEY (historico_score_tfe_id),
    CONSTRAINT fk_hst_score FOREIGN KEY (score_tfe_id) REFERENCES score_tfe (score_tfe_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- metricas_desempenho_tecnico (MetricasDesempenhoTecnico.java)
CREATE TABLE metricas_desempenho_tecnico (
    metricas_desempenho_tecnico_id INT            NOT NULL AUTO_INCREMENT,
    profissional_id                INT,
    prazo_medio_conclusao          DECIMAL(10,2),
    taxa_retrabalho                DECIMAL(5,2),
    created_at                     DATETIME(6)    NOT NULL,
    updated_at                     DATETIME(6),
    ativo                          TINYINT(1)     NOT NULL DEFAULT 1,
    version                        BIGINT,
    PRIMARY KEY (metricas_desempenho_tecnico_id),
    CONSTRAINT fk_mdt_profissional FOREIGN KEY (profissional_id) REFERENCES profissional_de_base (profissional_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- metricas_comportamento_operacional (MetricasComportamentoOperacional.java)
CREATE TABLE metricas_comportamento_operacional (
    metricas_comportamento_operacional_id INT           NOT NULL AUTO_INCREMENT,
    profissional_id                       INT,
    taxa_pontualidade                     DECIMAL(5,2),
    indice_satisfacao                     DECIMAL(5,2),
    created_at                            DATETIME(6)   NOT NULL,
    updated_at                            DATETIME(6),
    ativo                                 TINYINT(1)    NOT NULL DEFAULT 1,
    version                               BIGINT,
    PRIMARY KEY (metricas_comportamento_operacional_id),
    CONSTRAINT fk_mco_profissional FOREIGN KEY (profissional_id) REFERENCES profissional_de_base (profissional_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- trajetoria_laboral (TrajetoriaLaboral.java)
CREATE TABLE trajetoria_laboral (
    trajetoria_laboral_id INT          NOT NULL AUTO_INCREMENT,
    profissional_id       INT,
    empresa               VARCHAR(255),
    cargo                 VARCHAR(100),
    data_inicio           DATE,
    data_fim              DATE,
    created_at            DATETIME(6)  NOT NULL,
    updated_at            DATETIME(6),
    ativo                 TINYINT(1)   NOT NULL DEFAULT 1,
    version               BIGINT,
    PRIMARY KEY (trajetoria_laboral_id),
    CONSTRAINT fk_tl_profissional FOREIGN KEY (profissional_id) REFERENCES profissional_de_base (profissional_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- apadrinhamento_rede (ApadrinhamentoRede.java)
CREATE TABLE apadrinhamento_rede (
    apadrinhamento_rede_id INT         NOT NULL AUTO_INCREMENT,
    padrinho_id            INT,
    apadrinhado_id         INT,
    data_inicio            DATE,
    created_at             DATETIME(6) NOT NULL,
    updated_at             DATETIME(6),
    ativo                  TINYINT(1)  NOT NULL DEFAULT 1,
    version                BIGINT,
    PRIMARY KEY (apadrinhamento_rede_id),
    CONSTRAINT fk_ar_padrinho     FOREIGN KEY (padrinho_id)    REFERENCES profissional_de_base (profissional_id),
    CONSTRAINT fk_ar_apadrinhado  FOREIGN KEY (apadrinhado_id) REFERENCES profissional_de_base (profissional_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- interacao_campo_mor (InteracaoCampoMOR.java)
CREATE TABLE interacao_campo_mor (
    interacao_campo_mor_id INT         NOT NULL AUTO_INCREMENT,
    profissional_id        INT,
    projeto_id             INT,
    tipo_interacao         VARCHAR(50),
    descricao              TEXT,
    created_at             DATETIME(6) NOT NULL,
    updated_at             DATETIME(6),
    ativo                  TINYINT(1)  NOT NULL DEFAULT 1,
    version                BIGINT,
    PRIMARY KEY (interacao_campo_mor_id),
    CONSTRAINT fk_icm_profissional FOREIGN KEY (profissional_id) REFERENCES profissional_de_base (profissional_id),
    CONSTRAINT fk_icm_projeto      FOREIGN KEY (projeto_id)      REFERENCES projeto              (projeto_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

SET FOREIGN_KEY_CHECKS = 1;
