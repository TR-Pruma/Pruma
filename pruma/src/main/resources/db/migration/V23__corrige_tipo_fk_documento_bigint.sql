-- V23: Converte documento_id de INT para BIGINT (idempotente)
-- MySQL 8 nao aceita ALTER TABLE MODIFY numa coluna referenciada por FK
-- mesmo com FOREIGN_KEY_CHECKS=0. Ordem obrigatoria:
--   1) drop FKs que apontam para documento_id
--   2) muda PK documento_id para BIGINT
--   3) muda FK columns para BIGINT
--   4) recria FKs
-- Todas as etapas condicionais para suportar re-execucao.

SET FOREIGN_KEY_CHECKS = 0;

-- 1a. Drop FK fk_assin_documento em assinatura_digital
SET @n = (SELECT COUNT(*) FROM information_schema.table_constraints
          WHERE table_schema = DATABASE() AND table_name = 'assinatura_digital'
            AND constraint_name = 'fk_assin_documento' AND constraint_type = 'FOREIGN KEY');
SET @s = IF(@n > 0,
    'ALTER TABLE assinatura_digital DROP FOREIGN KEY fk_assin_documento',
    'SELECT ''fk_assin_documento ja dropada -- skip''');
PREPARE st FROM @s; EXECUTE st; DEALLOCATE PREPARE st;

-- 1b. Drop FK fk_anexo_documento em anexo
SET @n = (SELECT COUNT(*) FROM information_schema.table_constraints
          WHERE table_schema = DATABASE() AND table_name = 'anexo'
            AND constraint_name = 'fk_anexo_documento' AND constraint_type = 'FOREIGN KEY');
SET @s = IF(@n > 0,
    'ALTER TABLE anexo DROP FOREIGN KEY fk_anexo_documento',
    'SELECT ''fk_anexo_documento ja dropada -- skip''');
PREPARE st FROM @s; EXECUTE st; DEALLOCATE PREPARE st;

-- 2. Muda PK documento.documento_id para BIGINT (agora sem FKs apontando)
ALTER TABLE documento
    MODIFY COLUMN documento_id BIGINT NOT NULL AUTO_INCREMENT;

-- 3a. Muda FK column em assinatura_digital para BIGINT
ALTER TABLE assinatura_digital
    MODIFY COLUMN documento_id BIGINT NULL;

-- 3b. Muda FK column em anexo para BIGINT
ALTER TABLE anexo
    MODIFY COLUMN documento_id BIGINT NULL;

-- 4a. Recria FK em assinatura_digital
SET @n = (SELECT COUNT(*) FROM information_schema.table_constraints
          WHERE table_schema = DATABASE() AND table_name = 'assinatura_digital'
            AND constraint_name = 'fk_assin_documento' AND constraint_type = 'FOREIGN KEY');
SET @s = IF(@n = 0,
    'ALTER TABLE assinatura_digital ADD CONSTRAINT fk_assin_documento FOREIGN KEY (documento_id) REFERENCES documento (documento_id)',
    'SELECT ''fk_assin_documento ja existe -- skip''');
PREPARE st FROM @s; EXECUTE st; DEALLOCATE PREPARE st;

-- 4b. Recria FK em anexo
SET @n = (SELECT COUNT(*) FROM information_schema.table_constraints
          WHERE table_schema = DATABASE() AND table_name = 'anexo'
            AND constraint_name = 'fk_anexo_documento' AND constraint_type = 'FOREIGN KEY');
SET @s = IF(@n = 0,
    'ALTER TABLE anexo ADD CONSTRAINT fk_anexo_documento FOREIGN KEY (documento_id) REFERENCES documento (documento_id)',
    'SELECT ''fk_anexo_documento ja existe -- skip''');
PREPARE st FROM @s; EXECUTE st; DEALLOCATE PREPARE st;

SET FOREIGN_KEY_CHECKS = 1;
