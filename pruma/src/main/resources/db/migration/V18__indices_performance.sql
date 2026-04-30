-- V18: Índices de performance para queries críticas
-- Cobre os padrões de acesso mais frequentes do sistema.
--
-- HISTÓRICO DE EXECUÇÃO:
--   idx_historico_score_prof_data   → criado na execução parcial anterior ✅
--   idx_parcela_cobranca_cron       → criado na execução parcial anterior ✅
--   idx_fatiamento_reconciliacao    → criado na execução parcial anterior ✅
--   idx_trajetoria_portfolio        → absorvido pelo V12 inline no CREATE TABLE ✅
--
-- Índices pendentes (criados aqui):

-- lojista_parceiro: filtro de take rate e lojistas ativos
ALTER TABLE lojista_parceiro
    ADD INDEX idx_lojista_taxa_ativa (ativo, taxa_comissao_percentual);

-- pedido_marketplace: consultas por lojista e status (painel do marketplace)
ALTER TABLE pedido_marketplace
    ADD INDEX idx_pedido_lojista_status (lojista_id, status);
