-- V28: Alinha auditoria + avaliacao ao DER V4 e cria tabelas faltantes
SET FOREIGN_KEY_CHECKS = 0;

-- ===== AVALIACAO =====
-- Reverte nota de DECIMAL(3,1) para INT (V27 havia convertido; DER V4 define INT)
SET @col_type_av = (SELECT DATA_TYPE FROM information_schema.columns
                    WHERE table_schema = DATABASE()
                      AND table_name   = 'avaliacao'
                      AND column_name  = 'nota');
SET @s = IF(@col_type_av IN ('decimal','numeric'),
    'ALTER TABLE avaliacao MODIFY COLUMN nota INT NULL',
    'SELECT 1 -- nota ja e INT');
PREPARE st FROM @s; EXECUTE st; DEALLOCATE PREPARE st;

-- Remove cliente_cpf (DER nao tem esta coluna)
SET @n = (SELECT COUNT(*) FROM information_schema.columns
          WHERE table_schema = DATABASE() AND table_name = 'avaliacao' AND column_name = 'cliente_cpf');
SET @s = IF(@n > 0,
    'ALTER TABLE avaliacao DROP FOREIGN KEY fk_avaliacao_cliente',
    'SELECT 1');
PREPARE st FROM @s; EXECUTE st; DEALLOCATE PREPARE st;
SET @n = (SELECT COUNT(*) FROM information_schema.columns
          WHERE table_schema = DATABASE() AND table_name = 'avaliacao' AND column_name = 'cliente_cpf');
SET @s = IF(@n > 0,
    'ALTER TABLE avaliacao DROP COLUMN cliente_cpf',
    'SELECT 1');
PREPARE st FROM @s; EXECUTE st; DEALLOCATE PREPARE st;

-- Adiciona avaliador_id
SET @n = (SELECT COUNT(*) FROM information_schema.columns
          WHERE table_schema = DATABASE() AND table_name = 'avaliacao' AND column_name = 'avaliador_id');
SET @s = IF(@n = 0,
    'ALTER TABLE avaliacao ADD COLUMN avaliador_id INT NULL',
    'SELECT 1');
PREPARE st FROM @s; EXECUTE st; DEALLOCATE PREPARE st;

-- Adiciona avaliado_id
SET @n = (SELECT COUNT(*) FROM information_schema.columns
          WHERE table_schema = DATABASE() AND table_name = 'avaliacao' AND column_name = 'avaliado_id');
SET @s = IF(@n = 0,
    'ALTER TABLE avaliacao ADD COLUMN avaliado_id INT NULL',
    'SELECT 1');
PREPARE st FROM @s; EXECUTE st; DEALLOCATE PREPARE st;

-- FK avaliador -> usuario
SET @n = (SELECT COUNT(*) FROM information_schema.table_constraints
          WHERE table_schema = DATABASE() AND table_name = 'avaliacao'
            AND constraint_name = 'fk_avaliacao_avaliador');
SET @s = IF(@n = 0,
    'ALTER TABLE avaliacao ADD CONSTRAINT fk_avaliacao_avaliador FOREIGN KEY (avaliador_id) REFERENCES usuario (usuario_id)',
    'SELECT 1');
PREPARE st FROM @s; EXECUTE st; DEALLOCATE PREPARE st;

-- FK avaliado -> usuario
SET @n = (SELECT COUNT(*) FROM information_schema.table_constraints
          WHERE table_schema = DATABASE() AND table_name = 'avaliacao'
            AND constraint_name = 'fk_avaliacao_avaliado');
SET @s = IF(@n = 0,
    'ALTER TABLE avaliacao ADD CONSTRAINT fk_avaliacao_avaliado FOREIGN KEY (avaliado_id) REFERENCES usuario (usuario_id)',
    'SELECT 1');
PREPARE st FROM @s; EXECUTE st; DEALLOCATE PREPARE st;

-- Index avaliador
SET @n = (SELECT COUNT(*) FROM information_schema.statistics
          WHERE table_schema = DATABASE() AND table_name = 'avaliacao' AND index_name = 'idx_avaliacao_avaliador');
