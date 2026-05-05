-- =============================================================================
-- V5 - Alinhamento do schema com as classes Java do domínio
-- Gerado em: 2026-05-04
-- Referência: auditoria V1..V4 vs. classes Java em core/domain
--
-- Organização:
--   SEÇÃO 1 — Críticos: colunas NOT NULL ausentes que quebram o boot JPA
--   SEÇÃO 2 — Graves: constraints e tamanhos divergentes
--   SEÇÃO 3 — Moderados: tabelas órfãs e estrutura incorreta
--   SEÇÃO 4 — Índices faltantes declarados no @Table do Java
-- =============================================================================

-- =============================================================================
-- SEÇÃO 1 — CRÍTICOS
-- =============================================================================

-- -----------------------------------------------------------------------------
-- 1.1  endereco — 7 colunas obrigatórias ausentes
--      Classe: Endereco.java (@Column(nullable=false) em todos os campos abaixo)
--      Problema: tabela criada em V1 só continha endereco_id, empresa_cnpj e auditoria
-- -----------------------------------------------------------------------------
ALTER TABLE endereco
    ADD COLUMN IF NOT EXISTS logradouro  VARCHAR(255) NOT NULL DEFAULT '',
    ADD COLUMN IF NOT EXISTS numero      VARCHAR(20)  NOT NULL DEFAULT '',
    ADD COLUMN IF NOT EXISTS complemento VARCHAR(100),
    ADD COLUMN IF NOT EXISTS bairro      VARCHAR(100) NOT NULL DEFAULT '',
    ADD COLUMN IF NOT EXISTS cidade      VARCHAR(100) NOT NULL DEFAULT '',
    ADD COLUMN IF NOT EXISTS uf          CHAR(2)      NOT NULL DEFAULT '',
    ADD COLUMN IF NOT EXISTS cep         VARCHAR(8)   NOT NULL DEFAULT '';

-- Remover defaults temporários usados para satisfazer NOT NULL em dados existentes
-- (executar APÓS popular os valores reais, se houver registros):
-- ALTER TABLE endereco
--     ALTER COLUMN logradouro DROP DEFAULT,
--     ALTER COLUMN numero     DROP DEFAULT,
--     ALTER COLUMN bairro     DROP DEFAULT,
--     ALTER COLUMN cidade     DROP DEFAULT,
--     ALTER COLUMN uf         DROP DEFAULT,
--     ALTER COLUMN cep        DROP DEFAULT;

-- empresa_cnpj é optional=false no Java mas nullable no V1
ALTER TABLE endereco ALTER COLUMN empresa_cnpj SET NOT NULL;

-- -----------------------------------------------------------------------------
-- 1.2  cliente — colunas nome, email, telefone e senha ausentes
--      Classe: Cliente.java (implements UserDetails, todos os campos NOT NULL)
--      Problema: V2 criou a tabela apenas com cliente_id, cliente_cpf e endereco_id
-- -----------------------------------------------------------------------------
ALTER TABLE cliente
    ADD COLUMN IF NOT EXISTS nome     VARCHAR(150) NOT NULL DEFAULT '',
    ADD COLUMN IF NOT EXISTS email    VARCHAR(150) NOT NULL DEFAULT '',
    ADD COLUMN IF NOT EXISTS telefone VARCHAR(30)  NOT NULL DEFAULT '',
    ADD COLUMN IF NOT EXISTS senha    VARCHAR(255) NOT NULL DEFAULT '';

-- Unique constraint em email (declarada no Java como @Column(unique=true))
ALTER TABLE cliente
    ADD CONSTRAINT IF NOT EXISTS uq_cliente_email UNIQUE (email);

-- endereco_id é NOT NULL no Java (optional=false) mas nullable no V2
ALTER TABLE cliente ALTER COLUMN endereco_id SET NOT NULL;

-- Remover defaults temporários após popular dados:
-- ALTER TABLE cliente
--     ALTER COLUMN nome     DROP DEFAULT,
--     ALTER COLUMN email    DROP DEFAULT,
--     ALTER COLUMN telefone DROP DEFAULT,
--     ALTER COLUMN senha    DROP DEFAULT;

