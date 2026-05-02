-- V27: Converte avaliacao.nota de INT para DECIMAL(3,1)
-- Entidade mapeou nota como BigDecimal DECIMAL(3,1).
-- Banco ainda tem INT. Conversao sem perda de dados.

SET @col_type = (SELECT DATA_TYPE FROM information_schema.columns
                 WHERE table_schema = DATABASE()
                   AND table_name   = 'avaliacao'
                   AND column_name  = 'nota');
SET @s = IF(@col_type = 'int',
    'ALTER TABLE avaliacao MODIFY COLUMN nota DECIMAL(3,1) NULL',
    'SELECT 1');
PREPARE st FROM @s; EXECUTE st; DEALLOCATE PREPARE st;
