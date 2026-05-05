-- =============================================================================
-- V37: RECONSTROI apadrinhamento_rede
--
-- Problema original: tabela criada pelo V7 com TIMESTAMP (sem precisao 6),
-- sem colunas ativo/version. O Hibernate (ddl-auto=validate) exige DATETIME(6),
-- ativo e version mapeados em ApadrinhamentoRede.java.
-- O CREATE TABLE IF NOT EXISTS do V36 foi ignorado pois a tabela ja existia.
--
-- Fix do erro 1054: todo SELECT no backup usa Dynamic SQL (PREPARE/EXECUTE)
-- para que o MySQL nao tente resolver colunas em tempo de compilacao da procedure.
--
-- Estrategia:
--   1. Remove FKs da tabela atual
--   2. Renomeia para _bkp
--   3. Recria com schema correto
--   4. Reinsere dados via Dynamic SQL
--   5. Dropa backup
--
-- Compativel com MySQL 5.7+
-- =============================================================================

-- ---------------------------------------------------------------------------
-- STEP 1: remove FKs da tabela para permitir RENAME
-- ---------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS pruma_drop_fk;
CREATE PROCEDURE pruma_drop_fk(IN tbl VARCHAR(64), IN fk VARCHAR(64))
BEGIN
  IF EXISTS (
    SELECT 1 FROM information_schema.TABLE_CONSTRAINTS
    WHERE CONSTRAINT_SCHEMA = DATABASE()
      AND TABLE_NAME        = tbl
      AND CONSTRAINT_NAME   = fk
      AND CONSTRAINT_TYPE   = 'FOREIGN KEY'
  ) THEN
    SET @_s = CONCAT('ALTER TABLE `', tbl, '` DROP FOREIGN KEY `', fk, '`');
    PREPARE _p FROM @_s; EXECUTE _p; DEALLOCATE PREPARE _p;
  END IF;
END;

CALL pruma_drop_fk('apadrinhamento_rede', 'fk_apadrinhamento_padrinho');
CALL pruma_drop_fk('apadrinhamento_rede', 'fk_apadrinhamento_afilhado');
CALL pruma_drop_fk('apadrinhamento_rede', 'fk_apadr_padrinho');
CALL pruma_drop_fk('apadrinhamento_rede', 'fk_apadr_afilhado');
DROP PROCEDURE IF EXISTS pruma_drop_fk;

-- ---------------------------------------------------------------------------
-- STEP 2: backup da tabela atual (se existir)
-- ---------------------------------------------------------------------------
DROP TABLE IF EXISTS apadrinhamento_rede_bkp;

DROP PROCEDURE IF EXISTS pruma_rename;
CREATE PROCEDURE pruma_rename()
BEGIN
  IF EXISTS (
    SELECT 1 FROM information_schema.TABLES
    WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'apadrinhamento_rede'
  ) THEN
    RENAME TABLE apadrinhamento_rede TO apadrinhamento_rede_bkp;
  END IF;
END;
CALL pruma_rename();
DROP PROCEDURE IF EXISTS pruma_rename;

-- ---------------------------------------------------------------------------
-- STEP 3: cria tabela com schema definitivo
-- Mapeamento: ApadrinhamentoRede.java
-- ---------------------------------------------------------------------------
CREATE TABLE apadrinhamento_rede (
  apadrinhamento_id BIGINT      NOT NULL AUTO_INCREMENT,
  padrinho_id       INT         NOT NULL,
  afilhado_id       INT         NOT NULL,
  data_inicio       DATE        NOT NULL,
  data_fim          DATE        NULL,
  status            VARCHAR(20) NOT NULL DEFAULT 'ATIVO',
  ativo             TINYINT(1)  NOT NULL DEFAULT 1,
  version           BIGINT      NULL,
  created_at        DATETIME(6) NOT NULL DEFAULT NOW(6),
  updated_at        DATETIME(6) NOT NULL DEFAULT NOW(6),
  PRIMARY KEY (apadrinhamento_id),
  CONSTRAINT uq_apadrinhamento_ativo
    UNIQUE (padrinho_id, afilhado_id, status),
  CONSTRAINT fk_apadr_padrinho
    FOREIGN KEY (padrinho_id) REFERENCES profissional_de_base (profissional_id),
  CONSTRAINT fk_apadr_afilhado
    FOREIGN KEY (afilhado_id) REFERENCES profissional_de_base (profissional_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE INDEX idx_apadrinhamento_padrinho ON apadrinhamento_rede (padrinho_id);
CREATE INDEX idx_apadrinhamento_afilhado ON apadrinhamento_rede (afilhado_id);
CREATE INDEX idx_apadrinhamento_status   ON apadrinhamento_rede (status);

-- ---------------------------------------------------------------------------
-- STEP 4: reinsere dados do backup usando Dynamic SQL
-- O Dynamic SQL evita que o MySQL resolva colunas do backup em tempo de
-- compilacao da procedure (causa do erro 1054).
-- ---------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS pruma_restore;
CREATE PROCEDURE pruma_restore()
BEGIN
  DECLARE has_bkp      INT DEFAULT 0;
  DECLARE has_ativo    INT DEFAULT 0;
  DECLARE has_version  INT DEFAULT 0;
  DECLARE col_ativo    VARCHAR(200);
  DECLARE col_version  VARCHAR(200);
  DECLARE sql_insert   TEXT;

  -- verifica se backup existe
  SELECT COUNT(*) INTO has_bkp
  FROM information_schema.TABLES
  WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'apadrinhamento_rede_bkp';

  IF has_bkp > 0 THEN

    -- verifica colunas opcionais no backup
    SELECT COUNT(*) INTO has_ativo
    FROM information_schema.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME   = 'apadrinhamento_rede_bkp'
      AND COLUMN_NAME  = 'ativo';

    SELECT COUNT(*) INTO has_version
    FROM information_schema.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME   = 'apadrinhamento_rede_bkp'
      AND COLUMN_NAME  = 'version';

    -- monta expressoes condicionais
    IF has_ativo > 0 THEN
      SET col_ativo = 'ativo';
    ELSE
      SET col_ativo = '1';
    END IF;

    IF has_version > 0 THEN
      SET col_version = 'version';
    ELSE
      SET col_version = 'NULL';
    END IF;

    -- monta e executa o INSERT dinamicamente
    SET sql_insert = CONCAT(
      'INSERT INTO apadrinhamento_rede ',
      '(apadrinhamento_id, padrinho_id, afilhado_id, data_inicio, data_fim, ',
      ' status, ativo, version, created_at, updated_at) ',
      'SELECT apadrinhamento_id, padrinho_id, afilhado_id, data_inicio, data_fim, ',
      '       COALESCE(status, ''ATIVO''), ', col_ativo, ', ', col_version, ', ',
      '       COALESCE(CAST(created_at AS DATETIME(6)), NOW(6)), ',
      '       COALESCE(CAST(updated_at AS DATETIME(6)), NOW(6)) ',
      'FROM apadrinhamento_rede_bkp'
    );

    PREPARE _stmt FROM sql_insert;
    EXECUTE _stmt;
    DEALLOCATE PREPARE _stmt;

  END IF;
END;

CALL pruma_restore();
DROP PROCEDURE IF EXISTS pruma_restore;

-- ---------------------------------------------------------------------------
-- STEP 5: remove backup
-- ---------------------------------------------------------------------------
DROP TABLE IF EXISTS apadrinhamento_rede_bkp;
