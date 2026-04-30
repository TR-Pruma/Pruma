-- V15: Entidades do Marketplace B2B e normalização de especialidades
--
-- lojista_parceiro    — entidade DISTINTA de fornecedor genérico
--                       tem take rate contratual com a plataforma (3–8% conforme doc)
-- especialidade       — normaliza profissional_de_base.especialidade VARCHAR(100) singular
-- profissional_especialidade — N:N profissional ↔ especialidade
-- ALTER insumo        — adiciona lojista_preferencial_id (algoritmo de roteamento ℛ)
-- ALTER pedido_marketplace — adiciona lojista_id FK (mantém fornecedor_id por compatibilidade)
-- ALTER item_pedido_marketplace — adiciona preco_total (campo calculado faltante no DER)

CREATE TABLE lojista_parceiro (
    lojista_id                  INT             NOT NULL AUTO_INCREMENT,
    cnpj                        VARCHAR(14)     NOT NULL,
    razao_social                VARCHAR(255)    NOT NULL,
    nome_fantasia               VARCHAR(255)    NULL,
    email                       VARCHAR(255)    NOT NULL,
    telefone                    VARCHAR(20)     NULL,
    taxa_comissao_percentual    DECIMAL(5,4)    NOT NULL
        COMMENT 'Take rate: 0.0300–0.0800 conforme w_supply no documento técnico',
    categorias_produtos         VARCHAR(500)    NULL
        COMMENT 'CSV das categorias: MATERIAL,FERRAMENTA,EPI,ACABAMENTO',
    endereco_id                 INT             NULL,
    created_at                  DATETIME(6)     NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at                  DATETIME(6)     NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    ativo                       TINYINT(1)      NOT NULL DEFAULT 1,
    version                     BIGINT          NOT NULL DEFAULT 0,
    PRIMARY KEY (lojista_id),
    CONSTRAINT uq_lojista_cnpj UNIQUE (cnpj),
    CONSTRAINT fk_lojista_endereco FOREIGN KEY (endereco_id)
        REFERENCES endereco (endereco_id)
        ON DELETE SET NULL ON UPDATE CASCADE,
    INDEX idx_lojista_cnpj   (cnpj),
    INDEX idx_lojista_ativo  (ativo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabela de ofícios/especialidades — substitui VARCHAR singular em profissional_de_base
CREATE TABLE especialidade (
    especialidade_id    INT             NOT NULL AUTO_INCREMENT,
    nome                VARCHAR(100)    NOT NULL COMMENT 'Ex: pedreiro, pintor, eletricista, encanador',
    descricao           VARCHAR(255)    NULL,
    created_at          DATETIME(6)     NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at          DATETIME(6)     NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    ativo               TINYINT(1)      NOT NULL DEFAULT 1,
    version             BIGINT          NOT NULL DEFAULT 0,
    PRIMARY KEY (especialidade_id),
    CONSTRAINT uq_especialidade_nome UNIQUE (nome)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Associativa N:N profissional ↔ especialidade
CREATE TABLE profissional_especialidade (
    profissional_id     INT NOT NULL,
    especialidade_id    INT NOT NULL,
    PRIMARY KEY (profissional_id, especialidade_id),
    CONSTRAINT fk_prof_esp_profissional FOREIGN KEY (profissional_id)
        REFERENCES profissional_de_base (profissional_id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_prof_esp_especialidade FOREIGN KEY (especialidade_id)
        REFERENCES especialidade (especialidade_id)
        ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Algoritmo de roteamento ℛ: M_lista → V_parceiros
ALTER TABLE insumo
    ADD COLUMN lojista_preferencial_id INT NULL
        COMMENT 'FK lojista_parceiro — roteamento ℛ do marketplace B2B'
        AFTER categoria_id,
    ADD CONSTRAINT fk_insumo_lojista_preferencial FOREIGN KEY (lojista_preferencial_id)
        REFERENCES lojista_parceiro (lojista_id)
        ON DELETE SET NULL ON UPDATE CASCADE,
    ADD INDEX idx_insumo_lojista_preferencial (lojista_preferencial_id);

-- Vincula pedido ao lojista parceiro (mantém fornecedor_id para compatibilidade retroativa)
ALTER TABLE pedido_marketplace
    ADD COLUMN lojista_id INT NULL
        COMMENT 'FK lojista_parceiro — marketplace B2B'
        AFTER fornecedor_id,
    ADD CONSTRAINT fk_pedido_lojista FOREIGN KEY (lojista_id)
        REFERENCES lojista_parceiro (lojista_id)
        ON DELETE SET NULL ON UPDATE CASCADE,
    ADD INDEX idx_pedido_lojista (lojista_id);

-- Adiciona preco_total faltante em item_pedido_marketplace
ALTER TABLE item_pedido_marketplace
    ADD COLUMN preco_total DECIMAL(18,2) NULL
        COMMENT 'quantidade * preco_unitario'
        AFTER preco_unitario;
