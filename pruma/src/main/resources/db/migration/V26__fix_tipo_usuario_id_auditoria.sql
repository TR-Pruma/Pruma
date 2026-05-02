-- V26: Corrige coluna tipo_usuario em auditoria
-- V25 criou tipo_usuario como VARCHAR(50) mas o Hibernate espera INT
-- pois a FK referencia tipo_usuario_id (PK INT da tabela tipo_usuario).
-- Todos os passos sao condicionais (idempotente).

SET FOREIGN_KEY_CHECKS = 0;

-- 1. Renomeia tipo_usuario VARCHAR -> tipo_usuario_id INT (se ainda for VARCHAR)
SET @tem_varchar = (SELECT COUNT(*) FROM information_schema.columns
                    WHERE table_schema = DATABASE() AND table_name = 'auditoria'
                      AND column_name = 'tipo_usuario' AND DATA_TYPE = 'varchar');
SET @s = IF(@tem_varchar > 0,
    'ALTER TABLE auditoria CHANGE COLUMN tipo_usuario tipo_usuario_id INT NULL',
    'SELECT 1');
PREPARE st FROM @s; EXECUTE st; DEALLOCATE PREPARE st;

-- 2. Garante que tipo_usuario_id existe caso nao tenha vindo do passo anterior
SET @n = (SELECT COUNT(*) FROM information_schema.columns
          WHERE table_schema = DATABASE() AND table_name = 'auditoria'
            AND column_name = 'tipo_usuario_id');
SET @s = IF(@n = 0,
    'ALTER TABLE auditoria ADD COLUMN tipo_usuario_id INT NULL',
    'SELECT 1');
PREPARE st FROM @s; EXECUTE st; DEALLOCATE PREPARE st;

-- 3. Adiciona FK fk_auditoria_tipo_usuario (se nao existir)
SET @n = (SELECT COUNT(*) FROM information_schema.table_constraints
          WHERE table_schema = DATABASE() AND table_name = 'auditoria'
            AND constraint_name = 'fk_auditoria_tipo_usuario'
            AND constraint_type = 'FOREIGN KEY');
SET @s = IF(@n = 0,
    'ALTER TABLE auditoria ADD CONSTRAINT fk_auditoria_tipo_usuario FOREIGN KEY (tipo_usuario_id) REFERENCES tipo_usuario (tipo_usuario_id)',
    'SELECT 1');
PREPARE st FROM @s; EXECUTE st; DEALLOCATE PREPARE st;

SET FOREIGN_KEY_CHECKS = 1;
