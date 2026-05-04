-- V32: Sincroniza tabela comunicacao com a entidade Comunicacao.java
-- Problema: Hibernate valida schema (ddl-auto=validate) e aponta colunas
--           ausentes: mensagem, cliente_id e tipo_remetente.
-- Solucao:  Adiciona as tres colunas e a FK para cliente.
--           Colunas recebem valor default temporario para nao violar NOT NULL
--           em ambientes com dados existentes; ajuste conforme necessidade.

ALTER TABLE comunicacao
    ADD COLUMN tipo_remetente VARCHAR(15)  NOT NULL DEFAULT 'SISTEMA'  COMMENT 'Ex: CLIENTE, PROFISSIONAL, SISTEMA',
    ADD COLUMN mensagem       TEXT         NOT NULL                     COMMENT 'Corpo da mensagem',
    ADD COLUMN cliente_id     INT          NOT NULL DEFAULT 0           COMMENT 'FK para cliente';

-- Remove os defaults temporarios usados apenas para a migracao
ALTER TABLE comunicacao
    ALTER COLUMN tipo_remetente DROP DEFAULT,
    ALTER COLUMN cliente_id     DROP DEFAULT;

-- Adiciona FK para cliente
ALTER TABLE comunicacao
    ADD CONSTRAINT fk_com_cliente
        FOREIGN KEY (cliente_id) REFERENCES cliente (cliente_id);

-- Indice auxiliar (consultas de comunicacoes por cliente/projeto sao comuns)
CREATE INDEX idx_comunicacao_cliente  ON comunicacao (cliente_id);
CREATE INDEX idx_comunicacao_remetente ON comunicacao (tipo_remetente);