SET @s = IF(@n = 0,
    'ALTER TABLE avaliacao ADD INDEX idx_avaliacao_avaliador (avaliador_id)',
    'SELECT 1');
PREPARE st FROM @s; EXECUTE st; DEALLOCATE PREPARE st;

-- Index avaliado
SET @n = (SELECT COUNT(*) FROM information_schema.statistics
          WHERE table_schema = DATABASE() AND table_name = 'avaliacao' AND index_name = 'idx_avaliacao_avaliado');
SET @s = IF(@n = 0,
    'ALTER TABLE avaliacao ADD INDEX idx_avaliacao_avaliado (avaliado_id)',
    'SELECT 1');
PREPARE st FROM @s; EXECUTE st; DEALLOCATE PREPARE st;

-- ===== AUDITORIA =====
-- Converte auditoria_id de BINARY(16) para INT AUTO_INCREMENT
-- MySQL nao permite MODIFY diretamente em PK binaria -> precisa: drop PK, modify, re-add PK
SET @pk_type_au = (SELECT COLUMN_TYPE FROM information_schema.columns
                   WHERE table_schema = DATABASE() AND table_name = 'auditoria'
                     AND column_name = 'auditoria_id');

-- Passo 1: Drop PK (apenas se ainda for BINARY)
SET @s = IF(@pk_type_au = 'binary(16)',
    'ALTER TABLE auditoria DROP PRIMARY KEY',
    'SELECT 1');
PREPARE st FROM @s; EXECUTE st; DEALLOCATE PREPARE st;

-- Passo 2: Modify para INT AUTO_INCREMENT
SET @pk_type_au = (SELECT COLUMN_TYPE FROM information_schema.columns
                   WHERE table_schema = DATABASE() AND table_name = 'auditoria'
                     AND column_name = 'auditoria_id');
SET @s = IF(@pk_type_au != 'int',
    'ALTER TABLE auditoria MODIFY COLUMN auditoria_id INT NOT NULL AUTO_INCREMENT',
    'SELECT 1');
PREPARE st FROM @s; EXECUTE st; DEALLOCATE PREPARE st;

-- Passo 3: Re-add PK
SET @pk_exists = (SELECT COUNT(*) FROM information_schema.table_constraints
                  WHERE table_schema = DATABASE() AND table_name = 'auditoria'
                    AND constraint_type = 'PRIMARY KEY');
SET @s = IF(@pk_exists = 0,
    'ALTER TABLE auditoria ADD PRIMARY KEY (auditoria_id)',
    'SELECT 1');
PREPARE st FROM @s; EXECUTE st; DEALLOCATE PREPARE st;

-- Remove data_hora (nao existe no DER)
SET @n = (SELECT COUNT(*) FROM information_schema.columns
          WHERE table_schema = DATABASE() AND table_name = 'auditoria' AND column_name = 'data_hora');
SET @s = IF(@n > 0,
    'ALTER TABLE auditoria DROP COLUMN data_hora',
    'SELECT 1');
PREPARE st FROM @s; EXECUTE st; DEALLOCATE PREPARE st;

-- Remove cliente_cpf
SET @n = (SELECT COUNT(*) FROM information_schema.columns
          WHERE table_schema = DATABASE() AND table_name = 'auditoria' AND column_name = 'cliente_cpf');
SET @s = IF(@n > 0,
    'ALTER TABLE auditoria DROP COLUMN cliente_cpf',
    'SELECT 1');
PREPARE st FROM @s; EXECUTE st; DEALLOCATE PREPARE st;

-- Remove tipo_usuario_id (drop FK primeiro)
SET @n = (SELECT COUNT(*) FROM information_schema.table_constraints
          WHERE table_schema = DATABASE() AND table_name = 'auditoria'
            AND constraint_name = 'fk_auditoria_tipo_usuario');
SET @s = IF(@n > 0,
    'ALTER TABLE auditoria DROP FOREIGN KEY fk_auditoria_tipo_usuario',
    'SELECT 1');
PREPARE st FROM @s; EXECUTE st; DEALLOCATE PREPARE st;
SET @n = (SELECT COUNT(*) FROM information_schema.columns
          WHERE table_schema = DATABASE() AND table_name = 'auditoria' AND column_name = 'tipo_usuario_id');
