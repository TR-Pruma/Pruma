-- V29: Adiciona colunas de auditoria faltantes na tabela checklist
-- Entidade usa @CreationTimestamp (data_criacao), @UpdateTimestamp (data_atualizacao) e @Version

-- data_criacao
SET @n = (SELECT COUNT(*) FROM information_schema.columns
          WHERE table_schema = DATABASE() AND table_name = 'checklist' AND column_name = 'data_criacao');
SET @s = IF(@n = 0,
    'ALTER TABLE checklist ADD COLUMN data_criacao DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6)',
    'SELECT 1');
PREPARE st FROM @s; EXECUTE st; DEALLOCATE PREPARE st;

-- data_atualizacao
SET @n = (SELECT COUNT(*) FROM information_schema.columns
          WHERE table_schema = DATABASE() AND table_name = 'checklist' AND column_name = 'data_atualizacao');
SET @s = IF(@n = 0,
    'ALTER TABLE checklist ADD COLUMN data_atualizacao DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6)',
    'SELECT 1');
PREPARE st FROM @s; EXECUTE st; DEALLOCATE PREPARE st;

-- version
SET @n = (SELECT COUNT(*) FROM information_schema.columns
          WHERE table_schema = DATABASE() AND table_name = 'checklist' AND column_name = 'version');
SET @s = IF(@n = 0,
    'ALTER TABLE checklist ADD COLUMN version BIGINT NOT NULL DEFAULT 0',
    'SELECT 1');
PREPARE st FROM @s; EXECUTE st; DEALLOCATE PREPARE st;
