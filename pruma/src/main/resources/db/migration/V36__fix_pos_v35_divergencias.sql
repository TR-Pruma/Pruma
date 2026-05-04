-- =============================================================================
-- V36: CORRECOES RESIDUAIS POS-V35
-- Garante que bancos que passaram por ddl=update (Opcao A) antes do V35
-- estejam 100% alinhados com as entidades Java do branch feature/novos_services.
-- Todos os ALTER TABLE sao idemopotentes (IF EXISTS / IF NOT EXISTS).
-- =============================================================================

-- ---------------------------------------------------------------------------
-- comunicacao: garante colunas adicionadas pelas entidades novas
-- ---------------------------------------------------------------------------
ALTER TABLE comunicacao
    MODIFY COLUMN comunicacao_id INT NOT NULL AUTO_INCREMENT,
    MODIFY COLUMN projeto_id     INT NOT NULL,
    ADD COLUMN IF NOT EXISTS cliente_id     INT         NOT NULL DEFAULT 0,
    ADD COLUMN IF NOT EXISTS mensagem       TEXT        NOT NULL,
    ADD COLUMN IF NOT EXISTS tipo_remetente VARCHAR(15) NOT NULL DEFAULT 'SISTEMA';

-- Remove defaults temporarios apos a insercao das colunas
ALTER TABLE comunicacao
    ALTER COLUMN cliente_id     DROP DEFAULT,
    ALTER COLUMN tipo_remetente DROP DEFAULT;

-- FK comunicacao -> cliente (idempotente)
SET @fk_exists = (
    SELECT COUNT(*) FROM information_schema.TABLE_CONSTRAINTS
    WHERE CONSTRAINT_SCHEMA = DATABASE()
      AND TABLE_NAME        = 'comunicacao'
      AND CONSTRAINT_NAME   = 'fk_com_cliente'
      AND CONSTRAINT_TYPE   = 'FOREIGN KEY'
);
SET @sql = IF(@fk_exists = 0,
    'ALTER TABLE comunicacao ADD CONSTRAINT fk_com_cliente FOREIGN KEY (cliente_id) REFERENCES cliente (cliente_id)',
    'SELECT 1'
);
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- Indices de performance para comunicacao
CREATE INDEX IF NOT EXISTS idx_com_cliente   ON comunicacao (cliente_id);
CREATE INDEX IF NOT EXISTS idx_com_remetente ON comunicacao (tipo_remetente);

-- ---------------------------------------------------------------------------
-- comunicacao_aux: tabela auxiliar 1:1 com @MapsId
-- ---------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS comunicacao_aux (
    comunicacao_id INT         NOT NULL,
    tipo_mensagem  VARCHAR(30) NOT NULL,
    created_at     DATETIME(6) NOT NULL,
    updated_at     DATETIME(6),
    ativo          TINYINT(1)  NOT NULL DEFAULT 1,
    version        BIGINT,
    PRIMARY KEY (comunicacao_id),
    CONSTRAINT fk_ca_comunicacao FOREIGN KEY (comunicacao_id) REFERENCES comunicacao (comunicacao_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ---------------------------------------------------------------------------
-- anexo: colunas adicionadas pela entidade Anexo.java
-- ---------------------------------------------------------------------------
ALTER TABLE anexo
    ADD COLUMN IF NOT EXISTS tipo_anexo   VARCHAR(50)  NULL,
    ADD COLUMN IF NOT EXISTS descricao    VARCHAR(500) NULL,
    ADD COLUMN IF NOT EXISTS url_externa  VARCHAR(500) NULL;

-- ---------------------------------------------------------------------------
-- cronograma: garante colunas obrigatorias (nome, uk, indices)
-- ---------------------------------------------------------------------------
ALTER TABLE cronograma
    ADD COLUMN IF NOT EXISTS nome       VARCHAR(255) NOT NULL DEFAULT 'sem-nome',
    MODIFY COLUMN projeto_id INT NOT NULL,
    MODIFY COLUMN data_inicio DATE NOT NULL,
    MODIFY COLUMN data_fim    DATE NOT NULL;

ALTER TABLE cronograma ALTER COLUMN nome DROP DEFAULT;

SET @uk_exists = (
    SELECT COUNT(*) FROM information_schema.TABLE_CONSTRAINTS
    WHERE CONSTRAINT_SCHEMA = DATABASE()
      AND TABLE_NAME        = 'cronograma'
      AND CONSTRAINT_NAME   = 'uk_cronograma_projeto_periodo'
      AND CONSTRAINT_TYPE   = 'UNIQUE'
);
SET @sql = IF(@uk_exists = 0,
    'ALTER TABLE cronograma ADD CONSTRAINT uk_cronograma_projeto_periodo UNIQUE (projeto_id, data_inicio, data_fim)',
    'SELECT 1'
);
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

CREATE INDEX IF NOT EXISTS idx_cronograma_projeto ON cronograma (projeto_id);
CREATE INDEX IF NOT EXISTS idx_cronograma_periodo ON cronograma (data_inicio, data_fim);
