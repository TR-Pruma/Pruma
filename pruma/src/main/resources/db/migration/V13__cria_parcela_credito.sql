-- V13: parcela_credito — amortização das propostas de crédito aprovadas
-- proposta_credito.numero_parcelas define quantas linhas existem aqui.
-- UNIQUE(proposta_credito_id, numero_parcela) garante integridade da sequência.
-- O cron de cobrança usa idx_parcela_cobranca para buscar ATRASADAS diariamente.
-- O fatiamento_pagamento (V14) deduz parcelas automaticamente via deducao_divida.

CREATE TABLE parcela_credito (
    parcela_credito_id      BIGINT          NOT NULL AUTO_INCREMENT,
    proposta_credito_id     BIGINT          NOT NULL,
    numero_parcela          INT             NOT NULL COMMENT 'Sequência: 1, 2, 3…',
    valor_parcela           DECIMAL(18,2)   NOT NULL,
    data_vencimento         DATE            NOT NULL,
    data_pagamento          DATE            NULL     COMMENT 'NULL = não paga ainda',
    valor_pago              DECIMAL(18,2)   NULL,
    status                  VARCHAR(20)     NOT NULL DEFAULT 'PENDENTE'
                                            COMMENT 'PENDENTE | PAGA | ATRASADA | CANCELADA',
    modalidade_pagamento    VARCHAR(30)     NULL
                                            COMMENT 'PIX | DREX | BOLETO | CONTRATO_INTELIGENTE',
    created_at              TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at              TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (parcela_credito_id),
    CONSTRAINT uq_parcela_sequencia UNIQUE (proposta_credito_id, numero_parcela),
    CONSTRAINT fk_parcela_proposta FOREIGN KEY (proposta_credito_id)
        REFERENCES proposta_credito (proposta_credito_id)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    INDEX idx_parcela_proposta   (proposta_credito_id),
    INDEX idx_parcela_cobranca   (status, data_vencimento),
    INDEX idx_parcela_status     (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
