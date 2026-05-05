-- =============================================================================
-- V4 - FKs diferidas e índices complementares
-- =============================================================================

-- Índices em trajetoria_laboral (FKs adicionadas em V3)
CREATE INDEX idx_trajetoria_profissional ON trajetoria_laboral(profissional_id);
CREATE INDEX idx_trajetoria_obra         ON trajetoria_laboral(obra_id);
CREATE INDEX idx_trajetoria_projeto      ON trajetoria_laboral(projeto_id);

-- Índices adicionais de performance
CREATE INDEX idx_score_tfe_calculado           ON score_tfe(calculado_em);
CREATE INDEX idx_historico_score_calculado     ON historico_score_tfe(calculado_em);
CREATE INDEX idx_proposta_credito_data         ON proposta_credito(created_at);
CREATE INDEX idx_fatiamento_status             ON fatiamento_pagamento(status_liquidacao);
CREATE INDEX idx_notificacao_data              ON notificacao(data_hora);
CREATE INDEX idx_mensagem_cliente              ON mensagem_instantanea(cliente_cpf);
CREATE INDEX idx_comunicacao_projeto           ON comunicacao(projeto_id);
CREATE INDEX idx_comunicacao_cliente           ON comunicacao(cliente_id);
CREATE INDEX idx_log_cliente                   ON log_alteracao(cliente_cpf);
CREATE INDEX idx_imagem_exif_manipulada        ON imagem_projeto(exif_manipulada);
CREATE INDEX idx_interacao_status              ON interacao_campo_mor(status_processamento);
