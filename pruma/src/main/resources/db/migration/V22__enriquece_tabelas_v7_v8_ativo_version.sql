-- V22: Adiciona ativo e version nas tabelas criadas por V7 e V8
-- Essas tabelas foram criadas antes do padrao ativo+version ser
-- estabelecido no projeto. As entidades JPA ja possuem esses campos.

-- interacao_campo_mor (V7)
ALTER TABLE interacao_campo_mor
    ADD COLUMN ativo    TINYINT(1) NOT NULL DEFAULT 1,
    ADD COLUMN version  BIGINT     NOT NULL DEFAULT 0;

-- pedido_marketplace (V8)
ALTER TABLE pedido_marketplace
    ADD COLUMN ativo    TINYINT(1) NOT NULL DEFAULT 1,
    ADD COLUMN version  BIGINT     NOT NULL DEFAULT 0;

-- item_pedido_marketplace (V8)
ALTER TABLE item_pedido_marketplace
    ADD COLUMN ativo    TINYINT(1) NOT NULL DEFAULT 1,
    ADD COLUMN version  BIGINT     NOT NULL DEFAULT 0;

-- proposta_credito (V8) -- version ja existe, apenas ativo faltava
ALTER TABLE proposta_credito
    ADD COLUMN ativo TINYINT(1) NOT NULL DEFAULT 1;
