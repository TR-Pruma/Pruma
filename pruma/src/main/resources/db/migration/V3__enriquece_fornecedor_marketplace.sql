-- V3: Enriquece fornecedor com campos para participação no Marketplace B2B

ALTER TABLE fornecedor
    ADD COLUMN IF NOT EXISTS parceiro_marketplace  BOOLEAN      NOT NULL DEFAULT FALSE,
    ADD COLUMN IF NOT EXISTS taxa_comissao         DECIMAL(5,4),
    ADD COLUMN IF NOT EXISTS categorias_produtos   TEXT,
    ADD COLUMN IF NOT EXISTS ativo                 BOOLEAN      NOT NULL DEFAULT TRUE;

CREATE INDEX IF NOT EXISTS idx_fornecedor_marketplace ON fornecedor(parceiro_marketplace);
