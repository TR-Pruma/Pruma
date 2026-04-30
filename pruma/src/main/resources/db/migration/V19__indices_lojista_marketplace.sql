-- V19: Indices pendentes do lojista e marketplace
-- Estes indices faziam parte do V18 original mas nao foram criados
-- porque V18 falhou antes de chegar neles (idx_trajetoria_portfolio duplicado).
-- Versao idempotente: DROP IF EXISTS antes de cada criacao.

-- lojista_parceiro: filtro de take rate e lojistas ativos
DROP INDEX IF EXISTS idx_lojista_taxa_ativa ON lojista_parceiro;
ALTER TABLE lojista_parceiro
    ADD INDEX idx_lojista_taxa_ativa (ativo, taxa_comissao_percentual);

-- pedido_marketplace: consultas por lojista e status (painel do marketplace)
DROP INDEX IF EXISTS idx_pedido_lojista_status ON pedido_marketplace;
ALTER TABLE pedido_marketplace
    ADD INDEX idx_pedido_lojista_status (lojista_id, status);
