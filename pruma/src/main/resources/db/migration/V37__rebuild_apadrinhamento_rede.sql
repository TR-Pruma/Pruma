-- =============================================================================
-- V37: FINALIZA alinhamento de apadrinhamento_rede com ApadrinhamentoRede.java
--
-- Estado do banco apos V35 + V36:
--   PK:    apadrinhamento_rede_id  (V36 nao conseguiu renomear - FK impedia)
--   FKs:   fk_ar_padrinho, fk_ar_apadrinhado  (criadas pelo V35, nomes originais)
--   Cols:  padrinho_id, afilhado_id (V36 renomeou apadrinhado_id -> afilhado_id)
--   Faltam: apadrinhamento_id (rename da PK), status, data_fim
--
-- Este migration:
--   1. Dropa FKs (pelo nome exato criado no V35)
--   2. Renomeia PK: apadrinhamento_rede_id -> apadrinhamento_id
--   3. Adiciona colunas faltantes: status, data_fim
--   4. Recria FKs e indices
-- =============================================================================

SET FOREIGN_KEY_CHECKS = 0;

-- 1. Remove FKs (nomes conforme V35)
ALTER TABLE apadrinhamento_rede DROP FOREIGN KEY fk_ar_padrinho;
ALTER TABLE apadrinhamento_rede DROP FOREIGN KEY fk_ar_apadrinhado;

-- 2. Renomeia PK: apadrinhamento_rede_id -> apadrinhamento_id
ALTER TABLE apadrinhamento_rede
    CHANGE COLUMN apadrinhamento_rede_id apadrinhamento_id BIGINT NOT NULL AUTO_INCREMENT;

-- 3. Adiciona colunas faltantes
ALTER TABLE apadrinhamento_rede
    ADD COLUMN data_fim DATE         NULL,
    ADD COLUMN status   VARCHAR(20)  NOT NULL DEFAULT 'ATIVO';

-- 4. Recria FKs
ALTER TABLE apadrinhamento_rede
    ADD CONSTRAINT fk_apadr_padrinho
        FOREIGN KEY (padrinho_id) REFERENCES profissional_de_base (profissional_id),
    ADD CONSTRAINT fk_apadr_afilhado
        FOREIGN KEY (afilhado_id) REFERENCES profissional_de_base (profissional_id);

-- 5. Indices auxiliares
ALTER TABLE apadrinhamento_rede
    ADD INDEX idx_apadrinhamento_padrinho (padrinho_id),
    ADD INDEX idx_apadrinhamento_afilhado (afilhado_id),
    ADD INDEX idx_apadrinhamento_status   (status);

SET FOREIGN_KEY_CHECKS = 1;
