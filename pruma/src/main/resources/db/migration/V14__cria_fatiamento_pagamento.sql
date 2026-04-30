-- V14: fatiamento_pagamento — operador projetor P do documento técnico
-- Representa o split automático de cada pagamento de obra:
--   P(f) = τ_plataforma ⊕ τ_fornecedores ⊕ τ_dívida
--   (I-P)f = valor_liquido_trabalhador  (parcela residual ao trabalhador)
-- A norma ‖(I-P)f‖ < ‖f‖ para todo f≠0 (desigualdade de Bessel).
--
-- pagamento.pagamento_id é INT (conforme DER) — FK usa INT aqui.
-- Após criar a tabela, enriquece `pagamento` com modalidade e FK fatiamento.

CREATE TABLE fatiamento_pagamento (
    fatiamento_id               BIGINT          NOT NULL AUTO_INCREMENT,
    pagamento_id                INT             NOT NULL,
    obra_id                     INT             NOT NULL,
    profissional_id             INT             NOT NULL,
    valor_bruto                 DECIMAL(18,2)   NOT NULL COMMENT 'Valor total recebido pelo serviço',
    valor_plataforma            DECIMAL(18,2)   NOT NULL DEFAULT 0 COMMENT 'τ_plataforma — take rate',
    valor_fornecedores          DECIMAL(18,2)   NOT NULL DEFAULT 0 COMMENT 'τ_fornecedores — repasse automático',
    valor_deducao_divida        DECIMAL(18,2)   NOT NULL DEFAULT 0 COMMENT 'τ_dívida — dedução parcelas de crédito',
    valor_liquido_trabalhador   DECIMAL(18,2)   NOT NULL COMMENT '(I-P)f — parcela residual ao trabalhador',
    status_liquidacao           VARCHAR(20)     NOT NULL DEFAULT 'PENDENTE'
                                                COMMENT 'PENDENTE | PROCESSADO | ERRO',
    liquidado_em                TIMESTAMP       NULL,
    hash_transacao              VARCHAR(128)    NULL COMMENT 'Hash contrato inteligente / Drex',
    created_at                  TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at                  TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (fatiamento_id),
    CONSTRAINT fk_fatiamento_pagamento   FOREIGN KEY (pagamento_id)
        REFERENCES pagamento (pagamento_id)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_fatiamento_obra        FOREIGN KEY (obra_id)
        REFERENCES obra (obra_id)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_fatiamento_profissional FOREIGN KEY (profissional_id)
        REFERENCES profissional_de_base (profissional_id)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    INDEX idx_fatiamento_pagamento        (pagamento_id),
    INDEX idx_fatiamento_profissional     (profissional_id),
    INDEX idx_fatiamento_status_liquidacao (status_liquidacao, liquidado_em)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Enriquece pagamento com modalidade de liquidação e FK para o fatiamento
ALTER TABLE pagamento
    ADD COLUMN modalidade    VARCHAR(30) NULL
        COMMENT 'PIX | DREX | BOLETO | CONTRATO_INTELIGENTE'
        AFTER data_pagamento,
    ADD COLUMN fatiamento_id BIGINT      NULL
        COMMENT 'FK para fatiamento_pagamento'
        AFTER modalidade,
    ADD CONSTRAINT fk_pagamento_fatiamento FOREIGN KEY (fatiamento_id)
        REFERENCES fatiamento_pagamento (fatiamento_id)
        ON DELETE SET NULL ON UPDATE CASCADE,
    ADD INDEX idx_pagamento_fatiamento (fatiamento_id);