SET @s = IF(@n > 0,
    'ALTER TABLE auditoria DROP COLUMN tipo_usuario_id',
    'SELECT 1');
PREPARE st FROM @s; EXECUTE st; DEALLOCATE PREPARE st;

-- Remove tipo_usuario (coluna texto, adicionada no V25)
SET @n = (SELECT COUNT(*) FROM information_schema.columns
          WHERE table_schema = DATABASE() AND table_name = 'auditoria' AND column_name = 'tipo_usuario');
SET @s = IF(@n > 0,
    'ALTER TABLE auditoria DROP COLUMN tipo_usuario',
    'SELECT 1');
PREPARE st FROM @s; EXECUTE st; DEALLOCATE PREPARE st;

-- Converte acao TEXT -> VARCHAR(100)
SET @col_type_au2 = (SELECT DATA_TYPE FROM information_schema.columns
                     WHERE table_schema = DATABASE() AND table_name = 'auditoria'
                       AND column_name = 'acao');
SET @s = IF(@col_type_au2 = 'text',
    'ALTER TABLE auditoria MODIFY COLUMN acao VARCHAR(100) NOT NULL',
    'SELECT 1');
PREPARE st FROM @s; EXECUTE st; DEALLOCATE PREPARE st;

-- Adiciona usuario_id
SET @n = (SELECT COUNT(*) FROM information_schema.columns
          WHERE table_schema = DATABASE() AND table_name = 'auditoria' AND column_name = 'usuario_id');
SET @s = IF(@n = 0,
    'ALTER TABLE auditoria ADD COLUMN usuario_id INT NULL',
    'SELECT 1');
PREPARE st FROM @s; EXECUTE st; DEALLOCATE PREPARE st;

-- Adiciona entidade
SET @n = (SELECT COUNT(*) FROM information_schema.columns
          WHERE table_schema = DATABASE() AND table_name = 'auditoria' AND column_name = 'entidade');
SET @s = IF(@n = 0,
    'ALTER TABLE auditoria ADD COLUMN entidade VARCHAR(100) NULL',
    'SELECT 1');
PREPARE st FROM @s; EXECUTE st; DEALLOCATE PREPARE st;

-- Adiciona entidade_id
SET @n = (SELECT COUNT(*) FROM information_schema.columns
          WHERE table_schema = DATABASE() AND table_name = 'auditoria' AND column_name = 'entidade_id');
SET @s = IF(@n = 0,
    'ALTER TABLE auditoria ADD COLUMN entidade_id INT NULL',
    'SELECT 1');
PREPARE st FROM @s; EXECUTE st; DEALLOCATE PREPARE st;

-- Adiciona detalhe TEXT
SET @n = (SELECT COUNT(*) FROM information_schema.columns
          WHERE table_schema = DATABASE() AND table_name = 'auditoria' AND column_name = 'detalhe');
SET @s = IF(@n = 0,
    'ALTER TABLE auditoria ADD COLUMN detalhe TEXT NULL',
    'SELECT 1');
PREPARE st FROM @s; EXECUTE st; DEALLOCATE PREPARE st;

-- FK auditoria -> usuario
SET @n = (SELECT COUNT(*) FROM information_schema.table_constraints
          WHERE table_schema = DATABASE() AND table_name = 'auditoria'
            AND constraint_name = 'fk_auditoria_usuario');
SET @s = IF(@n = 0,
    'ALTER TABLE auditoria ADD CONSTRAINT fk_auditoria_usuario FOREIGN KEY (usuario_id) REFERENCES usuario (usuario_id)',
    'SELECT 1');
PREPARE st FROM @s; EXECUTE st; DEALLOCATE PREPARE st;

-- Index idx_auditoria_usuario
SET @n = (SELECT COUNT(*) FROM information_schema.statistics
          WHERE table_schema = DATABASE() AND table_name = 'auditoria' AND index_name = 'idx_auditoria_usuario');
SET @s = IF(@n = 0,
    'ALTER TABLE auditoria ADD INDEX idx_auditoria_usuario (usuario_id)',
    'SELECT 1');
