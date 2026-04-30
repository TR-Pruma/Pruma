-- V19: Índices pendentes do lojista e marketplace
-- Estes indices faziam parte do V18 original mas nao foram criados
-- porque V18 falhou antes de chegar neles (idx_trajetoria_portfolio duplicado).

-- lojista_parceiro: filtro de take rate e lojistas ativos
ALTER TABLE lojista_parceiro
    ADD INDEX idx_lojista_taxa_ativa (ativo, taxa_comissao_percentual);

-- pedido_marketplace: consultas por lojista e status (painel do marketplace)
ALTER TABLE pedido_marketplace
    ADD INDEX idx_pedido_lojista_status (lojista_id, status);
