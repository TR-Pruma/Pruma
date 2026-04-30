-- V21: Adiciona coluna version em apadrinhamento_rede
-- A entidade usa @Version (optimistic locking) mas V7 nao criava essa coluna.
-- Default 0 e compativel com todos os registros existentes.

ALTER TABLE apadrinhamento_rede
    ADD COLUMN version BIGINT NOT NULL DEFAULT 0;
