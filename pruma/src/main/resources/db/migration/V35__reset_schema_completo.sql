-- =============================================================================
-- V35: RESET TOTAL DO SCHEMA
-- Dropa todas as tabelas (ordem inversa de dependencia) e recria do zero,
-- 100% alinhado com as entidades Java do branch feature/novos_services.
-- V36 e V37 ja estao incorporados aqui — aqueles arquivos sao no-op.
-- Seguro porque nao ha dados nas tabelas de desenvolvimento.
-- =============================================================================

SET FOREIGN_KEY_CHECKS = 0;

-- -----------------------------------------------------------------------
-- DROP todas as tabelas (ordem inversa de dependencia)
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
    nome                  VARCHAR(50)  NOT NULL,
    descricao             VARCHAR(255),
    created_at            DATETIME(6)  NOT NULL,
    updated_at            DATETIME(6),
    ativo                 TINYINT(1)   NOT NULL DEFAULT 1,
    version               BIGINT,
    PRIMARY KEY (status_equipamento_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE material (
    material_id INT          NOT NULL AUTO_INCREMENT,
    nome        VARCHAR(255) NOT NULL,
    descricao   VARCHAR(500),
    unidade     VARCHAR(20),
    created_at  DATETIME(6)  NOT NULL,
    updated_at  DATETIME(6),
    ativo       TINYINT(1)   NOT NULL DEFAULT 1,
    version     BIGINT,
    PRIMARY KEY (material_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE insumo (
    insumo_id  INT          NOT NULL AUTO_INCREMENT,
    nome       VARCHAR(255) NOT NULL,
    descricao  VARCHAR(500),
    unidade    VARCHAR(20),
    created_at DATETIME(6)  NOT NULL,
    updated_at DATETIME(6),
    ativo      TINYINT(1)   NOT NULL DEFAULT 1,
    version    BIGINT,
    PRIMARY KEY (insumo_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE tipo_documento (
    tipo_documento_id INT          NOT NULL AUTO_INCREMENT,
    nome              VARCHAR(100) NOT NULL,
    descricao         VARCHAR(255),
    created_at        DATETIME(6)  NOT NULL,
    updated_at        DATETIME(6),
    ativo             TINYINT(1)   NOT NULL DEFAULT 1,
    version           BIGINT,
    PRIMARY KEY (tipo_documento_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ---------------------------------------------------------------------------
-- profissional_de_base  (sem FK externa)
-- ---------------------------------------------------------------------------
CREATE TABLE profissional_de_base (
    profissional_id INT          NOT NULL AUTO_INCREMENT,
    usuario_id      INT,
    nome            VARCHAR(255) NOT NULL,
    cpf             VARCHAR(11)  NOT NULL,
    email           VARCHAR(255),
    telefone        VARCHAR(20),
    especialidade   VARCHAR(100),
    created_at      DATETIME(6)  NOT NULL,
    updated_at      DATETIME(6),
    ativo           TINYINT(1)   NOT NULL DEFAULT 1,
    version         BIGINT,
    PRIMARY KEY (profissional_id),
    CONSTRAINT uk_profissional_cpf UNIQUE (cpf),
    CONSTRAINT fk_prof_usuario FOREIGN KEY (usuario_id) REFERENCES usuario (usuario_id),
    INDEX idx_prof_cpf (cpf)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ---------------------------------------------------------------------------
-- apadrinhamento_rede  (corrigido: PK BIGINT, FK afilhado_id, status, data_fim)
-- ---------------------------------------------------------------------------
CREATE TABLE apadrinhamento_rede (
    apadrinhamento_id BIGINT      NOT NULL AUTO_INCREMENT,
    padrinho_id       INT         NOT NULL,
    afilhado_id       INT         NOT NULL,
    data_inicio       DATE        NOT NULL,
    data_fim          DATE        NULL,
    status            VARCHAR(20) NOT NULL DEFAULT 'ATIVO',
    created_at        DATETIME(6) NOT NULL,
    updated_at        DATETIME(6) NOT NULL,
    ativo             TINYINT(1)  NOT NULL DEFAULT 1,
    version           BIGINT,
    PRIMARY KEY (apadrinhamento_id),
    INDEX idx_apadrinhamento_padrinho (padrinho_id),
    INDEX idx_apadrinhamento_afilhado (afilhado_id),
    INDEX idx_apadrinhamento_status   (status),
    CONSTRAINT fk_apadr_padrinho FOREIGN KEY (padrinho_id)
        REFERENCES profissional_de_base (profissional_id),
    CONSTRAINT fk_apadr_afilhado FOREIGN KEY (afilhado_id)
        REFERENCES profissional_de_base (profissional_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE tecnico_de_obras (
    tecnico_id      INT          NOT NULL AUTO_INCREMENT,
    profissional_id INT          NOT NULL,
    registro_crea   VARCHAR(50),
    created_at      DATETIME(6)  NOT NULL,
    updated_at      DATETIME(6),
    ativo           TINYINT(1)   NOT NULL DEFAULT 1,
    version         BIGINT,
    PRIMARY KEY (tecnico_id),
    CONSTRAINT fk_tecnico_prof FOREIGN KEY (profissional_id)
        REFERENCES profissional_de_base (profissional_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE projeto (
    projeto_id    INT          NOT NULL AUTO_INCREMENT,
    nome          VARCHAR(255) NOT NULL,
    descricao     TEXT,
    cliente_id    INT          NOT NULL,
    status        VARCHAR(30)  NOT NULL DEFAULT 'PENDENTE',
    data_inicio   DATE,
    data_fim      DATE,
    endereco_id   INT,
    created_at    DATETIME(6)  NOT NULL,
    updated_at    DATETIME(6),
    ativo         TINYINT(1)   NOT NULL DEFAULT 1,
    version       BIGINT,
    PRIMARY KEY (projeto_id),
    CONSTRAINT fk_projeto_cliente  FOREIGN KEY (cliente_id)  REFERENCES cliente  (cliente_id),
    CONSTRAINT fk_projeto_endereco FOREIGN KEY (endereco_id) REFERENCES endereco (endereco_id),
    INDEX idx_projeto_cliente (cliente_id),
    INDEX idx_projeto_status  (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE pre_obra (
    pre_obra_id INT         NOT NULL AUTO_INCREMENT,
    projeto_id  INT         NOT NULL,
    status      VARCHAR(30) NOT NULL DEFAULT 'PENDENTE',
    observacoes TEXT,
    created_at  DATETIME(6) NOT NULL,
    updated_at  DATETIME(6),
    ativo       TINYINT(1)  NOT NULL DEFAULT 1,
    version     BIGINT,
    PRIMARY KEY (pre_obra_id),
    CONSTRAINT fk_pre_obra_projeto FOREIGN KEY (projeto_id) REFERENCES projeto (projeto_id),
    INDEX idx_pre_obra_projeto (projeto_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE obra (
    obra_id    INT         NOT NULL AUTO_INCREMENT,
    projeto_id INT         NOT NULL,
    status     VARCHAR(30) NOT NULL DEFAULT 'EM_ANDAMENTO',
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6),
    ativo      TINYINT(1)  NOT NULL DEFAULT 1,
    version    BIGINT,
    PRIMARY KEY (obra_id),
    CONSTRAINT fk_obra_projeto FOREIGN KEY (projeto_id) REFERENCES projeto (projeto_id),
    INDEX idx_obra_projeto (projeto_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE pos_obra (
    pos_obra_id INT         NOT NULL AUTO_INCREMENT,
    projeto_id  INT         NOT NULL,
    status      VARCHAR(30) NOT NULL DEFAULT 'PENDENTE',
    observacoes TEXT,
    created_at  DATETIME(6) NOT NULL,
    updated_at  DATETIME(6),
    ativo       TINYINT(1)  NOT NULL DEFAULT 1,
    version     BIGINT,
    PRIMARY KEY (pos_obra_id),
    CONSTRAINT fk_pos_obra_projeto FOREIGN KEY (projeto_id) REFERENCES projeto (projeto_id),
    INDEX idx_pos_obra_projeto (projeto_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ---------------------------------------------------------------------------
-- documento  (PK BIGINT — corrigido)
-- ---------------------------------------------------------------------------
CREATE TABLE documento (
    documento_id      BIGINT       NOT NULL AUTO_INCREMENT,
    projeto_id        INT          NOT NULL,
    tipo_documento_id INT,
    nome              VARCHAR(255) NOT NULL,
    descricao         VARCHAR(500),
    caminho           VARCHAR(1024),
    created_at        DATETIME(6)  NOT NULL,
    updated_at        DATETIME(6),
    ativo             TINYINT(1)   NOT NULL DEFAULT 1,
    version           BIGINT,
    PRIMARY KEY (documento_id),
    CONSTRAINT fk_doc_projeto       FOREIGN KEY (projeto_id)        REFERENCES projeto        (projeto_id),
    CONSTRAINT fk_doc_tipo          FOREIGN KEY (tipo_documento_id) REFERENCES tipo_documento (tipo_documento_id),
    INDEX idx_doc_projeto (projeto_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ---------------------------------------------------------------------------
-- assinatura_digital  (FK documento_id BIGINT — corrigido)
-- ---------------------------------------------------------------------------
CREATE TABLE assinatura_digital (
    assinatura_id INT          NOT NULL AUTO_INCREMENT,
    documento_id  BIGINT       NOT NULL,
    assinante     VARCHAR(255) NOT NULL,
    hash          VARCHAR(512),
    data_assinatura DATETIME(6),
    created_at    DATETIME(6)  NOT NULL,
    updated_at    DATETIME(6),
    ativo         TINYINT(1)   NOT NULL DEFAULT 1,
    version       BIGINT,
    PRIMARY KEY (assinatura_id),
    CONSTRAINT fk_assinatura_documento FOREIGN KEY (documento_id)
        REFERENCES documento (documento_id),
    INDEX idx_assinatura_doc (documento_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ---------------------------------------------------------------------------
-- anexo  (FK documento_id BIGINT + projeto_id + campos Anexo.java — corrigido)
-- ---------------------------------------------------------------------------
CREATE TABLE anexo (
    anexo_id     INT           NOT NULL AUTO_INCREMENT,
    documento_id BIGINT,
    projeto_id   INT           NOT NULL,
    tipo_anexo   VARCHAR(15)   NOT NULL DEFAULT 'OUTRO',
    caminho      VARCHAR(1024) NOT NULL,
    nome         VARCHAR(255),
    tipo_mime    VARCHAR(100),
    tamanho      BIGINT,
    created_at   DATETIME(6)   NOT NULL,
    updated_at   DATETIME(6),
    ativo        TINYINT(1)    NOT NULL DEFAULT 1,
    version      BIGINT,
    PRIMARY KEY (anexo_id),
    CONSTRAINT fk_anexo_documento FOREIGN KEY (documento_id)
        REFERENCES documento (documento_id),
    CONSTRAINT fk_anexo_projeto   FOREIGN KEY (projeto_id)
        REFERENCES projeto   (projeto_id),
    INDEX idx_anexo_projeto (projeto_id),
    INDEX idx_anexo_tipo    (tipo_anexo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE permissao_usuario (
    permissao_id INT         NOT NULL AUTO_INCREMENT,
    usuario_id   INT         NOT NULL,
    permissao    VARCHAR(50) NOT NULL,
    created_at   DATETIME(6) NOT NULL,
    updated_at   DATETIME(6),
    ativo        TINYINT(1)  NOT NULL DEFAULT 1,
    version      BIGINT,
    PRIMARY KEY (permissao_id),
    CONSTRAINT fk_perm_usuario FOREIGN KEY (usuario_id) REFERENCES usuario (usuario_id),
    INDEX idx_perm_usuario (usuario_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE mensagem_instantanea (
    mensagem_id INT         NOT NULL AUTO_INCREMENT,
    remetente_id INT        NOT NULL,
    destinatario_id INT     NOT NULL,
    conteudo    TEXT        NOT NULL,
    lida        TINYINT(1)  NOT NULL DEFAULT 0,
    created_at  DATETIME(6) NOT NULL,
    updated_at  DATETIME(6),
    ativo       TINYINT(1)  NOT NULL DEFAULT 1,
    version     BIGINT,
    PRIMARY KEY (mensagem_id),
    CONSTRAINT fk_msg_remetente     FOREIGN KEY (remetente_id)    REFERENCES usuario (usuario_id),
    CONSTRAINT fk_msg_destinatario  FOREIGN KEY (destinatario_id) REFERENCES usuario (usuario_id),
    INDEX idx_msg_remetente    (remetente_id),
    INDEX idx_msg_destinatario (destinatario_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE mensagem_instantanea_aux (
    mensagem_id   INT         NOT NULL,
    tipo_mensagem VARCHAR(30) NOT NULL,
    created_at    DATETIME(6) NOT NULL,
    updated_at    DATETIME(6),
    ativo         TINYINT(1)  NOT NULL DEFAULT 1,
    version       BIGINT,
    PRIMARY KEY (mensagem_id),
    CONSTRAINT fk_mi_aux_msg FOREIGN KEY (mensagem_id)
        REFERENCES mensagem_instantanea (mensagem_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE lembrete (
    lembrete_id INT          NOT NULL AUTO_INCREMENT,
    usuario_id  INT          NOT NULL,
    titulo      VARCHAR(255) NOT NULL,
    descricao   TEXT,
    data_hora   DATETIME(6)  NOT NULL,
    concluido   TINYINT(1)   NOT NULL DEFAULT 0,
    created_at  DATETIME(6)  NOT NULL,
    updated_at  DATETIME(6),
    ativo       TINYINT(1)   NOT NULL DEFAULT 1,
    version     BIGINT,
    PRIMARY KEY (lembrete_id),
    CONSTRAINT fk_lembrete_usuario FOREIGN KEY (usuario_id) REFERENCES usuario (usuario_id),
    INDEX idx_lembrete_usuario  (usuario_id),
    INDEX idx_lembrete_datahora (data_hora)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE notificacao (
    notificacao_id INT          NOT NULL AUTO_INCREMENT,
    usuario_id     INT          NOT NULL,
    titulo         VARCHAR(255) NOT NULL,
    mensagem       TEXT,
    lida           TINYINT(1)   NOT NULL DEFAULT 0,
    created_at     DATETIME(6)  NOT NULL,
    updated_at     DATETIME(6),
    ativo          TINYINT(1)   NOT NULL DEFAULT 1,
    version        BIGINT,
    PRIMARY KEY (notificacao_id),
    CONSTRAINT fk_notif_usuario FOREIGN KEY (usuario_id) REFERENCES usuario (usuario_id),
    INDEX idx_notif_usuario (usuario_id),
    INDEX idx_notif_lida    (lida)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE auditoria (
    auditoria_id  INT          NOT NULL AUTO_INCREMENT,
    usuario_id    INT,
    entidade      VARCHAR(100) NOT NULL,
    entidade_id   VARCHAR(50)  NOT NULL,
    acao          VARCHAR(20)  NOT NULL,
    descricao     TEXT,
    ip_address    VARCHAR(45),
    created_at    DATETIME(6)  NOT NULL,
    updated_at    DATETIME(6),
    ativo         TINYINT(1)   NOT NULL DEFAULT 1,
    version       BIGINT,
    PRIMARY KEY (auditoria_id),
    CONSTRAINT fk_auditoria_usuario FOREIGN KEY (usuario_id) REFERENCES usuario (usuario_id),
    INDEX idx_auditoria_entidade   (entidade, entidade_id),
    INDEX idx_auditoria_usuario    (usuario_id),
    INDEX idx_auditoria_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE log_alteracao (
    log_id       INT          NOT NULL AUTO_INCREMENT,
    usuario_id   INT,
    entidade     VARCHAR(100) NOT NULL,
    entidade_id  VARCHAR(50)  NOT NULL,
    campo        VARCHAR(100),
    valor_antigo TEXT,
    valor_novo   TEXT,
    created_at   DATETIME(6)  NOT NULL,
    updated_at   DATETIME(6),
    ativo        TINYINT(1)   NOT NULL DEFAULT 1,
    version      BIGINT,
    PRIMARY KEY (log_id),
    CONSTRAINT fk_log_usuario FOREIGN KEY (usuario_id) REFERENCES usuario (usuario_id),
    INDEX idx_log_entidade (entidade, entidade_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE log_alteracao_aux (
    log_id     INT         NOT NULL,
    tipo_log   VARCHAR(30) NOT NULL,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6),
    ativo      TINYINT(1)  NOT NULL DEFAULT 1,
    version    BIGINT,
    PRIMARY KEY (log_id),
    CONSTRAINT fk_la_aux_log FOREIGN KEY (log_id)
        REFERENCES log_alteracao (log_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE relatorio (
    relatorio_id INT          NOT NULL AUTO_INCREMENT,
    projeto_id   INT          NOT NULL,
    tipo         VARCHAR(50)  NOT NULL,
    conteudo     TEXT,
    created_at   DATETIME(6)  NOT NULL,
    updated_at   DATETIME(6),
    ativo        TINYINT(1)   NOT NULL DEFAULT 1,
    version      BIGINT,
    PRIMARY KEY (relatorio_id),
    CONSTRAINT fk_relatorio_projeto FOREIGN KEY (projeto_id) REFERENCES projeto (projeto_id),
    INDEX idx_relatorio_projeto (projeto_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE termo_garantia (
    termo_id   INT          NOT NULL AUTO_INCREMENT,
    projeto_id INT          NOT NULL,
    descricao  TEXT,
    validade   DATE,
    created_at DATETIME(6)  NOT NULL,
    updated_at DATETIME(6),
    ativo      TINYINT(1)   NOT NULL DEFAULT 1,
    version    BIGINT,
    PRIMARY KEY (termo_id),
    CONSTRAINT fk_termo_projeto FOREIGN KEY (projeto_id) REFERENCES projeto (projeto_id),
    INDEX idx_termo_projeto (projeto_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE sub_contrato (
    sub_contrato_id INT          NOT NULL AUTO_INCREMENT,
    projeto_id      INT          NOT NULL,
    profissional_id INT,
    descricao       TEXT,
    valor           DECIMAL(15,2),
    created_at      DATETIME(6)  NOT NULL,
    updated_at      DATETIME(6),
    ativo           TINYINT(1)   NOT NULL DEFAULT 1,
    version         BIGINT,
    PRIMARY KEY (sub_contrato_id),
    CONSTRAINT fk_subcontrato_projeto FOREIGN KEY (projeto_id)      REFERENCES projeto             (projeto_id),
    CONSTRAINT fk_subcontrato_prof    FOREIGN KEY (profissional_id) REFERENCES profissional_de_base (profissional_id),
    INDEX idx_subcontrato_projeto (projeto_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE orcamento (
    orcamento_id INT           NOT NULL AUTO_INCREMENT,
    projeto_id   INT           NOT NULL,
    valor_total  DECIMAL(15,2) NOT NULL DEFAULT 0.00,
    status       VARCHAR(30)   NOT NULL DEFAULT 'PENDENTE',
    created_at   DATETIME(6)   NOT NULL,
    updated_at   DATETIME(6),
    ativo        TINYINT(1)    NOT NULL DEFAULT 1,
    version      BIGINT,
    PRIMARY KEY (orcamento_id),
    CONSTRAINT fk_orcamento_projeto FOREIGN KEY (projeto_id) REFERENCES projeto (projeto_id),
    INDEX idx_orcamento_projeto (projeto_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE item_orcamento (
    item_id      INT           NOT NULL AUTO_INCREMENT,
    orcamento_id INT           NOT NULL,
    descricao    VARCHAR(500)  NOT NULL,
    quantidade   DECIMAL(10,3) NOT NULL DEFAULT 1.000,
    valor_unit   DECIMAL(15,2) NOT NULL DEFAULT 0.00,
    created_at   DATETIME(6)   NOT NULL,
    updated_at   DATETIME(6),
    ativo        TINYINT(1)    NOT NULL DEFAULT 1,
    version      BIGINT,
    PRIMARY KEY (item_id),
    CONSTRAINT fk_item_orc_orcamento FOREIGN KEY (orcamento_id) REFERENCES orcamento (orcamento_id),
    INDEX idx_item_orc_orcamento (orcamento_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE pagamento (
    pagamento_id INT           NOT NULL AUTO_INCREMENT,
    projeto_id   INT           NOT NULL,
    valor        DECIMAL(15,2) NOT NULL,
    status       VARCHAR(30)   NOT NULL DEFAULT 'PENDENTE',
    data_vencimento DATE,
    data_pagamento  DATE,
    liquidacao_atomica TINYINT(1) NOT NULL DEFAULT 0,
    created_at   DATETIME(6)   NOT NULL,
    updated_at   DATETIME(6),
    ativo        TINYINT(1)    NOT NULL DEFAULT 1,
    version      BIGINT,
    PRIMARY KEY (pagamento_id),
    CONSTRAINT fk_pagamento_projeto FOREIGN KEY (projeto_id) REFERENCES projeto (projeto_id),
    INDEX idx_pagamento_projeto (projeto_id),
    INDEX idx_pagamento_status  (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE feedback (
    feedback_id INT          NOT NULL AUTO_INCREMENT,
    projeto_id  INT          NOT NULL,
    usuario_id  INT,
    comentario  TEXT,
    created_at  DATETIME(6)  NOT NULL,
    updated_at  DATETIME(6),
    ativo       TINYINT(1)   NOT NULL DEFAULT 1,
    version     BIGINT,
    PRIMARY KEY (feedback_id),
    CONSTRAINT fk_feedback_projeto FOREIGN KEY (projeto_id) REFERENCES projeto  (projeto_id),
    CONSTRAINT fk_feedback_usuario FOREIGN KEY (usuario_id) REFERENCES usuario  (usuario_id),
    INDEX idx_feedback_projeto (projeto_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE avaliacao (
    avaliacao_id    INT            NOT NULL AUTO_INCREMENT,
    projeto_id      INT            NOT NULL,
    avaliador_id    INT,
    avaliado_id     INT,
    nota            DECIMAL(3,1)   NOT NULL,
    comentario      TEXT,
    created_at      DATETIME(6)    NOT NULL,
    updated_at      DATETIME(6),
    ativo           TINYINT(1)     NOT NULL DEFAULT 1,
    version         BIGINT,
    PRIMARY KEY (avaliacao_id),
    CONSTRAINT fk_aval_projeto  FOREIGN KEY (projeto_id)   REFERENCES projeto (projeto_id),
    CONSTRAINT fk_aval_avaliador FOREIGN KEY (avaliador_id) REFERENCES usuario (usuario_id),
    CONSTRAINT fk_aval_avaliado  FOREIGN KEY (avaliado_id)  REFERENCES usuario (usuario_id),
    INDEX idx_aval_projeto (projeto_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE solicitacao_mudanca (
    solicitacao_id INT          NOT NULL AUTO_INCREMENT,
    projeto_id     INT          NOT NULL,
    solicitante_id INT,
    descricao      TEXT         NOT NULL,
    status         VARCHAR(30)  NOT NULL DEFAULT 'ABERTA',
    created_at     DATETIME(6)  NOT NULL,
    updated_at     DATETIME(6),
    ativo          TINYINT(1)   NOT NULL DEFAULT 1,
    version        BIGINT,
    PRIMARY KEY (solicitacao_id),
    CONSTRAINT fk_sol_mut_projeto    FOREIGN KEY (projeto_id)     REFERENCES projeto (projeto_id),
    CONSTRAINT fk_sol_mut_solicitante FOREIGN KEY (solicitante_id) REFERENCES usuario (usuario_id),
    INDEX idx_sol_mut_projeto (projeto_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE imagem_projeto (
    imagem_id  INT           NOT NULL AUTO_INCREMENT,
    projeto_id INT           NOT NULL,
    caminho    VARCHAR(1024) NOT NULL,
    descricao  VARCHAR(500),
    latitude   DECIMAL(10,7),
    longitude  DECIMAL(10,7),
    exif_data  TEXT,
    created_at DATETIME(6)   NOT NULL,
    updated_at DATETIME(6),
    ativo      TINYINT(1)    NOT NULL DEFAULT 1,
    version    BIGINT,
    PRIMARY KEY (imagem_id),
    CONSTRAINT fk_imagem_projeto FOREIGN KEY (projeto_id) REFERENCES projeto (projeto_id),
    INDEX idx_imagem_projeto (projeto_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE historico_localizacao (
    localizacao_id INT           NOT NULL AUTO_INCREMENT,
    projeto_id     INT           NOT NULL,
    latitude       DECIMAL(10,7) NOT NULL,
    longitude      DECIMAL(10,7) NOT NULL,
    precisao       DECIMAL(8,2),
    altitude       DECIMAL(8,2),
    velocidade     DECIMAL(8,2),
    direcao        DECIMAL(5,2),
    provedor       VARCHAR(50),
    capturado_em   DATETIME(6)   NOT NULL,
    created_at     DATETIME(6)   NOT NULL,
    updated_at     DATETIME(6),
    ativo          TINYINT(1)    NOT NULL DEFAULT 1,
    version        BIGINT,
    PRIMARY KEY (localizacao_id),
    CONSTRAINT fk_loc_projeto FOREIGN KEY (projeto_id) REFERENCES projeto (projeto_id),
    INDEX idx_loc_projeto     (projeto_id),
    INDEX idx_loc_capturado   (capturado_em)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE inspecao (
    inspecao_id    INT          NOT NULL AUTO_INCREMENT,
    projeto_id     INT          NOT NULL,
    tecnico_id     INT,
    data_inspecao  DATETIME(6)  NOT NULL,
    status         VARCHAR(30)  NOT NULL DEFAULT 'PENDENTE',
    observacoes    TEXT,
    created_at     DATETIME(6)  NOT NULL,
    updated_at     DATETIME(6),
    ativo          TINYINT(1)   NOT NULL DEFAULT 1,
    version        BIGINT,
    PRIMARY KEY (inspecao_id),
    CONSTRAINT fk_inspecao_projeto FOREIGN KEY (projeto_id) REFERENCES projeto          (projeto_id),
    CONSTRAINT fk_inspecao_tecnico FOREIGN KEY (tecnico_id) REFERENCES tecnico_de_obras (tecnico_id),
    INDEX idx_inspecao_projeto (projeto_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE equipamento_projeto (
    equipamento_projeto_id INT         NOT NULL AUTO_INCREMENT,
    projeto_id             INT         NOT NULL,
    equipamento_id         INT         NOT NULL,
    status_equipamento_id  INT,
    data_entrada           DATE,
    data_saida             DATE,
    created_at             DATETIME(6) NOT NULL,
    updated_at             DATETIME(6),
    ativo                  TINYINT(1)  NOT NULL DEFAULT 1,
    version                BIGINT,
    PRIMARY KEY (equipamento_projeto_id),
    CONSTRAINT fk_ep_projeto    FOREIGN KEY (projeto_id)            REFERENCES projeto          (projeto_id),
    CONSTRAINT fk_ep_equip      FOREIGN KEY (equipamento_id)         REFERENCES equipamento      (equipamento_id),
    CONSTRAINT fk_ep_status     FOREIGN KEY (status_equipamento_id)  REFERENCES status_equipamento (status_equipamento_id),
    INDEX idx_ep_projeto    (projeto_id),
    INDEX idx_ep_equipamento (equipamento_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE equipamento_projeto_aux (
    equipamento_projeto_id INT         NOT NULL,
    observacao             TEXT,
    created_at             DATETIME(6) NOT NULL,
    updated_at             DATETIME(6),
    ativo                  TINYINT(1)  NOT NULL DEFAULT 1,
    version                BIGINT,
    PRIMARY KEY (equipamento_projeto_id),
    CONSTRAINT fk_epa_ep FOREIGN KEY (equipamento_projeto_id)
        REFERENCES equipamento_projeto (equipamento_projeto_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE requisicao_material (
    requisicao_id  INT           NOT NULL AUTO_INCREMENT,
    projeto_id     INT           NOT NULL,
    material_id    INT           NOT NULL,
    quantidade     DECIMAL(10,3) NOT NULL,
    status         VARCHAR(30)   NOT NULL DEFAULT 'PENDENTE',
    created_at     DATETIME(6)   NOT NULL,
    updated_at     DATETIME(6),
    ativo          TINYINT(1)    NOT NULL DEFAULT 1,
    version        BIGINT,
    PRIMARY KEY (requisicao_id),
    CONSTRAINT fk_req_mat_projeto  FOREIGN KEY (projeto_id)  REFERENCES projeto  (projeto_id),
    CONSTRAINT fk_req_mat_material FOREIGN KEY (material_id) REFERENCES material (material_id),
    INDEX idx_req_mat_projeto (projeto_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE material_utilizado (
    material_utilizado_id INT           NOT NULL AUTO_INCREMENT,
    projeto_id            INT           NOT NULL,
    material_id           INT           NOT NULL,
    quantidade            DECIMAL(10,3) NOT NULL,
    created_at            DATETIME(6)   NOT NULL,
    updated_at            DATETIME(6),
    ativo                 TINYINT(1)    NOT NULL DEFAULT 1,
    version               BIGINT,
    PRIMARY KEY (material_utilizado_id),
    CONSTRAINT fk_mat_util_projeto  FOREIGN KEY (projeto_id)  REFERENCES projeto  (projeto_id),
    CONSTRAINT fk_mat_util_material FOREIGN KEY (material_id) REFERENCES material (material_id),
    INDEX idx_mat_util_projeto (projeto_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE checklist (
    checklist_id INT          NOT NULL AUTO_INCREMENT,
    projeto_id   INT          NOT NULL,
    nome         VARCHAR(255) NOT NULL,
    created_at   DATETIME(6)  NOT NULL,
    updated_at   DATETIME(6),
    ativo        TINYINT(1)   NOT NULL DEFAULT 1,
    version      BIGINT,
    PRIMARY KEY (checklist_id),
    CONSTRAINT fk_checklist_projeto FOREIGN KEY (projeto_id) REFERENCES projeto (projeto_id),
    INDEX idx_checklist_projeto (projeto_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE item_checklist (
    item_id      INT          NOT NULL AUTO_INCREMENT,
    checklist_id INT          NOT NULL,
    descricao    VARCHAR(500) NOT NULL,
    concluido    TINYINT(1)   NOT NULL DEFAULT 0,
    created_at   DATETIME(6)  NOT NULL,
    updated_at   DATETIME(6),
    ativo        TINYINT(1)   NOT NULL DEFAULT 1,
    version      BIGINT,
    PRIMARY KEY (item_id),
    CONSTRAINT fk_item_chk_checklist FOREIGN KEY (checklist_id) REFERENCES checklist (checklist_id),
    INDEX idx_item_chk_checklist (checklist_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE tarefa (
    tarefa_id   INT          NOT NULL AUTO_INCREMENT,
    projeto_id  INT          NOT NULL,
    titulo      VARCHAR(255) NOT NULL,
    descricao   TEXT,
    status      VARCHAR(30)  NOT NULL DEFAULT 'PENDENTE',
    responsavel_id INT,
    data_limite DATE,
    created_at  DATETIME(6)  NOT NULL,
    updated_at  DATETIME(6),
    ativo       TINYINT(1)   NOT NULL DEFAULT 1,
    version     BIGINT,
    PRIMARY KEY (tarefa_id),
    CONSTRAINT fk_tarefa_projeto     FOREIGN KEY (projeto_id)     REFERENCES projeto             (projeto_id),
    CONSTRAINT fk_tarefa_responsavel FOREIGN KEY (responsavel_id) REFERENCES profissional_de_base (profissional_id),
    INDEX idx_tarefa_projeto (projeto_id),
    INDEX idx_tarefa_status  (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE atividade (
    atividade_id INT          NOT NULL AUTO_INCREMENT,
    projeto_id   INT          NOT NULL,
    tarefa_id    INT,
    descricao    VARCHAR(500) NOT NULL,
    status       VARCHAR(30)  NOT NULL DEFAULT 'PENDENTE',
    created_at   DATETIME(6)  NOT NULL,
    updated_at   DATETIME(6),
    ativo        TINYINT(1)   NOT NULL DEFAULT 1,
    version      BIGINT,
    PRIMARY KEY (atividade_id),
    CONSTRAINT fk_atividade_projeto FOREIGN KEY (projeto_id) REFERENCES projeto (projeto_id),
    CONSTRAINT fk_atividade_tarefa  FOREIGN KEY (tarefa_id)  REFERENCES tarefa  (tarefa_id),
    INDEX idx_atividade_projeto (projeto_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE fase_cronograma (
    fase_id    INT          NOT NULL AUTO_INCREMENT,
    nome       VARCHAR(255) NOT NULL,
    descricao  VARCHAR(500),
    ordem      INT          NOT NULL DEFAULT 0,
    created_at DATETIME(6)  NOT NULL,
    updated_at DATETIME(6),
    ativo      TINYINT(1)   NOT NULL DEFAULT 1,
    version    BIGINT,
    PRIMARY KEY (fase_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ---------------------------------------------------------------------------
-- cronograma  (+ coluna nome + unique constraint — corrigido)
-- ---------------------------------------------------------------------------
CREATE TABLE cronograma (
    cronograma_id INT          NOT NULL AUTO_INCREMENT,
    projeto_id    INT          NOT NULL,
    fase_id       INT,
    nome          VARCHAR(255) NOT NULL,
    data_inicio   DATE         NOT NULL,
    data_fim      DATE         NOT NULL,
    status        VARCHAR(30)  NOT NULL DEFAULT 'PLANEJADO',
    created_at    DATETIME(6)  NOT NULL,
    updated_at    DATETIME(6),
    ativo         TINYINT(1)   NOT NULL DEFAULT 1,
    version       BIGINT,
    PRIMARY KEY (cronograma_id),
    CONSTRAINT fk_cronograma_projeto FOREIGN KEY (projeto_id) REFERENCES projeto       (projeto_id),
    CONSTRAINT fk_cronograma_fase    FOREIGN KEY (fase_id)    REFERENCES fase_cronograma (fase_id),
    CONSTRAINT uk_cronograma_projeto_periodo UNIQUE (projeto_id, data_inicio, data_fim),
    INDEX idx_cronograma_projeto (projeto_id),
    INDEX idx_cronograma_periodo (data_inicio, data_fim)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ---------------------------------------------------------------------------
-- comunicacao  (corrigido: cliente_id, mensagem TEXT, tipo_remetente)
-- ---------------------------------------------------------------------------
CREATE TABLE comunicacao (
    comunicacao_id INT         NOT NULL AUTO_INCREMENT,
    projeto_id     INT         NOT NULL,
    cliente_id     INT         NOT NULL,
    mensagem       TEXT        NOT NULL,
    tipo_remetente VARCHAR(15) NOT NULL,
    created_at     DATETIME(6) NOT NULL,
    updated_at     DATETIME(6),
    ativo          TINYINT(1)  NOT NULL DEFAULT 1,
    version        BIGINT,
    PRIMARY KEY (comunicacao_id),
    CONSTRAINT fk_com_projeto FOREIGN KEY (projeto_id) REFERENCES projeto (projeto_id),
    CONSTRAINT fk_com_cliente FOREIGN KEY (cliente_id) REFERENCES cliente (cliente_id),
    INDEX idx_com_projeto   (projeto_id),
    INDEX idx_com_cliente   (cliente_id),
    INDEX idx_com_remetente (tipo_remetente)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ---------------------------------------------------------------------------
-- comunicacao_aux  (corrigido: @MapsId sobre comunicacao)
-- ---------------------------------------------------------------------------
CREATE TABLE comunicacao_aux (
    comunicacao_id INT         NOT NULL,
    tipo_mensagem  VARCHAR(30) NOT NULL,
    created_at     DATETIME(6) NOT NULL,
    updated_at     DATETIME(6),
    ativo          TINYINT(1)  NOT NULL DEFAULT 1,
    version        BIGINT,
    PRIMARY KEY (comunicacao_id),
    CONSTRAINT fk_ca_comunicacao FOREIGN KEY (comunicacao_id)
        REFERENCES comunicacao (comunicacao_id)
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
    CONSTRAINT fk_pp_projeto FOREIGN KEY (projeto_id)      REFERENCES projeto             (projeto_id),
    CONSTRAINT fk_pp_prof    FOREIGN KEY (profissional_id) REFERENCES profissional_de_base (profissional_id),
    INDEX idx_pp_projeto (projeto_id),
    INDEX idx_pp_prof    (profissional_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE projeto_categoria (
    projeto_id   INT NOT NULL,
    categoria_id INT NOT NULL,
    PRIMARY KEY (projeto_id, categoria_id),
    CONSTRAINT fk_pc_projeto   FOREIGN KEY (projeto_id)   REFERENCES projeto   (projeto_id),
    CONSTRAINT fk_pc_categoria FOREIGN KEY (categoria_id) REFERENCES categoria (categoria_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE especialidade (
    especialidade_id INT          NOT NULL AUTO_INCREMENT,
    nome             VARCHAR(100) NOT NULL,
    descricao        VARCHAR(255),
    created_at       DATETIME(6)  NOT NULL,
    updated_at       DATETIME(6),
    ativo            TINYINT(1)   NOT NULL DEFAULT 1,
    version          BIGINT,
    PRIMARY KEY (especialidade_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE profissional_especialidade (
    profissional_id  INT NOT NULL,
    especialidade_id INT NOT NULL,
    PRIMARY KEY (profissional_id, especialidade_id),
    CONSTRAINT fk_pe_prof FOREIGN KEY (profissional_id)  REFERENCES profissional_de_base (profissional_id),
    CONSTRAINT fk_pe_esp  FOREIGN KEY (especialidade_id) REFERENCES especialidade        (especialidade_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE lojista_parceiro (
    lojista_id      INT          NOT NULL AUTO_INCREMENT,
    nome            VARCHAR(255) NOT NULL,
    cnpj            VARCHAR(14),
    email           VARCHAR(255),
    telefone        VARCHAR(20),
    created_at      DATETIME(6)  NOT NULL,
    updated_at      DATETIME(6),
    ativo           TINYINT(1)   NOT NULL DEFAULT 1,
    version         BIGINT,
    PRIMARY KEY (lojista_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE pedido_marketplace (
    pedido_id    INT           NOT NULL AUTO_INCREMENT,
    cliente_id   INT           NOT NULL,
    lojista_id   INT           NOT NULL,
    status       VARCHAR(30)   NOT NULL DEFAULT 'PENDENTE',
    valor_total  DECIMAL(15,2) NOT NULL DEFAULT 0.00,
    created_at   DATETIME(6)   NOT NULL,
    updated_at   DATETIME(6),
    ativo        TINYINT(1)    NOT NULL DEFAULT 1,
    version      BIGINT,
    PRIMARY KEY (pedido_id),
    CONSTRAINT fk_pedido_cliente  FOREIGN KEY (cliente_id)  REFERENCES cliente          (cliente_id),
    CONSTRAINT fk_pedido_lojista  FOREIGN KEY (lojista_id)  REFERENCES lojista_parceiro (lojista_id),
    INDEX idx_pedido_cliente  (cliente_id),
    INDEX idx_pedido_lojista  (lojista_id),
    INDEX idx_pedido_status   (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE item_pedido_marketplace (
    item_pedido_id INT           NOT NULL AUTO_INCREMENT,
    pedido_id      INT           NOT NULL,
    descricao      VARCHAR(500)  NOT NULL,
    quantidade     DECIMAL(10,3) NOT NULL DEFAULT 1.000,
    valor_unit     DECIMAL(15,2) NOT NULL DEFAULT 0.00,
    created_at     DATETIME(6)   NOT NULL,
    updated_at     DATETIME(6),
    ativo          TINYINT(1)    NOT NULL DEFAULT 1,
    version        BIGINT,
    PRIMARY KEY (item_pedido_id),
    CONSTRAINT fk_item_pedido FOREIGN KEY (pedido_id) REFERENCES pedido_marketplace (pedido_id),
    INDEX idx_item_pedido (pedido_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE fatiamento_pagamento (
    fatiamento_id   INT           NOT NULL AUTO_INCREMENT,
    pagamento_id    INT           NOT NULL,
    numero_parcela  INT           NOT NULL,
    valor_parcela   DECIMAL(15,2) NOT NULL,
    data_vencimento DATE          NOT NULL,
    status          VARCHAR(30)   NOT NULL DEFAULT 'PENDENTE',
    created_at      DATETIME(6)   NOT NULL,
    updated_at      DATETIME(6),
    ativo           TINYINT(1)    NOT NULL DEFAULT 1,
    version         BIGINT,
    PRIMARY KEY (fatiamento_id),
    CONSTRAINT fk_fat_pagamento FOREIGN KEY (pagamento_id) REFERENCES pagamento (pagamento_id),
    INDEX idx_fat_pagamento (pagamento_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE parcela_credito (
    parcela_id      INT           NOT NULL AUTO_INCREMENT,
    cliente_id      INT           NOT NULL,
    numero_parcela  INT           NOT NULL,
    valor           DECIMAL(15,2) NOT NULL,
    data_vencimento DATE          NOT NULL,
    status          VARCHAR(30)   NOT NULL DEFAULT 'PENDENTE',
    created_at      DATETIME(6)   NOT NULL,
    updated_at      DATETIME(6),
    ativo           TINYINT(1)    NOT NULL DEFAULT 1,
    version         BIGINT,
    PRIMARY KEY (parcela_id),
    CONSTRAINT fk_parcela_cliente FOREIGN KEY (cliente_id) REFERENCES cliente (cliente_id),
    INDEX idx_parcela_cliente (cliente_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE proposta_credito (
    proposta_id  INT           NOT NULL AUTO_INCREMENT,
    cliente_id   INT           NOT NULL,
    valor        DECIMAL(15,2) NOT NULL,
    status       VARCHAR(30)   NOT NULL DEFAULT 'PENDENTE',
    created_at   DATETIME(6)   NOT NULL,
    updated_at   DATETIME(6),
    ativo        TINYINT(1)    NOT NULL DEFAULT 1,
    version      BIGINT,
    PRIMARY KEY (proposta_id),
    CONSTRAINT fk_proposta_cliente FOREIGN KEY (cliente_id) REFERENCES cliente (cliente_id),
    INDEX idx_proposta_cliente (cliente_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE trajetoria_laboral (
    trajetoria_id   INT          NOT NULL AUTO_INCREMENT,
    profissional_id INT          NOT NULL,
    cargo           VARCHAR(100) NOT NULL,
    empresa         VARCHAR(255),
    data_inicio     DATE         NOT NULL,
    data_fim        DATE,
    created_at      DATETIME(6)  NOT NULL,
    updated_at      DATETIME(6),
    ativo           TINYINT(1)   NOT NULL DEFAULT 1,
    version         BIGINT,
    PRIMARY KEY (trajetoria_id),
    CONSTRAINT fk_traj_prof FOREIGN KEY (profissional_id)
        REFERENCES profissional_de_base (profissional_id),
    INDEX idx_traj_prof (profissional_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE score_tfe (
    score_id        INT           NOT NULL AUTO_INCREMENT,
    profissional_id INT           NOT NULL,
    score           DECIMAL(5,2)  NOT NULL DEFAULT 0.00,
    created_at      DATETIME(6)   NOT NULL,
    updated_at      DATETIME(6),
    ativo           TINYINT(1)    NOT NULL DEFAULT 1,
    version         BIGINT,
    PRIMARY KEY (score_id),
    CONSTRAINT fk_score_prof FOREIGN KEY (profissional_id)
        REFERENCES profissional_de_base (profissional_id),
    INDEX idx_score_prof (profissional_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE historico_score_tfe (
    historico_id    INT           NOT NULL AUTO_INCREMENT,
    profissional_id INT           NOT NULL,
    score           DECIMAL(5,2)  NOT NULL,
    motivo          VARCHAR(255),
    created_at      DATETIME(6)   NOT NULL,
    updated_at      DATETIME(6),
    ativo           TINYINT(1)    NOT NULL DEFAULT 1,
    version         BIGINT,
    PRIMARY KEY (historico_id),
    CONSTRAINT fk_hist_score_prof FOREIGN KEY (profissional_id)
        REFERENCES profissional_de_base (profissional_id),
    INDEX idx_hist_score_prof (profissional_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE metricas_desempenho_tecnico (
    metrica_id      INT          NOT NULL AUTO_INCREMENT,
    profissional_id INT          NOT NULL,
    indicador       VARCHAR(100) NOT NULL,
    valor           DECIMAL(10,4),
    periodo         VARCHAR(20),
    created_at      DATETIME(6)  NOT NULL,
    updated_at      DATETIME(6),
    ativo           TINYINT(1)   NOT NULL DEFAULT 1,
    version         BIGINT,
    PRIMARY KEY (metrica_id),
    CONSTRAINT fk_mdt_prof FOREIGN KEY (profissional_id)
        REFERENCES profissional_de_base (profissional_id),
    INDEX idx_mdt_prof (profissional_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE metricas_comportamento_operacional (
    metrica_id      INT          NOT NULL AUTO_INCREMENT,
    profissional_id INT          NOT NULL,
    indicador       VARCHAR(100) NOT NULL,
    valor           DECIMAL(10,4),
    periodo         VARCHAR(20),
    created_at      DATETIME(6)  NOT NULL,
    updated_at      DATETIME(6),
    ativo           TINYINT(1)   NOT NULL DEFAULT 1,
    version         BIGINT,
    PRIMARY KEY (metrica_id),
    CONSTRAINT fk_mco_prof FOREIGN KEY (profissional_id)
        REFERENCES profissional_de_base (profissional_id),
    INDEX idx_mco_prof (profissional_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE interacao_campo_mor (
    interacao_id    INT          NOT NULL AUTO_INCREMENT,
    profissional_id INT          NOT NULL,
    projeto_id      INT,
    tipo            VARCHAR(50)  NOT NULL,
    descricao       TEXT,
    created_at      DATETIME(6)  NOT NULL,
    updated_at      DATETIME(6),
    ativo           TINYINT(1)   NOT NULL DEFAULT 1,
    version         BIGINT,
    PRIMARY KEY (interacao_id),
    CONSTRAINT fk_icm_prof    FOREIGN KEY (profissional_id) REFERENCES profissional_de_base (profissional_id),
    CONSTRAINT fk_icm_projeto FOREIGN KEY (projeto_id)      REFERENCES projeto              (projeto_id),
    INDEX idx_icm_prof    (profissional_id),
    INDEX idx_icm_projeto (projeto_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE insumo_fornecedor (
    insumo_fornecedor_id INT           NOT NULL AUTO_INCREMENT,
    insumo_id            INT           NOT NULL,
    fornecedor_id        INT           NOT NULL,
    preco                DECIMAL(15,2),
    created_at           DATETIME(6)   NOT NULL,
    updated_at           DATETIME(6),
    ativo                TINYINT(1)    NOT NULL DEFAULT 1,
    version              BIGINT,
    PRIMARY KEY (insumo_fornecedor_id),
    CONSTRAINT fk_if_insumo    FOREIGN KEY (insumo_id)    REFERENCES insumo    (insumo_id),
    CONSTRAINT fk_if_fornecedor FOREIGN KEY (fornecedor_id) REFERENCES fornecedor (fornecedor_id),
    INDEX idx_if_insumo    (insumo_id),
    INDEX idx_if_fornecedor (fornecedor_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE insumo_fornecedor_aux (
    insumo_fornecedor_id INT         NOT NULL,
    observacao           TEXT,
    created_at           DATETIME(6) NOT NULL,
    updated_at           DATETIME(6),
    ativo                TINYINT(1)  NOT NULL DEFAULT 1,
    version              BIGINT,
    PRIMARY KEY (insumo_fornecedor_id),
    CONSTRAINT fk_ifa_if FOREIGN KEY (insumo_fornecedor_id)
        REFERENCES insumo_fornecedor (insumo_fornecedor_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

SET FOREIGN_KEY_CHECKS = 1;
