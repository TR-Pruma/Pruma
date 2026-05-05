-- =============================================================================
-- V37: RECONSTROI apadrinhamento_rede
--
-- Problema: a tabela foi criada pelo V7 com TIMESTAMP (sem precisao de 6)
-- e sem as colunas ativo/version. O Hibernate (ddl-auto=validate) exige
-- DATETIME(6) para @CreationTimestamp/@UpdateTimestamp e as colunas
-- ativo/version mapeadas na entidade ApadrinhamentoRede.java.
-- O V36 tentou resolver via CREATE TABLE IF NOT EXISTS, mas a tabela ja
-- existia, entao o bloco foi ignorado.
--
-- Estrategia:
--   1. Renomeia a tabela atual para _bkp (preserva dados)
--   2. Recria com schema exato da entidade Java
--   3. Copia os dados
--   4. Dropa o backup
--
-- Compativel com MySQL 5.7+
-- =============================================================================

-- ---------------------------------------------------------------------------
-- STEP 1: remover FKs de tabelas filhas que apontam para apadrinhamento_rede
-- (se houver) para permitir o rename. Caso nao existam, os DROPs sao no-op.
-- ---------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS pruma_drop_fk_if_exists;
CREATE PROCEDURE pruma_drop_fk_if_exists(IN tbl VARCHAR(64), IN fk VARCHAR(64))
BEGIN
  IF EXISTS (
    SELECT 1 FROM information_schema.TABLE_CONSTRAINTS
    WHERE CONSTRAINT_SCHEMA = DATABASE()
      AND TABLE_NAME        = tbl
      AND CONSTRAINT_NAME   = fk
      AND CONSTRAINT_TYPE   = 'FOREIGN KEY'
  ) THEN
    SET @s = CONCAT('ALTER TABLE `', tbl, '` DROP FOREIGN KEY `', fk, '`');
    PREPARE p FROM @s; EXECUTE p; DEALLOCATE PREPARE p;
  END IF;
END;

-- FKs conhecidas na propria tabela (precisam ser removidas antes do RENAME)
CALL pruma_drop_fk_if_exists('apadrinhamento_rede', 'fk_apadrinhamento_padrinho');
CALL pruma_drop_fk_if_exists('apadrinhamento_rede', 'fk_apadrinhamento_afilhado');
CALL pruma_drop_fk_if_exists('apadrinhamento_rede', 'fk_apadr_padrinho');
CALL pruma_drop_fk_if_exists('apadrinhamento_rede', 'fk_apadr_afilhado');

DROP PROCEDURE IF EXISTS pruma_drop_fk_if_exists;

-- ---------------------------------------------------------------------------
-- STEP 2: backup da tabela atual (se existir)
-- ---------------------------------------------------------------------------
DROP TABLE IF EXISTS apadrinhamento_rede_bkp;

DROP PROCEDURE IF EXISTS pruma_rename_if_exists;
CREATE PROCEDURE pruma_rename_if_exists()
BEGIN
  IF EXISTS (
    SELECT 1 FROM information_schema.TABLES
    WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'apadrinhamento_rede'
  ) THEN
    RENAME TABLE apadrinhamento_rede TO apadrinhamento_rede_bkp;
  END IF;
END;
CALL pruma_rename_if_exists();
DROP PROCEDURE IF EXISTS pruma_rename_if_exists;

