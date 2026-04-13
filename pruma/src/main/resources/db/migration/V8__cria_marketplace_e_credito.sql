-- V8: Cria tabelas pedido_marketplace, item_pedido_marketplace e proposta_credito

CREATE TABLE IF NOT EXISTS pedido_marketplace (
    pedido_marketplace_id BIGSERIAL    PRIMARY KEY,
    profissional_id       INT          NOT NULL,
    obra_id               INT,
    fornecedor_id         INT          NOT NULL,
    valor_bruto           DECIMAL(18,2) NOT NULL,
    taxa_plataforma       DECIMAL(18,2),
    status                VARCHAR(20)  NOT NULL DEFAULT 'PENDENTE',
    data_entrega          DATE,
    created_at            TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at            TIMESTAMP    NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_pedido_mkt_profissional
        FOREIGN KEY (profissional_id) REFERENCES profissional_de_base(profissional_id)
            ON DELETE CASCADE,
    CONSTRAINT fk_pedido_mkt_obra
        FOREIGN KEY (obra_id) REFERENCES obra(obra_id)
            ON DELETE SET NULL,
    CONSTRAINT fk_pedido_mkt_fornecedor
        FOREIGN KEY (fornecedor_id) REFERENCES fornecedor(fornecedor_id)
);

CREATE INDEX IF NOT EXISTS idx_pedido_mkt_profissional ON pedido_marketplace(profissional_id);
CREATE INDEX IF NOT EXISTS idx_pedido_mkt_obra         ON pedido_marketplace(obra_id);
CREATE INDEX IF NOT EXISTS idx_pedido_mkt_fornecedor   ON pedido_marketplace(fornecedor_id);
CREATE INDEX IF NOT EXISTS idx_pedido_mkt_status       ON pedido_marketplace(status);

-- -------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS item_pedido_marketplace (
    item_pedido_marketplace_id BIGSERIAL     PRIMARY KEY,
    pedido_marketplace_id      BIGINT        NOT NULL,
    insumo_id                  INT           NOT NULL,
    quantidade                 INT           NOT NULL,
    preco_unitario             DECIMAL(18,2) NOT NULL,
    CONSTRAINT fk_item_pedido_mkt_pedido
        FOREIGN KEY (pedido_marketplace_id) REFERENCES pedido_marketplace(pedido_marketplace_id)
            ON DELETE CASCADE,
    CONSTRAINT fk_item_pedido_mkt_insumo
        FOREIGN KEY (insumo_id) REFERENCES insumo(insumo_id)
);

CREATE INDEX IF NOT EXISTS idx_item_pedido_mkt_pedido ON item_pedido_marketplace(pedido_marketplace_id);
CREATE INDEX IF NOT EXISTS idx_item_pedido_mkt_insumo ON item_pedido_marketplace(insumo_id);

-- -------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS proposta_credito (
    proposta_credito_id  BIGSERIAL     PRIMARY KEY,
    profissional_id      INT           NOT NULL,
    score_no_momento     DECIMAL(5,4)  NOT NULL,
    valor_solicitado     DECIMAL(18,2) NOT NULL,
    valor_aprovado       DECIMAL(18,2),
    taxa_juros           DECIMAL(6,4),
    numero_parcelas      INT,
    status               VARCHAR(20)   NOT NULL DEFAULT 'SOLICITADA',
    parceiro_financeiro  VARCHAR(100),
    data_vencimento      DATE,
    created_at           TIMESTAMP     NOT NULL DEFAULT NOW(),
    updated_at           TIMESTAMP     NOT NULL DEFAULT NOW(),
    version              BIGINT        NOT NULL DEFAULT 0,
    CONSTRAINT fk_proposta_credito_profissional
        FOREIGN KEY (profissional_id) REFERENCES profissional_de_base(profissional_id)
            ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_proposta_credito_profissional ON proposta_credito(profissional_id);
CREATE INDEX IF NOT EXISTS idx_proposta_credito_status       ON proposta_credito(status);
