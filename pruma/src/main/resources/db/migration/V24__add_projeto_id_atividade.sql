-- V24: Adiciona projeto_id em atividade
-- Entidade Atividade foi refatorada para referenciar Projeto diretamente.
-- obra_id e responsavel permanecem no banco (colunas legadas, nao mapeadas).
-- Idempotente: ADD COLUMN e ADD FK condicionais via INFORMATION_SCHEMA.

-- 1. Adiciona coluna projeto_id (se nao existir)
SET @n = (SELECT COUNT(*) FROM information_schema.columns
          WHERE table_schema = DATABASE()
            AND table_name   = 'atividade'
            AND column_name  = 'projeto_id');
SET @s = IF(@n = 0,
    'ALTER TABLE atividade ADD COLUMN projeto_id INT NULL AFTER obra_id',
    'SELECT 1');
PREPARE st FROM @s; EXECUTE st; DEALLOCATE PREPARE st;

-- 2. Adiciona FK para projeto (se nao existir)
SET @n = (SELECT COUNT(*) FROM information_schema.table_constraints
          WHERE table_schema    = DATABASE()
            AND table_name      = 'atividade'
            AND constraint_name = 'fk_atividade_projeto'
            AND constraint_type = 'FOREIGN KEY');
SET @s = IF(@n = 0,
    'ALTER TABLE atividade ADD CONSTRAINT fk_atividade_projeto FOREIGN KEY (projeto_id) REFERENCES projeto (projeto_id)',
    'SELECT 1');
PREPARE st FROM @s; EXECUTE st; DEALLOCATE PREPARE st;
