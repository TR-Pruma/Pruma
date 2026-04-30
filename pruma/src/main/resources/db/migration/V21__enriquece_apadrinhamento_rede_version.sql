-- V21: Adiciona coluna version em apadrinhamento_rede (idempotente)
-- Usa INFORMATION_SCHEMA para nao falhar se coluna ja existir.

SET @n = (SELECT COUNT(*) FROM information_schema.columns
          WHERE table_schema = DATABASE()
            AND table_name   = 'apadrinhamento_rede'
            AND column_name  = 'version');
SET @s = IF(@n = 0,
    'ALTER TABLE apadrinhamento_rede ADD COLUMN version BIGINT NOT NULL DEFAULT 0',
    'SELECT ''version already exists in apadrinhamento_rede -- skipped''');
PREPARE stmt FROM @s;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
