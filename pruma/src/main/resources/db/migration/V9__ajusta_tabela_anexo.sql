-- 1. Remove a FK existente
ALTER TABLE anexo
DROP FOREIGN KEY fk_anexo_documento;

-- 2. Renomeia documento_id → projeto_id
ALTER TABLE anexo
    CHANGE COLUMN documento_id projeto_id INT NOT NULL;

-- 3. Recria a FK apontando para a tabela projeto
ALTER TABLE anexo
    ADD CONSTRAINT fk_anexo_projeto
        FOREIGN KEY (projeto_id) REFERENCES projeto(projeto_id);

-- 4. Adiciona a coluna tipo_anexo que falta
ALTER TABLE anexo
    ADD COLUMN tipo_anexo VARCHAR(15) NOT NULL DEFAULT 'outros'
    AFTER projeto_id;

-- 5. Adiciona índice em tipo_anexo
ALTER TABLE anexo
    ADD INDEX idx_anexo_tipo (tipo_anexo);

-- 6. Corrige version de INT para BIGINT (entidade usa Long)
ALTER TABLE anexo
    MODIFY COLUMN version BIGINT NULL;