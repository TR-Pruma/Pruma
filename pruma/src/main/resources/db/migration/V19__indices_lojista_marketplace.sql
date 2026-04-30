-- V19: Indices pendentes do lojista e marketplace
-- Versao idempotente via INFORMATION_SCHEMA (compativel com qualquer MySQL 8).
-- Cria o indice somente se ele ainda nao existir.

-- idx_lojista_taxa_ativa
SET @n1 = (SELECT COUNT(*) FROM information_schema.statistics
           WHERE table_schema = DATABASE()
             AND table_name   = 'lojista_parceiro'
             AND index_name   = 'idx_lojista_taxa_ativa');
SET @s1 = IF(@n1 = 0,
    'ALTER TABLE lojista_parceiro ADD INDEX idx_lojista_taxa_ativa (ativo, taxa_comissao_percentual)',
    'SELECT ''idx_lojista_taxa_ativa already exists -- skipped''');
PREPARE stmt1 FROM @s1;
EXECUTE stmt1;
DEALLOCATE PREPARE stmt1;

-- idx_pedido_lojista_status
SET @n2 = (SELECT COUNT(*) FROM information_schema.statistics
           WHERE table_schema = DATABASE()
             AND table_name   = 'pedido_marketplace'
             AND index_name   = 'idx_pedido_lojista_status');
SET @s2 = IF(@n2 = 0,
    'ALTER TABLE pedido_marketplace ADD INDEX idx_pedido_lojista_status (lojista_id, status)',
    'SELECT ''idx_pedido_lojista_status already exists -- skipped''');
PREPARE stmt2 FROM @s2;
EXECUTE stmt2;
DEALLOCATE PREPARE stmt2;