-- ---------------------------------------------------------------------------
-- STEP 3: cria tabela com schema definitivo
-- Mapeamento: ApadrinhamentoRede.java
--   apadrinhamento_id  @Id @GeneratedValue BIGINT AUTO_INCREMENT
--   padrinho_id        @ManyToOne ProfissionalDeBase NOT NULL
--   afilhado_id        @ManyToOne ProfissionalDeBase NOT NULL
--   data_inicio        DATE NOT NULL
--   data_fim           DATE NULL
--   status             VARCHAR(20) NOT NULL DEFAULT 'ATIVO'
--   ativo              TINYINT(1)  NOT NULL DEFAULT 1   (@Column)
--   version            BIGINT                           (@Version)
--   created_at         DATETIME(6) NOT NULL             (@CreationTimestamp)
--   updated_at         DATETIME(6) NOT NULL             (@UpdateTimestamp)
-- ---------------------------------------------------------------------------
CREATE TABLE apadrinhamento_rede (
  apadrinhamento_id BIGINT       NOT NULL AUTO_INCREMENT,
  padrinho_id       INT          NOT NULL,
  afilhado_id       INT          NOT NULL,
  data_inicio       DATE         NOT NULL,
  data_fim          DATE         NULL,
  status            VARCHAR(20)  NOT NULL DEFAULT 'ATIVO',
  ativo             TINYINT(1)   NOT NULL DEFAULT 1,
  version           BIGINT       NULL,
  created_at        DATETIME(6)  NOT NULL DEFAULT NOW(6),
  updated_at        DATETIME(6)  NOT NULL DEFAULT NOW(6),
  PRIMARY KEY (apadrinhamento_id),
  CONSTRAINT uq_apadrinhamento_ativo
    UNIQUE (padrinho_id, afilhado_id, status),
  CONSTRAINT fk_apadr_padrinho
    FOREIGN KEY (padrinho_id)
    REFERENCES profissional_de_base (profissional_id),
  CONSTRAINT fk_apadr_afilhado
    FOREIGN KEY (afilhado_id)
    REFERENCES profissional_de_base (profissional_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE INDEX idx_apadrinhamento_padrinho ON apadrinhamento_rede (padrinho_id);
CREATE INDEX idx_apadrinhamento_afilhado ON apadrinhamento_rede (afilhado_id);
CREATE INDEX idx_apadrinhamento_status   ON apadrinhamento_rede (status);

-- ---------------------------------------------------------------------------
-- STEP 4: reinsere dados do backup (se a tabela existia antes)
-- ---------------------------------------------------------------------------
DROP PROCEDURE IF EXISTS pruma_restore_apadrinhamento;
CREATE PROCEDURE pruma_restore_apadrinhamento()
BEGIN
  IF EXISTS (
    SELECT 1 FROM information_schema.TABLES
    WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'apadrinhamento_rede_bkp'
  ) THEN
    INSERT INTO apadrinhamento_rede
      (apadrinhamento_id, padrinho_id, afilhado_id, data_inicio, data_fim,
       status, ativo, version, created_at, updated_at)
    SELECT
      apadrinhamento_id,
      padrinho_id,
      afilhado_id,
      data_inicio,
      data_fim,
      COALESCE(status, 'ATIVO'),
      -- ativo: usa coluna existente se disponivel, senao 1
      IF(EXISTS(SELECT 1 FROM information_schema.COLUMNS
                WHERE TABLE_SCHEMA=DATABASE()
                  AND TABLE_NAME='apadrinhamento_rede_bkp'
                  AND COLUMN_NAME='ativo'),
         ativo, 1),
      -- version: usa coluna existente se disponivel, senao NULL
      IF(EXISTS(SELECT 1 FROM information_schema.COLUMNS
                WHERE TABLE_SCHEMA=DATABASE()
                  AND TABLE_NAME='apadrinhamento_rede_bkp'
                  AND COLUMN_NAME='version'),
         version, NULL),
      COALESCE(CAST(created_at AS DATETIME(6)), NOW(6)),
      COALESCE(CAST(updated_at AS DATETIME(6)), NOW(6))
    FROM apadrinhamento_rede_bkp;
  END IF;
END;
CALL pruma_restore_apadrinhamento();
DROP PROCEDURE IF EXISTS pruma_restore_apadrinhamento;

-- ---------------------------------------------------------------------------
-- STEP 5: remove backup
-- ---------------------------------------------------------------------------
DROP TABLE IF EXISTS apadrinhamento_rede_bkp;