PREPARE st FROM @s; EXECUTE st; DEALLOCATE PREPARE st;

-- ===== NOVAS TABELAS =====

CREATE TABLE IF NOT EXISTS especialidade (
    especialidade_id INT         NOT NULL AUTO_INCREMENT,
    nome             VARCHAR(100) NOT NULL,
    descricao        VARCHAR(255),
    created_at       DATETIME(6)  NOT NULL,
    updated_at       DATETIME(6),
    ativo            TINYINT(1)   NOT NULL DEFAULT 1,
    version          BIGINT                DEFAULT 0,
    PRIMARY KEY (especialidade_id),
    UNIQUE KEY uk_especialidade_nome (nome)
);

CREATE TABLE IF NOT EXISTS lojista_parceiro (
    lojista_id                INT          NOT NULL AUTO_INCREMENT,
    cnpj                      VARCHAR(14)  NOT NULL,
    razao_social              VARCHAR(255) NOT NULL,
    nome_fantasia             VARCHAR(255),
    email                     VARCHAR(255),
    telefone                  VARCHAR(20),
    taxa_comissao_percentual  DECIMAL(5,2),
    categorias_produtos       VARCHAR(500),
    endereco_id               INT,
    created_at                DATETIME(6)  NOT NULL,
    updated_at                DATETIME(6),
    ativo                     TINYINT(1)   NOT NULL DEFAULT 1,
    version                   BIGINT                DEFAULT 0,
    PRIMARY KEY (lojista_id),
    UNIQUE KEY uk_lojista_cnpj (cnpj),
    CONSTRAINT fk_lojista_endereco FOREIGN KEY (endereco_id) REFERENCES endereco (endereco_id)
);

CREATE TABLE IF NOT EXISTS score_tfe (
    score_tfe_id      BIGINT      NOT NULL AUTO_INCREMENT,
    profissional_id   INT         NOT NULL,
    score_total       DECIMAL(5,4),
    score_tecnico     DECIMAL(5,4),
    score_operacional DECIMAL(5,4),
    score_social      DECIMAL(5,4),
    versao_algoritmo  VARCHAR(20),
    calculado_em      TIMESTAMP,
    created_at        DATETIME(6) NOT NULL,
    updated_at        DATETIME(6),
    ativo             TINYINT(1)  NOT NULL DEFAULT 1,
    version           BIGINT               DEFAULT 0,
    PRIMARY KEY (score_tfe_id),
    CONSTRAINT fk_score_tfe_profissional FOREIGN KEY (profissional_id) REFERENCES profissional_de_base (profissional_id)
);

CREATE TABLE IF NOT EXISTS trajetoria_laboral (
    trajetoria_id         BIGINT       NOT NULL AUTO_INCREMENT,
    profissional_id       INT          NOT NULL,
    obra_id               INT,
    projeto_id            INT,
    funcao_desempenhada   VARCHAR(100),
    data_inicio           DATE,
    data_fim              DATE,
    obra_concluida        TINYINT(1),
    avaliacao_recebida    DECIMAL(3,1),
    comentario_avaliacao  TEXT,
    created_at            DATETIME(6)  NOT NULL,
    updated_at            DATETIME(6),
    ativo                 TINYINT(1)   NOT NULL DEFAULT 1,
    version               BIGINT                DEFAULT 0,
    PRIMARY KEY (trajetoria_id),
    CONSTRAINT fk_trajetoria_profissional FOREIGN KEY (profissional_id) REFERENCES profissional_de_base (profissional_id),
    CONSTRAINT fk_trajetoria_obra         FOREIGN KEY (obra_id)         REFERENCES obra (obra_id),
    CONSTRAINT fk_trajetoria_projeto      FOREIGN KEY (projeto_id)      REFERENCES projeto (projeto_id)
);

