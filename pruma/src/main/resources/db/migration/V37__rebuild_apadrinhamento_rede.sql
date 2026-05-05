-- =============================================================================
-- V37: ALINHA apadrinhamento_rede com ApadrinhamentoRede.java
--
-- O V35 criou a tabela com schema divergente da entidade Java:
--   PK: apadrinhamento_rede_id  ->  deve ser: apadrinhamento_id
--   FK: apadrinhado_id          ->  deve ser: afilhado_id
--   Faltam: status, data_fim
--
-- Estrategia: ALTER TABLE puro, sem procedures, sem dynamic SQL.
-- Compativel com MySQL 5.7+
-- =============================================================================

SET FOREIGN_KEY_CHECKS = 0;

-- 1. Remove FKs existentes (necessario antes de renomear colunas referenciadas)
ALTER TABLE apadrinhamento_rede
    DROP FOREIGN KEY IF EXISTS fk_ar_padrinho,
    DROP FOREIGN KEY IF EXISTS fk_ar_apadrinhado,
    DROP FOREIGN KEY IF EXISTS fk_apadr_padrinho,
    DROP FOREIGN KEY IF EXISTS fk_apadr_afilhado,
    DROP FOREIGN KEY IF EXISTS fk_apadrinhamento_padrinho,
    DROP FOREIGN KEY IF EXISTS fk_apadrinhamento_afilhado;

-- 2. Renomeia PK: apadrinhamento_rede_id -> apadrinhamento_id
ALTER TABLE apadrinhamento_rede
    CHANGE COLUMN apadrinhamento_rede_id apadrinhamento_id BIGINT NOT NULL AUTO_INCREMENT;

-- 3. Renomeia FK de afilhado: apadrinhado_id -> afilhado_id
ALTER TABLE apadrinhamento_rede
    CHANGE COLUMN apadrinhado_id afilhado_id INT NULL;

-- 4. Adiciona colunas faltantes
ALTER TABLE apadrinhamento_rede
    ADD COLUMN IF NOT EXISTS data_fim DATE NULL,
    ADD COLUMN IF NOT EXISTS status   VARCHAR(20) NOT NULL DEFAULT 'ATIVO';

-- 5. Recria FKs
ALTER TABLE apadrinhamento_rede
    ADD CONSTRAINT fk_apadr_padrinho
        FOREIGN KEY (padrinho_id) REFERENCES profissional_de_base (profissional_id),
    ADD CONSTRAINT fk_apadr_afilhado
        FOREIGN KEY (afilhado_id) REFERENCES profissional_de_base (profissional_id);

-- 6. Recria indices (DROP IF EXISTS + CREATE para idempotencia)
DROP INDEX IF EXISTS idx_apadrinhamento_padrinho ON apadrinhamento_rede;
DROP INDEX IF EXISTS idx_apadrinhamento_afilhado ON apadrinhamento_rede;
DROP INDEX IF EXISTS idx_apadrinhamento_status   ON apadrinhamento_rede;

CREATE INDEX idx_apadrinhamento_padrinho ON apadrinhamento_rede (padrinho_id);
CREATE INDEX idx_apadrinhamento_afilhado ON apadrinhamento_rede (afilhado_id);
CREATE INDEX idx_apadrinhamento_status   ON apadrinhamento_rede (status);

SET FOREIGN_KEY_CHECKS = 1;
