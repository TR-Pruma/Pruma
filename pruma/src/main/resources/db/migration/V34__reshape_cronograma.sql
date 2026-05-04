-- V34: Sincroniza tabela cronograma com Cronograma.java
-- Entidade exige: cronograma_id, nome, projeto_id, data_inicio, data_fim + auditoria
-- O schema atual nao possui: nome, data_fim
-- Estrategia: ADD COLUMN para nao perder dados; constraints/indices recreados no final

-- 1. Adiciona colunas faltantes
ALTER TABLE cronograma
    ADD COLUMN nome       VARCHAR(255) NOT NULL DEFAULT 'Cronograma' COMMENT 'Nome do cronograma',
    ADD COLUMN data_fim   DATE         NOT NULL DEFAULT '2099-12-31' COMMENT 'Data de termino prevista';

-- Remove defaults temporarios
ALTER TABLE cronograma
    ALTER COLUMN nome     DROP DEFAULT,
    ALTER COLUMN data_fim DROP DEFAULT;

-- 2. Unique constraint exigida pela entidade: (projeto_id, data_inicio, data_fim)
--    Adiciona apenas se nao existir
ALTER TABLE cronograma
    ADD CONSTRAINT uk_cronograma_projeto_periodo
        UNIQUE (projeto_id, data_inicio, data_fim);

-- 3. Indices declarados na entidade via @Index
--    idx_cronograma_projeto ja pode existir desde V1 — cria com IF NOT EXISTS
CREATE INDEX IF NOT EXISTS idx_cronograma_projeto ON cronograma (projeto_id);
CREATE INDEX IF NOT EXISTS idx_cronograma_periodo  ON cronograma (data_inicio, data_fim);