CREATE TABLE IF NOT EXISTS fatiamento_pagamento (
    fatiamento_id              BIGINT       NOT NULL AUTO_INCREMENT,
    pagamento_id               INT          NOT NULL,
    obra_id                    INT,
    profissional_id            INT,
    valor_bruto                DECIMAL(18,2),
    valor_plataforma           DECIMAL(18,2),
    valor_fornecedores         DECIMAL(18,2),
    valor_deducao_divida       DECIMAL(18,2),
    valor_liquido_trabalhador  DECIMAL(18,2),
    status_liquidacao          VARCHAR(20),
    liquidado_em               TIMESTAMP,
    hash_transacao             VARCHAR(128),
    created_at                 TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at                 TIMESTAMP,
    PRIMARY KEY (fatiamento_id),
    CONSTRAINT fk_fatiamento_pagamento    FOREIGN KEY (pagamento_id)    REFERENCES pagamento (pagamento_id),
    CONSTRAINT fk_fatiamento_obra         FOREIGN KEY (obra_id)         REFERENCES obra (obra_id),
    CONSTRAINT fk_fatiamento_profissional FOREIGN KEY (profissional_id) REFERENCES profissional_de_base (profissional_id)
);

CREATE TABLE IF NOT EXISTS parcela_credito (
    parcela_credito_id    BIGINT      NOT NULL AUTO_INCREMENT,
    proposta_credito_id   BIGINT      NOT NULL,
    numero_parcela        INT,
    valor_parcela         DECIMAL(18,2),
    data_vencimento       DATE,
    data_pagamento        DATE,
    valor_pago            DECIMAL(18,2),
    status                VARCHAR(20),
    modalidade_pagamento  VARCHAR(30),
    created_at            TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at            TIMESTAMP,
    PRIMARY KEY (parcela_credito_id),
    CONSTRAINT fk_parcela_proposta FOREIGN KEY (proposta_credito_id) REFERENCES proposta_credito (proposta_credito_id)
);

CREATE TABLE IF NOT EXISTS metricas_comportamento_operacional (
    metrica_operacional_id           BIGINT        NOT NULL AUTO_INCREMENT,
    profissional_id                  INT           NOT NULL,
    frequencia_login_dias            INT,
    tempo_medio_resposta_horas       DECIMAL(10,4),
    indice_consistencia_documental   DECIMAL(5,4),
    total_solicitacoes_respondidas   INT,
    total_solicitacoes_recebidas     INT,
    periodo_referencia_inicio        DATE,
    periodo_referencia_fim           DATE,
    created_at                       TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at                       TIMESTAMP,
    ativo                            TINYINT(1)    NOT NULL DEFAULT 1,
    version                          BIGINT                 DEFAULT 0,
    PRIMARY KEY (metrica_operacional_id),
    CONSTRAINT fk_metrica_op_prof FOREIGN KEY (profissional_id) REFERENCES profissional_de_base (profissional_id)
);

CREATE TABLE IF NOT EXISTS metricas_desempenho_tecnico (
    metrica_tecnico_id               BIGINT        NOT NULL AUTO_INCREMENT,
    profissional_id                  INT           NOT NULL,
    desvio_pontualidade_horas        DECIMAL(10,4),
    total_etapas_concluidas          INT,
    total_etapas_planejadas          INT,
    qualidade_media_fotos            DECIMAL(5,4),
    percentual_fotos_validas_exif    DECIMAL(5,4),
    periodo_referencia_inicio        DATE,
    periodo_referencia_fim           DATE,
    created_at                       TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at                       TIMESTAMP,
    ativo                            TINYINT(1)    NOT NULL DEFAULT 1,
    version                          BIGINT                 DEFAULT 0,
    PRIMARY KEY (metrica_tecnico_id),
    CONSTRAINT fk_metrica_tec_prof FOREIGN KEY (profissional_id) REFERENCES profissional_de_base (profissional_id)
);

CREATE TABLE IF NOT EXISTS profissional_especialidade (
    profissional_id  INT NOT NULL,
    especialidade_id INT NOT NULL,
    PRIMARY KEY (profissional_id, especialidade_id),
    CONSTRAINT fk_prof_esp_profissional  FOREIGN KEY (profissional_id)  REFERENCES profissional_de_base (profissional_id),
    CONSTRAINT fk_prof_esp_especialidade FOREIGN KEY (especialidade_id) REFERENCES especialidade (especialidade_id)
);

SET FOREIGN_KEY_CHECKS = 1;
