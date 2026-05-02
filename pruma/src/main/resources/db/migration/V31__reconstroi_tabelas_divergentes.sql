-- V31: Reconstroi tabelas cujo DDL no V1 diverge das entidades Java
-- Banco limpo — drop + recreate e seguro
-- Ordem: respeita dependencias de FK

SET FOREIGN_KEY_CHECKS = 0;

-- ============================================================
-- tipo_usuario
-- V1: PK tipo_usuario_id, auditoria EN (created_at/version)
-- Java: PK tipo_usuario, auditoria PT (data_criacao/versao)
-- ============================================================
DROP TABLE IF EXISTS tipo_usuario;
CREATE TABLE tipo_usuario (
    tipo_usuario     INT          NOT NULL AUTO_INCREMENT,
    descricao        VARCHAR(255) NOT NULL,
    ativo            TINYINT(1)   NOT NULL DEFAULT 1,
    data_criacao     DATETIME(6)  NOT NULL,
    data_atualizacao DATETIME(6),
    versao           BIGINT,
    PRIMARY KEY (tipo_usuario)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================
-- cliente_tipo
-- V1: PK cliente_tipo_id, sem FK tipo_usuario, descricao 100
-- Java: PK id_cliente_tipo, FK tipo_usuario, descricao_cliente 255
-- ============================================================
DROP TABLE IF EXISTS cliente_tipo;
CREATE TABLE cliente_tipo (
    id_cliente_tipo   INT          NOT NULL AUTO_INCREMENT,
    tipo_usuario      INT          NOT NULL,
    descricao_cliente VARCHAR(255) NOT NULL,
    data_criacao      DATETIME(6)  NOT NULL,
    data_atualizacao  DATETIME(6),
    versao            BIGINT,
    ativo             TINYINT(1)   NOT NULL DEFAULT 1,
    PRIMARY KEY (id_cliente_tipo),
    CONSTRAINT fk_ct_tipo_usuario FOREIGN KEY (tipo_usuario) REFERENCES tipo_usuario (tipo_usuario)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================
-- fornecedor
-- V1: cpf, email, telefone — sem contato, sem marketplace
-- Java: nome, cnpj, contato, parceiro_marketplace, taxa_comissao, categorias_produtos
-- ============================================================
ALTER TABLE insumo_fornecedor DROP FOREIGN KEY fk_if_fornecedor;
ALTER TABLE sub_contrato      DROP FOREIGN KEY fk_sc_fornecedor;

DROP TABLE IF EXISTS fornecedor;
CREATE TABLE fornecedor (
    fornecedor_id        INT          NOT NULL AUTO_INCREMENT,
    nome                 VARCHAR(255) NOT NULL,
    cnpj                 VARCHAR(18)  NOT NULL,
    contato              VARCHAR(100) NOT NULL,
    parceiro_marketplace TINYINT(1)   NOT NULL DEFAULT 0,
    taxa_comissao        DECIMAL(5,4),
    categorias_produtos  TEXT,
    ativo                TINYINT(1)   NOT NULL DEFAULT 1,
    created_at           DATETIME(6)  NOT NULL,
    updated_at           DATETIME(6),
    version              BIGINT,
    PRIMARY KEY (fornecedor_id),
    CONSTRAINT uq_fornecedor_cnpj UNIQUE (cnpj),
    INDEX idx_fornecedor_nome        (nome),
    INDEX idx_fornecedor_cnpj        (cnpj),
    INDEX idx_fornecedor_marketplace (parceiro_marketplace)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

ALTER TABLE insumo_fornecedor ADD CONSTRAINT fk_if_fornecedor FOREIGN KEY (fornecedor_id) REFERENCES fornecedor (fornecedor_id);
ALTER TABLE sub_contrato      ADD CONSTRAINT fk_sc_fornecedor FOREIGN KEY (fornecedor_id) REFERENCES fornecedor (fornecedor_id);

-- ============================================================
-- projeto
-- V1: cliente_id, usuario_id, endereco_id, status, data_inicio, data_fim
-- Java: apenas nome, descricao, data_criacao (DATE) + auditoria EN herdada
-- Nota: fk_projeto_cliente nao existe mais (removida no V30)
-- ============================================================
ALTER TABLE sub_contrato         DROP FOREIGN KEY fk_sc_projeto;
ALTER TABLE orcamento            DROP FOREIGN KEY fk_orcamento_projeto;
ALTER TABLE pagamento            DROP FOREIGN KEY fk_pagamento_projeto;
ALTER TABLE cronograma           DROP FOREIGN KEY fk_cronograma_projeto;
ALTER TABLE documento            DROP FOREIGN KEY fk_doc_projeto;
ALTER TABLE relatorio            DROP FOREIGN KEY fk_rel_projeto;
ALTER TABLE comunicacao          DROP FOREIGN KEY fk_com_projeto;
ALTER TABLE feedback             DROP FOREIGN KEY fk_fb_projeto;
ALTER TABLE avaliacao            DROP FOREIGN KEY fk_aval_projeto;
ALTER TABLE solicitacao_mudanca  DROP FOREIGN KEY fk_sm_projeto;
ALTER TABLE imagem_projeto       DROP FOREIGN KEY fk_img_projeto;
ALTER TABLE termo_garantia       DROP FOREIGN KEY fk_tg_projeto;
ALTER TABLE equipamento_projeto  DROP FOREIGN KEY fk_ep_projeto;
ALTER TABLE projeto_categoria    DROP FOREIGN KEY fk_pc_projeto;
ALTER TABLE projeto_profissional DROP FOREIGN KEY fk_pp_projeto;
ALTER TABLE pre_obra             DROP FOREIGN KEY fk_pre_obra_projeto;
ALTER TABLE obra                 DROP FOREIGN KEY fk_obra_projeto;
ALTER TABLE checklist            DROP FOREIGN KEY fk_checklist_projeto;

DROP TABLE IF EXISTS projeto;
CREATE TABLE projeto (
    projeto_id   INT          NOT NULL AUTO_INCREMENT,
    nome         VARCHAR(100) NOT NULL,
    descricao    TEXT,
    data_criacao DATE,
    created_at   DATETIME(6)  NOT NULL,
    updated_at   DATETIME(6),
    ativo        TINYINT(1)   NOT NULL DEFAULT 1,
    version      BIGINT,
    PRIMARY KEY (projeto_id),
    INDEX idx_projeto_nome         (nome),
    INDEX idx_projeto_data_criacao (data_criacao)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

ALTER TABLE sub_contrato         ADD CONSTRAINT fk_sc_projeto         FOREIGN KEY (projeto_id) REFERENCES projeto (projeto_id);
ALTER TABLE orcamento            ADD CONSTRAINT fk_orcamento_projeto   FOREIGN KEY (projeto_id) REFERENCES projeto (projeto_id);
ALTER TABLE pagamento            ADD CONSTRAINT fk_pagamento_projeto   FOREIGN KEY (projeto_id) REFERENCES projeto (projeto_id);
ALTER TABLE cronograma           ADD CONSTRAINT fk_cronograma_projeto  FOREIGN KEY (projeto_id) REFERENCES projeto (projeto_id);
ALTER TABLE documento            ADD CONSTRAINT fk_doc_projeto         FOREIGN KEY (projeto_id) REFERENCES projeto (projeto_id);
ALTER TABLE relatorio            ADD CONSTRAINT fk_rel_projeto         FOREIGN KEY (projeto_id) REFERENCES projeto (projeto_id);
ALTER TABLE comunicacao          ADD CONSTRAINT fk_com_projeto         FOREIGN KEY (projeto_id) REFERENCES projeto (projeto_id);
ALTER TABLE feedback             ADD CONSTRAINT fk_fb_projeto          FOREIGN KEY (projeto_id) REFERENCES projeto (projeto_id);
ALTER TABLE avaliacao            ADD CONSTRAINT fk_aval_projeto        FOREIGN KEY (projeto_id) REFERENCES projeto (projeto_id);
ALTER TABLE solicitacao_mudanca  ADD CONSTRAINT fk_sm_projeto          FOREIGN KEY (projeto_id) REFERENCES projeto (projeto_id);
ALTER TABLE imagem_projeto       ADD CONSTRAINT fk_img_projeto         FOREIGN KEY (projeto_id) REFERENCES projeto (projeto_id);
ALTER TABLE termo_garantia       ADD CONSTRAINT fk_tg_projeto          FOREIGN KEY (projeto_id) REFERENCES projeto (projeto_id);
ALTER TABLE equipamento_projeto  ADD CONSTRAINT fk_ep_projeto          FOREIGN KEY (projeto_id) REFERENCES projeto (projeto_id);
ALTER TABLE projeto_categoria    ADD CONSTRAINT fk_pc_projeto          FOREIGN KEY (projeto_id) REFERENCES projeto (projeto_id);
ALTER TABLE projeto_profissional ADD CONSTRAINT fk_pp_projeto          FOREIGN KEY (projeto_id) REFERENCES projeto (projeto_id);
ALTER TABLE pre_obra             ADD CONSTRAINT fk_pre_obra_projeto    FOREIGN KEY (projeto_id) REFERENCES projeto (projeto_id);
ALTER TABLE obra                 ADD CONSTRAINT fk_obra_projeto        FOREIGN KEY (projeto_id) REFERENCES projeto (projeto_id);
ALTER TABLE checklist            ADD CONSTRAINT fk_checklist_projeto   FOREIGN KEY (projeto_id) REFERENCES projeto (projeto_id);

SET FOREIGN_KEY_CHECKS = 1;
