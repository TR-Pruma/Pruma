-- =============================================================================
-- V36: CORRECOES RESIDUAIS POS-V35
-- Compativel com MySQL 5.7+ (sem ADD COLUMN IF NOT EXISTS).
-- Usa procedures para idempotencia segura.
-- Mapeamento validado contra as entidades Java do branch feature/novos_services.
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
-- Entidade: Comunicacao.java
--   projeto_id     -> @ManyToOne @JoinColumn(nullable=false)
--   cliente_id     -> @ManyToOne @JoinColumn(nullable=false)
--   mensagem       -> @Column(columnDefinition="TEXT", nullable=false)
--   tipo_remetente -> @Column(length=15, nullable=false)
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
-- Entidade: ComunicacaoAux.java - @MapsId sobre comunicacao
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
-- anexo: alinhado com Anexo.java
--   projeto_id -> @ManyToOne @JoinColumn(nullable=false)
--   tipo_anexo -> @Column(length=15, nullable=false)
--   caminho    -> @Column(length=1024, nullable=false)
--   nome       -> @Column(length=255)
--   tipo_mime  -> @Column(length=100)
--   tamanho    -> @Column BIGINT
-- ===========================================================================
CALL pruma_add_col('anexo','projeto_id',
  'projeto_id INT NOT NULL DEFAULT 0');
CALL pruma_add_col('anexo','tipo_anexo',
  "tipo_anexo VARCHAR(15) NOT NULL DEFAULT 'OUTRO'");
CALL pruma_add_col('anexo','caminho',
  'caminho VARCHAR(1024) NOT NULL DEFAULT ""');
CALL pruma_add_col('anexo','nome',
  'nome VARCHAR(255) NULL');
CALL pruma_add_col('anexo','tipo_mime',
  'tipo_mime VARCHAR(100) NULL');
CALL pruma_add_col('anexo','tamanho',
  'tamanho BIGINT NULL');

CALL pruma_add_fk('anexo','fk_anexo_projeto',
  'FOREIGN KEY (projeto_id) REFERENCES projeto (projeto_id)');

CALL pruma_add_idx('anexo','idx_anexo_projeto','projeto_id');
CALL pruma_add_idx('anexo','idx_anexo_tipo',   'tipo_anexo');

-- ===========================================================================
-- apadrinhamento_rede
-- Entidade: ApadrinhamentoRede.java
--   apadrinhamento_id -> @Id @GeneratedValue BIGINT AUTO_INCREMENT
--   padrinho_id       -> @ManyToOne ProfissionalDeBase (nullable=false)
--   afilhado_id       -> @ManyToOne ProfissionalDeBase (nullable=false)
--   data_inicio       -> DATE NOT NULL
--   data_fim          -> DATE NULL
--   status            -> VARCHAR(20) NOT NULL DEFAULT 'ATIVO'
--   created_at        -> DATETIME(6) NOT NULL (@CreationTimestamp)
--   updated_at        -> DATETIME(6) NOT NULL (@UpdateTimestamp)
--
-- NOTA: CREATE TABLE IF NOT EXISTS e a unica abordagem valida aqui.
-- NAO usar pruma_add_col para colunas AUTO_INCREMENT/PK - MySQL erro 1075.
-- Se a tabela ja existia sem as colunas corretas, ela deve ser recriada
-- manualmente antes de rodar este migration.
-- ===========================================================================
CREATE TABLE IF NOT EXISTS apadrinhamento_rede (
  apadrinhamento_id BIGINT      NOT NULL AUTO_INCREMENT,
  padrinho_id       INT         NOT NULL,
  afilhado_id       INT         NOT NULL,
  data_inicio       DATE        NOT NULL,
  data_fim          DATE        NULL,
  status            VARCHAR(20) NOT NULL DEFAULT 'ATIVO',
  created_at        DATETIME(6) NOT NULL,
  updated_at        DATETIME(6) NOT NULL,
  PRIMARY KEY (apadrinhamento_id),
  CONSTRAINT fk_apadr_padrinho FOREIGN KEY (padrinho_id)
    REFERENCES profissional_de_base (profissional_id),
  CONSTRAINT fk_apadr_afilhado FOREIGN KEY (afilhado_id)
    REFERENCES profissional_de_base (profissional_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Colunas complementares caso a tabela ja exista porem incompleta
-- (apenas colunas simples - sem AUTO_INCREMENT/PK que causam erro 1075)
CALL pruma_add_col('apadrinhamento_rede','padrinho_id',
  'padrinho_id INT NOT NULL DEFAULT 0');
CALL pruma_add_col('apadrinhamento_rede','afilhado_id',
  'afilhado_id INT NOT NULL DEFAULT 0');
CALL pruma_add_col('apadrinhamento_rede','data_inicio',
  'data_inicio DATE NOT NULL DEFAULT (CURRENT_DATE)');
CALL pruma_add_col('apadrinhamento_rede','data_fim',
  'data_fim DATE NULL');
CALL pruma_add_col('apadrinhamento_rede','status',
  "status VARCHAR(20) NOT NULL DEFAULT 'ATIVO'");
CALL pruma_add_col('apadrinhamento_rede','created_at',
  'created_at DATETIME(6) NOT NULL DEFAULT NOW(6)');
CALL pruma_add_col('apadrinhamento_rede','updated_at',
  'updated_at DATETIME(6) NOT NULL DEFAULT NOW(6)');

CALL pruma_add_fk('apadrinhamento_rede','fk_apadr_padrinho',
  'FOREIGN KEY (padrinho_id) REFERENCES profissional_de_base (profissional_id)');
CALL pruma_add_fk('apadrinhamento_rede','fk_apadr_afilhado',
  'FOREIGN KEY (afilhado_id) REFERENCES profissional_de_base (profissional_id)');

CALL pruma_add_idx('apadrinhamento_rede','idx_apadrinhamento_padrinho','padrinho_id');
CALL pruma_add_idx('apadrinhamento_rede','idx_apadrinhamento_afilhado','afilhado_id');
CALL pruma_add_idx('apadrinhamento_rede','idx_apadrinhamento_status',  'status');

-- ===========================================================================
-- cronograma: coluna nome + constraints
-- Entidade: Cronograma.java
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
