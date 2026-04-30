-- V22: Adiciona ativo/version nas tabelas de V7 e V8 (idempotente)
-- Usa INFORMATION_SCHEMA para nao falhar se colunas ja existirem.

-- Helper macro: verifica existencia de coluna
-- interacao_campo_mor -> ativo
SET @n = (SELECT COUNT(*) FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='interacao_campo_mor' AND column_name='ativo');
SET @s = IF(@n=0,'ALTER TABLE interacao_campo_mor ADD COLUMN ativo TINYINT(1) NOT NULL DEFAULT 1','SELECT "skip"');
PREPARE st FROM @s; EXECUTE st; DEALLOCATE PREPARE st;

-- interacao_campo_mor -> version
SET @n = (SELECT COUNT(*) FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='interacao_campo_mor' AND column_name='version');
SET @s = IF(@n=0,'ALTER TABLE interacao_campo_mor ADD COLUMN version BIGINT NOT NULL DEFAULT 0','SELECT "skip"');
PREPARE st FROM @s; EXECUTE st; DEALLOCATE PREPARE st;

-- pedido_marketplace -> ativo
SET @n = (SELECT COUNT(*) FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='pedido_marketplace' AND column_name='ativo');
SET @s = IF(@n=0,'ALTER TABLE pedido_marketplace ADD COLUMN ativo TINYINT(1) NOT NULL DEFAULT 1','SELECT "skip"');
PREPARE st FROM @s; EXECUTE st; DEALLOCATE PREPARE st;

-- pedido_marketplace -> version
SET @n = (SELECT COUNT(*) FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='pedido_marketplace' AND column_name='version');
SET @s = IF(@n=0,'ALTER TABLE pedido_marketplace ADD COLUMN version BIGINT NOT NULL DEFAULT 0','SELECT "skip"');
PREPARE st FROM @s; EXECUTE st; DEALLOCATE PREPARE st;

-- item_pedido_marketplace -> ativo
SET @n = (SELECT COUNT(*) FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='item_pedido_marketplace' AND column_name='ativo');
SET @s = IF(@n=0,'ALTER TABLE item_pedido_marketplace ADD COLUMN ativo TINYINT(1) NOT NULL DEFAULT 1','SELECT "skip"');
PREPARE st FROM @s; EXECUTE st; DEALLOCATE PREPARE st;

-- item_pedido_marketplace -> version
SET @n = (SELECT COUNT(*) FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='item_pedido_marketplace' AND column_name='version');
SET @s = IF(@n=0,'ALTER TABLE item_pedido_marketplace ADD COLUMN version BIGINT NOT NULL DEFAULT 0','SELECT "skip"');
PREPARE st FROM @s; EXECUTE st; DEALLOCATE PREPARE st;

-- proposta_credito -> ativo
SET @n = (SELECT COUNT(*) FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='proposta_credito' AND column_name='ativo');
SET @s = IF(@n=0,'ALTER TABLE proposta_credito ADD COLUMN ativo TINYINT(1) NOT NULL DEFAULT 1','SELECT "skip"');
PREPARE st FROM @s; EXECUTE st; DEALLOCATE PREPARE st;
