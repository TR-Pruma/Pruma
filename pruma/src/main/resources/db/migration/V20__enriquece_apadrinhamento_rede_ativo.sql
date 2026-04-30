-- V20: Adiciona coluna ativo em apadrinhamento_rede (idempotente)
-- Usa INFORMATION_SCHEMA para nao falhar se coluna ja existir.

SET @n = (SELECT COUNT(*) FROM information_schema.columns
          WHERE table_schema = DATABASE()
            AND table_name   = 'apadrinhamento_rede'
            AND column_name  = 'ativo');
SET @s = IF(@n = 0,
    'ALTER TABLE apadrinhamento_rede ADD COLUMN ativo TINYINT(1) NOT NULL DEFAULT 1',
    'SELECT ''ativo already exists in apadrinhamento_rede -- skipped''');
PREPARE stmt FROM @s;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
