-- V4: Enriquece pagamento com campos de liquidação atômica (Fintech / Drex)

ALTER TABLE pagamento
    ADD COLUMN IF NOT EXISTS origem_pagamento           VARCHAR(20),
    ADD COLUMN IF NOT EXISTS meio_liquidacao            VARCHAR(20),
    ADD COLUMN IF NOT EXISTS valor_bruto                DECIMAL(18,2),
    ADD COLUMN IF NOT EXISTS taxa_plataforma            DECIMAL(18,2),
    ADD COLUMN IF NOT EXISTS parcela_credito            DECIMAL(18,2),
    ADD COLUMN IF NOT EXISTS valor_liquido_trabalhador  DECIMAL(18,2),
    ADD COLUMN IF NOT EXISTS status_liquidacao          VARCHAR(20) NOT NULL DEFAULT 'PENDENTE',
    ADD COLUMN IF NOT EXISTS id_transacao_externa       VARCHAR(100);

CREATE INDEX IF NOT EXISTS idx_pagamento_status_liq ON pagamento(status_liquidacao);
CREATE INDEX IF NOT EXISTS idx_pagamento_origem     ON pagamento(origem_pagamento);