-- -----------------------------------------------------------------------------
-- 1.3  obra — descricao e data_inicio devem ser NOT NULL
--      Classe: Obra.java (@NotBlank/@NotNull + @Column(nullable=false))
-- -----------------------------------------------------------------------------
-- Garantir que não existam NULLs antes de adicionar a constraint
UPDATE obra SET descricao   = '' WHERE descricao   IS NULL;
UPDATE obra SET data_inicio = CURRENT_DATE WHERE data_inicio IS NULL;

ALTER TABLE obra
    ALTER COLUMN descricao   SET NOT NULL,
    ALTER COLUMN data_inicio SET NOT NULL;

-- -----------------------------------------------------------------------------
-- 1.4  log_alteracao — projeto_id, cliente_cpf, tipo_usuario e descricao
--      devem ser NOT NULL; descricao é TEXT→VARCHAR(255)
--      Classe: LogAlteracao.java (@NotNull em todos + @NotBlank em descricao)
-- -----------------------------------------------------------------------------
-- Garantir ausência de NULLs antes das constraints
UPDATE log_alteracao SET descricao = '' WHERE descricao IS NULL;

ALTER TABLE log_alteracao
    ALTER COLUMN projeto_id   SET NOT NULL,
    ALTER COLUMN cliente_cpf  SET NOT NULL,
    ALTER COLUMN tipo_usuario SET NOT NULL;

-- Ajustar tipo de descricao: TEXT → VARCHAR(255) conforme @Size(max=255)
ALTER TABLE log_alteracao
    ALTER COLUMN descricao TYPE VARCHAR(255) USING LEFT(descricao, 255);

ALTER TABLE log_alteracao
    ALTER COLUMN descricao SET NOT NULL;

-- =============================================================================
-- SEÇÃO 2 — GRAVES
-- =============================================================================

-- -----------------------------------------------------------------------------
-- 2.1  profissional_de_base.profissional_cpf — VARCHAR(14) → VARCHAR(11)
--      Classe: ProfissionalDeBase.java (@Column(length=11), CPF sem máscara)
--      Impacto: todas as FKs abaixo herdam a coluna referenciada
-- -----------------------------------------------------------------------------
-- ATENÇÃO: só executar se os dados já estiverem normalizados (sem pontos/traços).
-- Descomente este bloco SOMENTE após confirmar que todos os CPFs estão sem máscara:
--
-- ALTER TABLE historico_localizacao DROP CONSTRAINT IF EXISTS historico_localizacao_profissional_cpf_fkey;
-- ALTER TABLE profissional_de_base ALTER COLUMN profissional_cpf TYPE VARCHAR(11);
-- ALTER TABLE historico_localizacao ALTER COLUMN profissional_cpf TYPE VARCHAR(11);
-- ALTER TABLE historico_localizacao
--     ADD CONSTRAINT historico_localizacao_profissional_cpf_fkey
--     FOREIGN KEY (profissional_cpf) REFERENCES profissional_de_base(profissional_cpf);

