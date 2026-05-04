-- V33: Adiciona colunas ausentes na tabela anexo para alinhar com a entidade Anexo.java
-- Causa: Schema-validation: missing column [tipo_anexo] in table [anexo]

-- 1. Coluna obrigatória: tipo_anexo VARCHAR(15) NOT NULL
ALTER TABLE anexo
    ADD COLUMN IF NOT EXISTS tipo_anexo VARCHAR(15) NOT NULL DEFAULT 'OUTROS';

-- Remove o default temporário (não deve ser mantido em produção)
ALTER TABLE anexo
    ALTER COLUMN tipo_anexo DROP DEFAULT;

-- 2. Coluna obrigatória: caminho VARCHAR(1024) NOT NULL
ALTER TABLE anexo
    ADD COLUMN IF NOT EXISTS caminho VARCHAR(1024) NOT NULL DEFAULT '';

ALTER TABLE anexo
    ALTER COLUMN caminho DROP DEFAULT;

-- 3. Coluna opcional: nome VARCHAR(255)
ALTER TABLE anexo
    ADD COLUMN IF NOT EXISTS nome VARCHAR(255);

-- 4. Coluna opcional: tipo_mime VARCHAR(100)
ALTER TABLE anexo
    ADD COLUMN IF NOT EXISTS tipo_mime VARCHAR(100);

-- 5. Coluna opcional: tamanho BIGINT
ALTER TABLE anexo
    ADD COLUMN IF NOT EXISTS tamanho BIGINT;

-- 6. Índice auxiliar em tipo_anexo (já declarado na entidade via @Index)
CREATE INDEX IF NOT EXISTS idx_anexo_tipo ON anexo (tipo_anexo);
