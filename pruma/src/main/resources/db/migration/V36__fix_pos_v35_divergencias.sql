-- =============================================================================
-- V36: CORRECOES RESIDUAIS POS-V35
-- Compativel com MySQL 5.7+ (sem ADD COLUMN IF NOT EXISTS).
-- Usa procedures para idempotencia segura.
-- =============================================================================

-- ---------------------------------------------------------------------------
-- HELPER: adiciona coluna apenas se nao existir
-- ---------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS pruma_add_col;
CREATE PROCEDURE pruma_add_col(IN t VARCHAR(64), IN c VARCHAR(64), IN ddl VARCHAR(500))
BEGIN
  IF NOT EXISTS (
    SELECT 1 FROM information_schema.COLUMNS
    WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME=t AND COLUMN_NAME=c
  ) THEN
    SET @s=CONCAT('ALTER TABLE `',t,'` ADD COLUMN ',ddl);
    PREPARE p FROM @s; EXECUTE p; DEALLOCATE PREPARE p;
  END IF;
END;

-- ---------------------------------------------------------------------------
-- HELPER: adiciona FK apenas se nao existir
-- ---------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS pruma_add_fk;
CREATE PROCEDURE pruma_add_fk(IN t VARCHAR(64), IN k VARCHAR(64), IN ddl VARCHAR(500))
BEGIN
  IF NOT EXISTS (
    SELECT 1 FROM information_schema.TABLE_CONSTRAINTS
    WHERE CONSTRAINT_SCHEMA=DATABASE() AND TABLE_NAME=t
      AND CONSTRAINT_NAME=k AND CONSTRAINT_TYPE='FOREIGN KEY'
  ) THEN
    SET @s=CONCAT('ALTER TABLE `',t,'` ADD CONSTRAINT `',k,'` ',ddl);
    PREPARE p FROM @s; EXECUTE p; DEALLOCATE PREPARE p;
  END IF;
END;

-- ---------------------------------------------------------------------------
-- HELPER: adiciona UNIQUE KEY apenas se nao existir
-- ---------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS pruma_add_uk;
CREATE PROCEDURE pruma_add_uk(IN t VARCHAR(64), IN k VARCHAR(64), IN cols VARCHAR(200))
BEGIN
  IF NOT EXISTS (
    SELECT 1 FROM information_schema.TABLE_CONSTRAINTS
    WHERE CONSTRAINT_SCHEMA=DATABASE() AND TABLE_NAME=t
      AND CONSTRAINT_NAME=k AND CONSTRAINT_TYPE='UNIQUE'
  ) THEN
    SET @s=CONCAT('ALTER TABLE `',t,'` ADD CONSTRAINT `',k,'` UNIQUE (',cols,')');
    PREPARE p FROM @s; EXECUTE p; DEALLOCATE PREPARE p;
  END IF;
END;

-- ---------------------------------------------------------------------------
-- HELPER: cria INDEX apenas se nao existir
-- ---------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS pruma_add_idx;
CREATE PROCEDURE pruma_add_idx(IN t VARCHAR(64), IN i VARCHAR(64), IN cols VARCHAR(200))
BEGIN
  IF NOT EXISTS (
    SELECT 1 FROM information_schema.STATISTICS
    WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME=t AND INDEX_NAME=i
  ) THEN
    SET @s=CONCAT('CREATE INDEX `',i,'` ON `',t,'` (',cols,')');
    PREPARE p FROM @s; EXECUTE p; DEALLOCATE PREPARE p;
  END IF;
END;

-- ===========================================================================
-- comunicacao: colunas obrigatorias
-- ===========================================================================
CALL pruma_add_col('comunicacao','cliente_id',
  'cliente_id INT NOT NULL DEFAULT 0');
CALL pruma_add_col('comunicacao','mensagem',
  'mensagem TEXT NOT NULL');
CALL pruma_add_col('comunicacao','tipo_remetente',
  "tipo_remetente VARCHAR(15) NOT NULL DEFAULT 'SISTEMA'");

CALL pruma_add_fk('comunicacao','fk_com_cliente',
  'FOREIGN KEY (cliente_id) REFERENCES cliente (cliente_id)');

CALL pruma_add_idx('comunicacao','idx_com_cliente',  'cliente_id');
CALL pruma_add_idx('comunicacao','idx_com_remetente','tipo_remetente');

-- ===========================================================================
-- comunicacao_aux
-- ===========================================================================
CREATE TABLE IF NOT EXISTS comunicacao_aux (
  comunicacao_id INT         NOT NULL,
  tipo_mensagem  VARCHAR(30) NOT NULL,
  created_at     DATETIME(6) NOT NULL,
  updated_at     DATETIME(6),
  ativo          TINYINT(1)  NOT NULL DEFAULT 1,
  version        BIGINT,
  PRIMARY KEY (comunicacao_id),
  CONSTRAINT fk_ca_comunicacao FOREIGN KEY (comunicacao_id)
    REFERENCES comunicacao (comunicacao_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ===========================================================================
-- anexo: colunas novas
-- ===========================================================================
CALL pruma_add_col('anexo','tipo_anexo',  'tipo_anexo  VARCHAR(50)  NULL');
CALL pruma_add_col('anexo','descricao',   'descricao   VARCHAR(500) NULL');
CALL pruma_add_col('anexo','url_externa', 'url_externa VARCHAR(500) NULL');

-- ===========================================================================
-- cronograma: coluna nome + constraints
-- ===========================================================================
CALL pruma_add_col('cronograma','nome',
  "nome VARCHAR(255) NOT NULL DEFAULT 'sem-nome'");

CALL pruma_add_uk('cronograma','uk_cronograma_projeto_periodo',
  'projeto_id, data_inicio, data_fim');

CALL pruma_add_idx('cronograma','idx_cronograma_projeto','projeto_id');
CALL pruma_add_idx('cronograma','idx_cronograma_periodo','data_inicio, data_fim');

-- ===========================================================================
-- Limpeza
-- ===========================================================================
DROP PROCEDURE IF EXISTS pruma_add_col;
DROP PROCEDURE IF EXISTS pruma_add_fk;
DROP PROCEDURE IF EXISTS pruma_add_uk;
DROP PROCEDURE IF EXISTS pruma_add_idx;
