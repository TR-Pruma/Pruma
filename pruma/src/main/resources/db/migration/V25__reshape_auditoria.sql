-- V25: Reshape tabela auditoria para novo design da entidade
-- Entidade foi redesenhada: PK vira UUID, novos campos de negocio.
-- Colunas legadas (usuario_id, entidade, entidade_id, detalhe) permanecem
-- no banco sem mapeamento -- nao causam erro no Hibernate validate.
-- Todos os passos sao condicionais (idempotente).

SET FOREIGN_KEY_CHECKS = 0;

-- 1. Muda auditoria_id de INT AUTO_INCREMENT para BINARY(16)
SET @col_type = (SELECT COLUMN_TYPE FROM information_schema.columns
                 WHERE table_schema = DATABASE()
                   AND table_name   = 'auditoria'
                   AND column_name  = 'auditoria_id');
SET @s = IF(@col_type != 'binary(16)',
    'ALTER TABLE auditoria MODIFY COLUMN auditoria_id BINARY(16) NOT NULL',
    'SELECT 1');
PREPARE st FROM @s; EXECUTE st; DEALLOCATE PREPARE st;

-- 2. Adiciona cliente_cpf (se nao existir)
SET @n = (SELECT COUNT(*) FROM information_schema.columns
          WHERE table_schema = DATABASE() AND table_name = 'auditoria' AND column_name = 'cliente_cpf');
SET @s = IF(@n = 0,
    'ALTER TABLE auditoria ADD COLUMN cliente_cpf VARCHAR(11) NULL',
    'SELECT 1');
PREPARE st FROM @s; EXECUTE st; DEALLOCATE PREPARE st;

-- 3. Adiciona tipo_usuario (coluna FK, se nao existir)
SET @n = (SELECT COUNT(*) FROM information_schema.columns
          WHERE table_schema = DATABASE() AND table_name = 'auditoria' AND column_name = 'tipo_usuario');
SET @s = IF(@n = 0,
    'ALTER TABLE auditoria ADD COLUMN tipo_usuario VARCHAR(50) NULL',
    'SELECT 1');
PREPARE st FROM @s; EXECUTE st; DEALLOCATE PREPARE st;

-- 4. Adiciona data_hora (se nao existir)
SET @n = (SELECT COUNT(*) FROM information_schema.columns
          WHERE table_schema = DATABASE() AND table_name = 'auditoria' AND column_name = 'data_hora');
SET @s = IF(@n = 0,
    'ALTER TABLE auditoria ADD COLUMN data_hora DATETIME(6) NULL',
    'SELECT 1');
PREPARE st FROM @s; EXECUTE st; DEALLOCATE PREPARE st;

-- 5. Modifica acao de VARCHAR(100) para TEXT (se ainda nao for TEXT/MEDIUMTEXT/LONGTEXT)
SET @col_type = (SELECT DATA_TYPE FROM information_schema.columns
                 WHERE table_schema = DATABASE() AND table_name = 'auditoria' AND column_name = 'acao');
SET @s = IF(@col_type = 'varchar',
    'ALTER TABLE auditoria MODIFY COLUMN acao TEXT NULL',
    'SELECT 1');
PREPARE st FROM @s; EXECUTE st; DEALLOCATE PREPARE st;

-- 6. Indice idx_auditoria_cpf (se nao existir)
SET @n = (SELECT COUNT(*) FROM information_schema.statistics
          WHERE table_schema = DATABASE() AND table_name = 'auditoria' AND index_name = 'idx_auditoria_cpf');
SET @s = IF(@n = 0,
    'ALTER TABLE auditoria ADD INDEX idx_auditoria_cpf (cliente_cpf)',
    'SELECT 1');
PREPARE st FROM @s; EXECUTE st; DEALLOCATE PREPARE st;

-- 7. Indice idx_auditoria_data (se nao existir)
SET @n = (SELECT COUNT(*) FROM information_schema.statistics
          WHERE table_schema = DATABASE() AND table_name = 'auditoria' AND index_name = 'idx_auditoria_data');
SET @s = IF(@n = 0,
    'ALTER TABLE auditoria ADD INDEX idx_auditoria_data (data_hora)',
    'SELECT 1');
PREPARE st FROM @s; EXECUTE st; DEALLOCATE PREPARE st;

SET FOREIGN_KEY_CHECKS = 1;
