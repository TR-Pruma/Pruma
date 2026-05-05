-- =============================================================================
-- V37: ALINHA apadrinhamento_rede com ApadrinhamentoRede.java
--
-- O V35 criou a tabela com schema divergente da entidade Java:
--   PK: apadrinhamento_rede_id  ->  deve ser: apadrinhamento_id
--   FK: apadrinhado_id          ->  deve ser: afilhado_id
--   Faltam: status, data_fim
--
-- NOTA: MySQL nao suporta DROP FOREIGN KEY IF EXISTS.
-- As FKs abaixo sao as criadas exatamente pelo V35 para esta tabela.
-- Se o nome for diferente no seu banco, ajuste antes de rodar.
-- =============================================================================

SET FOREIGN_KEY_CHECKS = 0;

-- 1. Remove FKs existentes (nomes conforme criados pelo V35)
ALTER TABLE apadrinhamento_rede DROP FOREIGN KEY fk_apadr_padrinho;
ALTER TABLE apadrinhamento_rede DROP FOREIGN KEY fk_apadr_afilhado;

-- 2. Renomeia PK: apadrinhamento_rede_id -> apadrinhamento_id
ALTER TABLE apadrinhamento_rede
    CHANGE COLUMN apadrinhamento_rede_id apadrinhamento_id BIGINT NOT NULL AUTO_INCREMENT;

-- 3. Renomeia coluna: apadrinhado_id -> afilhado_id
ALTER TABLE apadrinhamento_rede
    CHANGE COLUMN apadrinhado_id afilhado_id INT NULL;

-- 4. Adiciona colunas faltantes (idempotente via IGNORE no SET)
ALTER TABLE apadrinhamento_rede
    ADD COLUMN data_fim DATE NULL,
    ADD COLUMN status   VARCHAR(20) NOT NULL DEFAULT 'ATIVO';

-- 5. Recria FKs com nomes corretos
ALTER TABLE apadrinhamento_rede
    ADD CONSTRAINT fk_apadr_padrinho
        FOREIGN KEY (padrinho_id) REFERENCES profissional_de_base (profissional_id),
    ADD CONSTRAINT fk_apadr_afilhado
        FOREIGN KEY (afilhado_id) REFERENCES profissional_de_base (profissional_id);

-- 6. Recria indices
ALTER TABLE apadrinhamento_rede
    ADD INDEX idx_apadrinhamento_padrinho (padrinho_id),
    ADD INDEX idx_apadrinhamento_afilhado (afilhado_id),
    ADD INDEX idx_apadrinhamento_status   (status);

SET FOREIGN_KEY_CHECKS = 1;
