-- =============================================================================
-- V2__reshape_comunicacao.sql
-- Alinha a tabela `comunicacao` com a entidade Comunicacao.java.
--
-- Entidade exige:
--   - cliente_id  INT NOT NULL  (ManyToOne -> cliente)
--   - tipo_remetente VARCHAR(15) NOT NULL
--   - mensagem TEXT NOT NULL
--
-- Schema atual (V1) possuía:
--   - usuario_id  INT  (FK diferente — removida)
--   - assunto     VARCHAR(255)  (removida)
--   - conteudo    TEXT  (renomeada para mensagem)
-- =============================================================================

-- 1. Remover FK e coluna usuario_id (substituída por cliente_id)
ALTER TABLE comunicacao
    DROP FOREIGN KEY fk_com_usuario,
    DROP COLUMN usuario_id;

-- 2. Remover coluna assunto (não existe mais na entidade)
ALTER TABLE comunicacao
    DROP COLUMN assunto;

-- 3. Renomear conteudo -> mensagem (mantém dados existentes)
ALTER TABLE comunicacao
    RENAME COLUMN conteudo TO mensagem;

-- 4. Garantir NOT NULL em mensagem (coluna vazia vira string vazia transitoriamente)
UPDATE comunicacao SET mensagem = '' WHERE mensagem IS NULL;
ALTER TABLE comunicacao
    MODIFY COLUMN mensagem TEXT NOT NULL;

-- 5. Adicionar tipo_remetente
ALTER TABLE comunicacao
    ADD COLUMN tipo_remetente VARCHAR(15) NOT NULL DEFAULT 'SISTEMA' AFTER projeto_id;

-- Remover default após adição (campo deve ser preenchido pela aplicação)
ALTER TABLE comunicacao
    ALTER COLUMN tipo_remetente DROP DEFAULT;

-- 6. Adicionar cliente_id NOT NULL com default temporário 0
ALTER TABLE comunicacao
    ADD COLUMN cliente_id INT NOT NULL DEFAULT 0 AFTER tipo_remetente;

-- ATENÇÃO: se existirem linhas em comunicacao com cliente_id = 0,
-- elas vão violar a FK abaixo. Limpe-as antes de prosseguir:
--   DELETE FROM comunicacao WHERE cliente_id = 0;
-- ou atualize para um cliente válido:
--   UPDATE comunicacao SET cliente_id = <id_valido> WHERE cliente_id = 0;

-- 7. Remover default temporário e adicionar FK
ALTER TABLE comunicacao
    ALTER COLUMN cliente_id DROP DEFAULT,
    ADD CONSTRAINT fk_com_cliente
        FOREIGN KEY (cliente_id) REFERENCES cliente (cliente_id);

-- 8. Índices auxiliares
CREATE INDEX idx_com_cliente       ON comunicacao (cliente_id);
CREATE INDEX idx_com_tipo_remetente ON comunicacao (tipo_remetente);
