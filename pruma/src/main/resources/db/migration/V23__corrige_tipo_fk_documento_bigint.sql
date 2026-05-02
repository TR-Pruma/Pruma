-- V23: Converte documento_id de INT para BIGINT
-- Apenas documento e assinatura_digital sao afetadas.
-- anexo NAO tem coluna documento_id (tem projeto_id) - nao entra aqui.
-- FK fk_assin_documento pode nao existir (falhas anteriores de V23
-- podem ter dropado sem recriar), por isso todos os passos sao condicionais.

SET FOREIGN_KEY_CHECKS = 0;

-- 1. Drop FK fk_assin_documento em assinatura_digital (se existir)
SET @n = (SELECT COUNT(*) FROM information_schema.table_constraints
          WHERE table_schema = DATABASE() AND table_name = 'assinatura_digital'
            AND constraint_name = 'fk_assin_documento' AND constraint_type = 'FOREIGN KEY');
SET @s = IF(@n > 0,
    'ALTER TABLE assinatura_digital DROP FOREIGN KEY fk_assin_documento',
    'SELECT 1');
PREPARE st FROM @s; EXECUTE st; DEALLOCATE PREPARE st;

-- 2. Muda PK documento.documento_id para BIGINT
ALTER TABLE documento
    MODIFY COLUMN documento_id BIGINT NOT NULL AUTO_INCREMENT;

-- 3. Muda FK column assinatura_digital.documento_id para BIGINT
ALTER TABLE assinatura_digital
    MODIFY COLUMN documento_id BIGINT NULL;

-- 4. Recria FK fk_assin_documento (se nao existir)
SET @n = (SELECT COUNT(*) FROM information_schema.table_constraints
          WHERE table_schema = DATABASE() AND table_name = 'assinatura_digital'
            AND constraint_name = 'fk_assin_documento' AND constraint_type = 'FOREIGN KEY');
SET @s = IF(@n = 0,
    'ALTER TABLE assinatura_digital ADD CONSTRAINT fk_assin_documento FOREIGN KEY (documento_id) REFERENCES documento (documento_id)',
    'SELECT 1');
PREPARE st FROM @s; EXECUTE st; DEALLOCATE PREPARE st;

SET FOREIGN_KEY_CHECKS = 1;