-- -----------------------------------------------------------------------------
-- 2.2  cliente.cliente_cpf — VARCHAR(14) → VARCHAR(11)
--      Classe: Cliente.java (@Column(length=11))
--      Impacto: notificacao, lembrete, mensagem_instantanea, feedback,
--               log_alteracao, comunicacao, permissao_usuario
-- -----------------------------------------------------------------------------
-- Mesmo critério de 2.1 — descomente após normalização:
--
-- ALTER TABLE notificacao          DROP CONSTRAINT IF EXISTS notificacao_cliente_cpf_fkey;
-- ALTER TABLE lembrete             DROP CONSTRAINT IF EXISTS lembrete_cliente_cpf_fkey;
-- ALTER TABLE mensagem_instantanea DROP CONSTRAINT IF EXISTS mensagem_instantanea_cliente_cpf_fkey;
-- ALTER TABLE feedback             DROP CONSTRAINT IF EXISTS feedback_cliente_cpf_fkey;
-- ALTER TABLE log_alteracao        DROP CONSTRAINT IF EXISTS log_alteracao_cliente_cpf_fkey;
-- ALTER TABLE permissao_usuario    DROP CONSTRAINT IF EXISTS permissao_usuario_cliente_cpf_fkey;
-- ALTER TABLE cliente              ALTER COLUMN cliente_cpf TYPE VARCHAR(11);
-- ALTER TABLE notificacao          ALTER COLUMN cliente_cpf TYPE VARCHAR(11);
-- ALTER TABLE lembrete             ALTER COLUMN cliente_cpf TYPE VARCHAR(11);
-- ALTER TABLE mensagem_instantanea ALTER COLUMN cliente_cpf TYPE VARCHAR(11);
-- ALTER TABLE feedback             ALTER COLUMN cliente_cpf TYPE VARCHAR(11);
-- ALTER TABLE log_alteracao        ALTER COLUMN cliente_cpf TYPE VARCHAR(11);
-- ALTER TABLE permissao_usuario    ALTER COLUMN cliente_cpf TYPE VARCHAR(11);
-- -- Re-add FKs:
-- ALTER TABLE notificacao          ADD CONSTRAINT notificacao_cliente_cpf_fkey          FOREIGN KEY (cliente_cpf) REFERENCES cliente(cliente_cpf);
-- ALTER TABLE lembrete             ADD CONSTRAINT lembrete_cliente_cpf_fkey             FOREIGN KEY (cliente_cpf) REFERENCES cliente(cliente_cpf);
-- ALTER TABLE mensagem_instantanea ADD CONSTRAINT mensagem_instantanea_cliente_cpf_fkey FOREIGN KEY (cliente_cpf) REFERENCES cliente(cliente_cpf);
-- ALTER TABLE feedback             ADD CONSTRAINT feedback_cliente_cpf_fkey             FOREIGN KEY (cliente_cpf) REFERENCES cliente(cliente_cpf);
-- ALTER TABLE log_alteracao        ADD CONSTRAINT log_alteracao_cliente_cpf_fkey        FOREIGN KEY (cliente_cpf) REFERENCES cliente(cliente_cpf);
-- ALTER TABLE permissao_usuario    ADD CONSTRAINT permissao_usuario_cliente_cpf_fkey    FOREIGN KEY (cliente_cpf) REFERENCES cliente(cliente_cpf);

-- -----------------------------------------------------------------------------
-- 2.3  pagamento.forma_pagamento — VARCHAR(50) → VARCHAR(15)
--      Classe: Pagamento.java (@Column(name="forma_pagamento", length=15))
--      Verificar antes: SELECT MAX(LENGTH(forma_pagamento)) FROM pagamento;
-- -----------------------------------------------------------------------------
ALTER TABLE pagamento
    ALTER COLUMN forma_pagamento TYPE VARCHAR(15) USING LEFT(forma_pagamento, 15);

-- -----------------------------------------------------------------------------
-- 2.4  pagamento.data_pagamento — deve ser NOT NULL
--      Classe: Pagamento.java (@Column(name="data_pagamento", nullable=false))
-- -----------------------------------------------------------------------------
UPDATE pagamento SET data_pagamento = CURRENT_DATE WHERE data_pagamento IS NULL;
ALTER TABLE pagamento ALTER COLUMN data_pagamento SET NOT NULL;

-- -----------------------------------------------------------------------------
-- 2.5  mensagem_instantanea — colunas NOT NULL e tipos divergentes
--      Classe: MensagemInstantanea.java
--        destinatario_id   → TEXT NOT NULL        (SQL: VARCHAR(50)  nullable)
--        tipo_destinatario → VARCHAR(15) NOT NULL  (SQL: VARCHAR(30)  nullable)
--        conteudo          → TEXT NOT NULL         (SQL: TEXT         nullable)
--        tipo_usuario      → NOT NULL              (SQL: nullable)
-- -----------------------------------------------------------------------------
UPDATE mensagem_instantanea SET destinatario_id   = '' WHERE destinatario_id   IS NULL;
UPDATE mensagem_instantanea SET tipo_destinatario = '' WHERE tipo_destinatario IS NULL;
UPDATE mensagem_instantanea SET conteudo          = '' WHERE conteudo          IS NULL;

ALTER TABLE mensagem_instantanea
    ALTER COLUMN destinatario_id   TYPE TEXT,
    ALTER COLUMN destinatario_id   SET NOT NULL,
    ALTER COLUMN tipo_destinatario TYPE VARCHAR(15) USING LEFT(tipo_destinatario, 15),
    ALTER COLUMN tipo_destinatario SET NOT NULL,
    ALTER COLUMN conteudo          SET NOT NULL,
    ALTER COLUMN tipo_usuario      SET NOT NULL;

