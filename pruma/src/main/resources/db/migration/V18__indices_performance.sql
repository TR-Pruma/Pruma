-- V18: Índices de performance para queries críticas
-- Cobre os 5 padrões de acesso mais frequentes do sistema:
--   1. Score vigente de profissional     → score_tfe
--   2. Histórico de scores por data      → historico_score_tfe
--   3. Parcelas em atraso (cron diário)  → parcela_credito
--   4. Reconciliação de fatiamentos      → fatiamento_pagamento
--   5. Portfólio auditável do profissional → trajetoria_laboral
--   6. Pedidos marketplace por lojista   → pedido_marketplace

-- historico_score_tfe: série temporal ordenada por profissional + data
ALTER TABLE historico_score_tfe
    ADD INDEX idx_historico_score_prof_data (profissional_id, calculado_em);

-- parcela_credito: cron de cobrança — busca ATRASADAS com vencimento passado
ALTER TABLE parcela_credito
    ADD INDEX idx_parcela_cobranca_cron (status, data_vencimento);

-- fatiamento_pagamento: reconciliação financeira diária
ALTER TABLE fatiamento_pagamento
    ADD INDEX idx_fatiamento_reconciliacao (status_liquidacao, liquidado_em);

-- trajetoria_laboral: montagem de portfólio — obras concluídas mais recentes primeiro
ALTER TABLE trajetoria_laboral
    ADD INDEX idx_trajetoria_portfolio (profissional_id, obra_concluida, data_fim);

-- lojista_parceiro: filtro de take rate e lojistas ativos
ALTER TABLE lojista_parceiro
    ADD INDEX idx_lojista_taxa_ativa (ativo, taxa_comissao_percentual);

-- pedido_marketplace: consultas por lojista e status (painel do marketplace)
ALTER TABLE pedido_marketplace
    ADD INDEX idx_pedido_lojista_status (lojista_id, status);