-- -----------------------------------------------------------------------------
-- 2.6  feedback — mensagem e tipo_usuario NOT NULL no Java mas nullable no SQL
--      Classe: Feedback.java
-- -----------------------------------------------------------------------------
UPDATE feedback SET mensagem = '' WHERE mensagem IS NULL;
ALTER TABLE feedback
    ALTER COLUMN mensagem     SET NOT NULL,
    ALTER COLUMN tipo_usuario SET NOT NULL;

-- -----------------------------------------------------------------------------
-- 2.7  notificacao.mensagem — TEXT → VARCHAR(255)
--      Classe: Notificacao.java (@Column(name="mensagem", length=255))
-- -----------------------------------------------------------------------------
ALTER TABLE notificacao
    ALTER COLUMN mensagem TYPE VARCHAR(255) USING LEFT(mensagem, 255);

-- =============================================================================
-- SEÇÃO 3 — MODERADOS
-- =============================================================================

-- -----------------------------------------------------------------------------
-- 3.1  projeto_categoria — recriação como tabela de junção M:N correta
--      Problema: V1 criou como clone de categoria (pk própria + nome + descricao).
--      O Java a usa como tabela de junção pura: projeto ↔ categoria.
-- -----------------------------------------------------------------------------
DROP TABLE IF EXISTS projeto_categoria;

CREATE TABLE projeto_categoria (
    projeto_id   INTEGER NOT NULL REFERENCES projeto(projeto_id)    ON DELETE CASCADE,
    categoria_id INTEGER NOT NULL REFERENCES categoria(categoria_id) ON DELETE CASCADE,
    PRIMARY KEY (projeto_id, categoria_id)
);
CREATE INDEX idx_projeto_categoria_projeto   ON projeto_categoria(projeto_id);
CREATE INDEX idx_projeto_categoria_categoria ON projeto_categoria(categoria_id);

-- -----------------------------------------------------------------------------
-- 3.2  tecnico_de_obras — tabela órfã (sem @Entity correspondente)
--      TecnicoDeObras.java não existe; a entidade de técnicos é ProfissionalDeBase.
--      Descomente para remover após validar ausência de dados/dependências:
-- -----------------------------------------------------------------------------
-- DROP TABLE IF EXISTS tecnico_de_obras;

-- -----------------------------------------------------------------------------
-- 3.3  status_equipamento / status_solicitacao — lookup tables sem @Entity
--      Manter como lookup tables; documentar que não são mapeadas por JPA.
-- -----------------------------------------------------------------------------
COMMENT ON TABLE status_equipamento IS
    'Lookup table. Não mapeada via JPA — uso direto por valor VARCHAR em equipamento.status.';

COMMENT ON TABLE status_solicitacao IS
    'Lookup table. Referenciada por solicitacao_mudanca.status_solicitacao_id via FK inteira.';

-- -----------------------------------------------------------------------------
-- 3.4  score_tfe — snapshot do score atual; sem @Entity direta
--      HistoricoScoreTFE.java → historico_score_tfe (ok).
--      score_tfe é acesso via @Query nativo ou projeção JDBC.
-- -----------------------------------------------------------------------------
COMMENT ON TABLE score_tfe IS
    'Snapshot do score TFE atual por profissional. '
    'Sem @Entity JPA direta — acesso via @Query nativo em ScoreTfeRepository.';

-- =============================================================================
-- SEÇÃO 4 — ÍNDICES FALTANTES
-- (declarados em @Table(indexes={...}) nas classes Java mas ausentes no schema)
-- =============================================================================

-- profissional_de_base: idx_profissional_nome, idx_profissional_especialidade
CREATE INDEX IF NOT EXISTS idx_profissional_nome          ON profissional_de_base(nome);
CREATE INDEX IF NOT EXISTS idx_profissional_especialidade ON profissional_de_base(especialidade);

-- mensagem_instantanea: idx_msginst_tipo_usuario
CREATE INDEX IF NOT EXISTS idx_msginst_tipo_usuario ON mensagem_instantanea(tipo_usuario);

-- obra: idx_obra_data_inicio
CREATE INDEX IF NOT EXISTS idx_obra_data_inicio ON obra(data_inicio);

-- cliente: idx_cliente_email (coluna email adicionada na seção 1.2)
CREATE INDEX IF NOT EXISTS idx_cliente_email ON cliente(email);
