-- =============================================================================
-- V3 - Projeto, Obra, Orçamento, Pagamento e toda cadeia operacional
-- =============================================================================

-- ─── projeto ──────────────────────────────────────────────────────────────────
CREATE TABLE projeto (
    projeto_id   INTEGER      GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nome         VARCHAR(150) NOT NULL,
    descricao    TEXT,
    data_criacao DATE         NOT NULL DEFAULT CURRENT_DATE,
    created_at   TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at   TIMESTAMP    NOT NULL DEFAULT NOW(),
    ativo        BOOLEAN      NOT NULL DEFAULT TRUE,
    version      BIGINT       NOT NULL DEFAULT 0
);
CREATE INDEX idx_projeto_nome ON projeto(nome);

-- ─── projeto_profissional ─────────────────────────────────────────────────────
CREATE TABLE projeto_profissional (
    id              INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    projeto_id      INTEGER NOT NULL REFERENCES projeto(projeto_id),
    profissional_id INTEGER NOT NULL REFERENCES profissional_de_base(profissional_id),
    UNIQUE (projeto_id, profissional_id)
);

-- ─── obra ─────────────────────────────────────────────────────────────────────
CREATE TABLE obra (
    obra_id      INTEGER   GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    projeto_id   INTEGER   NOT NULL REFERENCES projeto(projeto_id),
    descricao    TEXT,
    data_inicio  DATE,
    data_fim     DATE,
    created_at   TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at   TIMESTAMP NOT NULL DEFAULT NOW(),
    ativo        BOOLEAN   NOT NULL DEFAULT TRUE,
    version      BIGINT    NOT NULL DEFAULT 0
);
CREATE INDEX idx_obra_projeto ON obra(projeto_id);

-- FK diferida: trajetoria_laboral → obra e projeto (criada em V2)
ALTER TABLE trajetoria_laboral ADD COLUMN obra_id    INTEGER REFERENCES obra(obra_id);
ALTER TABLE trajetoria_laboral ADD COLUMN projeto_id INTEGER REFERENCES projeto(projeto_id);

-- ─── pre_obra ─────────────────────────────────────────────────────────────────
CREATE TABLE pre_obra (
    pre_obra_id INTEGER   GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    obra_id     INTEGER   NOT NULL REFERENCES obra(obra_id),
    descricao   TEXT,
    data_inicio DATE,
    created_at  TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMP NOT NULL DEFAULT NOW(),
    version     BIGINT    NOT NULL DEFAULT 0
);

-- ─── pos_obra ─────────────────────────────────────────────────────────────────
CREATE TABLE pos_obra (
    pos_obra_id    BIGINT    GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    obra_id        INTEGER   NOT NULL REFERENCES obra(obra_id),
    descricao      TEXT,
    data_conclusao DATE,
    created_at     TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at     TIMESTAMP NOT NULL DEFAULT NOW()
);

-- ─── relatorio ────────────────────────────────────────────────────────────────
CREATE TABLE relatorio (
    relatorio_id INTEGER   GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    obra_id      INTEGER   NOT NULL REFERENCES obra(obra_id),
    descricao    TEXT,
    data_criacao DATE      NOT NULL DEFAULT CURRENT_DATE,
    created_at   TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at   TIMESTAMP NOT NULL DEFAULT NOW(),
    ativo        BOOLEAN   NOT NULL DEFAULT TRUE,
    version      BIGINT    NOT NULL DEFAULT 0
);

-- ─── orcamento ────────────────────────────────────────────────────────────────
CREATE TABLE orcamento (
    orcamento_id INTEGER       GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    projeto_id   INTEGER       NOT NULL REFERENCES projeto(projeto_id),
    empresa_cnpj VARCHAR(18)   REFERENCES empresa(empresa_cnpj),
    valor        NUMERIC(15,2) NOT NULL,
    data_envio   DATE,
    status       VARCHAR(30)   NOT NULL DEFAULT 'PENDENTE',
    created_at   TIMESTAMP     NOT NULL DEFAULT NOW(),
    updated_at   TIMESTAMP     NOT NULL DEFAULT NOW(),
    ativo        BOOLEAN       NOT NULL DEFAULT TRUE,
    version      BIGINT        NOT NULL DEFAULT 0
);
CREATE INDEX idx_orcamento_projeto ON orcamento(projeto_id);
CREATE INDEX idx_orcamento_status  ON orcamento(status);

-- ─── item_orcamento ───────────────────────────────────────────────────────────
CREATE TABLE item_orcamento (
    item_id        INTEGER       GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    orcamento_id   INTEGER       NOT NULL REFERENCES orcamento(orcamento_id),
    descricao      VARCHAR(255)  NOT NULL,
    quantidade     INTEGER       NOT NULL DEFAULT 1,
    valor_unitario NUMERIC(15,2) NOT NULL,
    version        BIGINT        NOT NULL DEFAULT 0,
    created_at     TIMESTAMP     NOT NULL DEFAULT NOW(),
    updated_at     TIMESTAMP     NOT NULL DEFAULT NOW()
);

-- ─── pagamento ────────────────────────────────────────────────────────────────
CREATE TABLE pagamento (
    pagamento_id              INTEGER       GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    orcamento_id              INTEGER       NOT NULL REFERENCES orcamento(orcamento_id),
    valor                     NUMERIC(15,2) NOT NULL,
    data_pagamento            DATE,
    forma_pagamento           VARCHAR(50),
    origem_pagamento          VARCHAR(50),
    meio_liquidacao           VARCHAR(50),
    valor_bruto               NUMERIC(15,2),
    taxa_plataforma           NUMERIC(15,2),
    parcela_credito           NUMERIC(15,2),
    valor_liquido_trabalhador NUMERIC(15,2),
    status_liquidacao         VARCHAR(30)   NOT NULL DEFAULT 'PENDENTE',
    id_transacao_externa      VARCHAR(150),
    version                   BIGINT        NOT NULL DEFAULT 0,
    created_at                TIMESTAMP     NOT NULL DEFAULT NOW(),
    updated_at                TIMESTAMP     NOT NULL DEFAULT NOW()
);
CREATE INDEX idx_pagamento_orcamento ON pagamento(orcamento_id);
CREATE INDEX idx_pagamento_status    ON pagamento(status_liquidacao);

-- ─── fatiamento_pagamento ─────────────────────────────────────────────────────
CREATE TABLE fatiamento_pagamento (
    fatiamento_id                 BIGINT        GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    pagamento_id                  INTEGER       NOT NULL REFERENCES pagamento(pagamento_id),
    obra_id                       INTEGER       NOT NULL REFERENCES obra(obra_id),
    profissional_id               INTEGER       NOT NULL REFERENCES profissional_de_base(profissional_id),
    valor_bruto                   NUMERIC(15,2) NOT NULL,
    valor_plataforma              NUMERIC(15,2),
    valor_fornecedores            NUMERIC(15,2),
    valor_deducao_divida          NUMERIC(15,2),
    valor_liquido_trabalhador     NUMERIC(15,2),
    status_liquidacao             VARCHAR(30)   NOT NULL DEFAULT 'PENDENTE',
    liquidado_em                  TIMESTAMP,
    hash_transacao                VARCHAR(255),
    created_at                    TIMESTAMP     NOT NULL DEFAULT NOW(),
    updated_at                    TIMESTAMP     NOT NULL DEFAULT NOW()
);
CREATE INDEX idx_fatiamento_pagamento    ON fatiamento_pagamento(pagamento_id);
CREATE INDEX idx_fatiamento_obra         ON fatiamento_pagamento(obra_id);
CREATE INDEX idx_fatiamento_profissional ON fatiamento_pagamento(profissional_id);

-- ─── cronograma ───────────────────────────────────────────────────────────────
CREATE TABLE cronograma (
    cronograma_id INTEGER      GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    projeto_id    INTEGER      NOT NULL REFERENCES projeto(projeto_id),
    nome          VARCHAR(150) NOT NULL,
    data_inicio   DATE,
    data_fim      DATE,
    created_at    TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at    TIMESTAMP    NOT NULL DEFAULT NOW(),
    ativo         BOOLEAN      NOT NULL DEFAULT TRUE,
    version       BIGINT       NOT NULL DEFAULT 0
);

-- ─── fase_cronograma ──────────────────────────────────────────────────────────
CREATE TABLE fase_cronograma (
    fase_id       INTEGER      GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    cronograma_id INTEGER      NOT NULL REFERENCES cronograma(cronograma_id),
    nome          VARCHAR(150) NOT NULL,
    descricao     TEXT,
    data_inicio   DATE,
    data_fim      DATE,
    created_at    TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at    TIMESTAMP    NOT NULL DEFAULT NOW(),
    ativo         BOOLEAN      NOT NULL DEFAULT TRUE,
    version       BIGINT       NOT NULL DEFAULT 0
);

-- ─── atividade ────────────────────────────────────────────────────────────────
CREATE TABLE atividade (
    atividade_id INTEGER   GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    projeto_id   INTEGER   NOT NULL REFERENCES projeto(projeto_id),
    descricao    TEXT      NOT NULL,
    status       VARCHAR(15) NOT NULL DEFAULT 'PENDENTE',
    data_inicio  DATE      NOT NULL,
    data_fim     DATE,
    version      BIGINT    NOT NULL DEFAULT 0,
    created_at   TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at   TIMESTAMP NOT NULL DEFAULT NOW()
);
CREATE INDEX idx_atividade_projeto ON atividade(projeto_id);
CREATE INDEX idx_atividade_status  ON atividade(status);

-- ─── tarefa ───────────────────────────────────────────────────────────────────
CREATE TABLE tarefa (
    tarefa_id       INTEGER   GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    atividade_id    INTEGER   NOT NULL REFERENCES atividade(atividade_id),
    descricao       TEXT      NOT NULL,
    status          VARCHAR(30) NOT NULL DEFAULT 'PENDENTE',
    data_criacao    DATE      NOT NULL DEFAULT CURRENT_DATE,
    data_conclusao  DATE,
    created_at      TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMP NOT NULL DEFAULT NOW(),
    ativo           BOOLEAN   NOT NULL DEFAULT TRUE,
    version         BIGINT    NOT NULL DEFAULT 0
);

-- ─── material_utilizado ───────────────────────────────────────────────────────
CREATE TABLE material_utilizado (
    id                   INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    atividade_id         INTEGER NOT NULL REFERENCES atividade(atividade_id),
    material_id          INTEGER NOT NULL REFERENCES material(material_id),
    quantidade_utilizada INTEGER NOT NULL DEFAULT 0,
    version              BIGINT  NOT NULL DEFAULT 0,
    created_at           TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at           TIMESTAMP NOT NULL DEFAULT NOW()
);

-- ─── checklist ────────────────────────────────────────────────────────────────
CREATE TABLE checklist (
    checklist_id     INTEGER      GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    projeto_id       INTEGER      NOT NULL REFERENCES projeto(projeto_id),
    nome             VARCHAR(150) NOT NULL,
    ativo            BOOLEAN      NOT NULL DEFAULT TRUE,
    data_criacao     TIMESTAMP    NOT NULL DEFAULT NOW(),
    data_atualizacao TIMESTAMP    NOT NULL DEFAULT NOW(),
    version          BIGINT       NOT NULL DEFAULT 0
);

-- ─── item_checklist ───────────────────────────────────────────────────────────
CREATE TABLE item_checklist (
    item_id      INTEGER   GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    checklist_id INTEGER   NOT NULL REFERENCES checklist(checklist_id),
    descricao    TEXT      NOT NULL,
    ordem        INTEGER   NOT NULL DEFAULT 0,
    status       VARCHAR(30) NOT NULL DEFAULT 'PENDENTE',
    observacao   TEXT,
    ativo        BOOLEAN   NOT NULL DEFAULT TRUE,
    created_at   TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at   TIMESTAMP NOT NULL DEFAULT NOW(),
    version      BIGINT    NOT NULL DEFAULT 0
);

-- ─── documento ────────────────────────────────────────────────────────────────
CREATE TABLE documento (
    documento_id      BIGINT        GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    projeto_id        INTEGER       REFERENCES projeto(projeto_id),
    tipo_documento_id INTEGER       REFERENCES tipo_documento(tipo_documento_id),
    nome_arquivo      VARCHAR(255)  NOT NULL,
    caminho_arquivo   VARCHAR(1024) NOT NULL,
    tipo_arquivo      VARCHAR(100),
    tamanho_arquivo   BIGINT,
    data_upload       TIMESTAMP     NOT NULL DEFAULT NOW(),
    data_atualizacao  TIMESTAMP     NOT NULL DEFAULT NOW(),
    ativo             BOOLEAN       NOT NULL DEFAULT TRUE,
    versao            BIGINT        NOT NULL DEFAULT 0
);
CREATE INDEX idx_documento_projeto ON documento(projeto_id);

-- ─── assinatura_digital ───────────────────────────────────────────────────────
CREATE TABLE assinatura_digital (
    assinatura_digital_id INTEGER   GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    usuario_id            INTEGER   NOT NULL REFERENCES usuario(usuario_id),
    documento_id          BIGINT    NOT NULL REFERENCES documento(documento_id),
    hash                  VARCHAR(255),
    data_assinatura       TIMESTAMP NOT NULL,
    created_at            TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at            TIMESTAMP NOT NULL DEFAULT NOW(),
    ativo                 BOOLEAN   NOT NULL DEFAULT TRUE,
    version               BIGINT    NOT NULL DEFAULT 0
);

-- ─── anexo ────────────────────────────────────────────────────────────────────
CREATE TABLE anexo (
    anexo_id   INTEGER       GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    projeto_id INTEGER       NOT NULL REFERENCES projeto(projeto_id),
    tipo_anexo VARCHAR(15)   NOT NULL,
    caminho    VARCHAR(1024) NOT NULL,
    nome       VARCHAR(255),
    tipo_mime  VARCHAR(100),
    tamanho    BIGINT,
    created_at TIMESTAMP     NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP     NOT NULL DEFAULT NOW(),
    ativo      BOOLEAN       NOT NULL DEFAULT TRUE,
    version    BIGINT        NOT NULL DEFAULT 0
);
CREATE INDEX idx_anexo_projeto ON anexo(projeto_id);
CREATE INDEX idx_anexo_tipo    ON anexo(tipo_anexo);

-- ─── imagem_projeto ───────────────────────────────────────────────────────────
CREATE TABLE imagem_projeto (
    imagem_id        INTEGER       GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    projeto_id       INTEGER       NOT NULL REFERENCES projeto(projeto_id),
    caminho_arquivo  VARCHAR(1024) NOT NULL,
    descricao        TEXT,
    data_upload      DATE          NOT NULL DEFAULT CURRENT_DATE,
    exif_latitude    DOUBLE PRECISION,
    exif_longitude   DOUBLE PRECISION,
    exif_timestamp   TIMESTAMP,
    exif_manipulada  BOOLEAN       NOT NULL DEFAULT FALSE,
    hash_sha256      VARCHAR(64),
    created_at       TIMESTAMP     NOT NULL DEFAULT NOW(),
    updated_at       TIMESTAMP     NOT NULL DEFAULT NOW(),
    ativo            BOOLEAN       NOT NULL DEFAULT TRUE,
    version          BIGINT        NOT NULL DEFAULT 0
);
CREATE INDEX idx_imagem_projeto ON imagem_projeto(projeto_id);

-- ─── inspecao ─────────────────────────────────────────────────────────────────
CREATE TABLE inspecao (
    inspecao_id   INTEGER   GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    projeto_id    INTEGER   NOT NULL REFERENCES projeto(projeto_id),
    tecnico_id    INTEGER   REFERENCES profissional_de_base(profissional_id),
    descricao     TEXT,
    data_inspecao DATE,
    resultado     TEXT,
    created_at    TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at    TIMESTAMP NOT NULL DEFAULT NOW(),
    ativo         BOOLEAN   NOT NULL DEFAULT TRUE,
    version       BIGINT    NOT NULL DEFAULT 0
);

-- ─── historico_localizacao ────────────────────────────────────────────────────
CREATE TABLE historico_localizacao (
    historico_id     INTEGER     GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    profissional_cpf VARCHAR(14) NOT NULL REFERENCES profissional_de_base(profissional_cpf),
    projeto_id       INTEGER     REFERENCES projeto(projeto_id),
    localizacao      TEXT,
    data_hora        TIMESTAMP   NOT NULL DEFAULT NOW(),
    created_at       TIMESTAMP   NOT NULL DEFAULT NOW(),
    updated_at       TIMESTAMP   NOT NULL DEFAULT NOW(),
    ativo            BOOLEAN     NOT NULL DEFAULT TRUE,
    version          BIGINT      NOT NULL DEFAULT 0
);

-- ─── equipamento_projeto ──────────────────────────────────────────────────────
CREATE TABLE equipamento_projeto (
    equipamento_id   INTEGER   NOT NULL REFERENCES equipamento(equipamento_id),
    projeto_id       INTEGER   NOT NULL REFERENCES projeto(projeto_id),
    data_alocacao    DATE,
    data_criacao     TIMESTAMP NOT NULL DEFAULT NOW(),
    data_atualizacao TIMESTAMP NOT NULL DEFAULT NOW(),
    versao           BIGINT    NOT NULL DEFAULT 0,
    PRIMARY KEY (equipamento_id, projeto_id)
);

-- ─── equipamento_projeto_aux ──────────────────────────────────────────────────
CREATE TABLE equipamento_projeto_aux (
    equipamento_projeto_id BIGINT    GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    equipamento_id         INTEGER   NOT NULL REFERENCES equipamento(equipamento_id),
    projeto_id             INTEGER   NOT NULL REFERENCES projeto(projeto_id),
    data_alocacao          DATE,
    created_at             TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at             TIMESTAMP NOT NULL DEFAULT NOW(),
    ativo                  BOOLEAN   NOT NULL DEFAULT TRUE,
    version                BIGINT    NOT NULL DEFAULT 0
);

-- ─── insumo_fornecedor ────────────────────────────────────────────────────────
CREATE TABLE insumo_fornecedor (
    insumo_id     INTEGER NOT NULL REFERENCES insumo(insumo_id),
    fornecedor_id INTEGER NOT NULL REFERENCES fornecedor(fornecedor_id),
    PRIMARY KEY (insumo_id, fornecedor_id)
);

-- ─── insumo_fornecedor_aux ────────────────────────────────────────────────────
CREATE TABLE insumo_fornecedor_aux (
    insumo_id     INTEGER       NOT NULL REFERENCES insumo(insumo_id),
    fornecedor_id INTEGER       NOT NULL REFERENCES fornecedor(fornecedor_id),
    preco         NUMERIC(15,2),
    version       BIGINT        NOT NULL DEFAULT 0,
    created_at    TIMESTAMP     NOT NULL DEFAULT NOW(),
    updated_at    TIMESTAMP     NOT NULL DEFAULT NOW(),
    PRIMARY KEY (insumo_id, fornecedor_id)
);

-- ─── requisicao_material ──────────────────────────────────────────────────────
CREATE TABLE requisicao_material (
    requisicao_id   INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    obra_id         INTEGER NOT NULL REFERENCES obra(obra_id),
    material_id     INTEGER NOT NULL REFERENCES material(material_id),
    quantidade      INTEGER NOT NULL DEFAULT 1,
    data_requisicao DATE    NOT NULL DEFAULT CURRENT_DATE,
    created_at      DATE    NOT NULL DEFAULT CURRENT_DATE
);

-- ─── pedido_marketplace ───────────────────────────────────────────────────────
CREATE TABLE pedido_marketplace (
    pedido_marketplace_id BIGINT        GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    profissional_id       INTEGER       NOT NULL REFERENCES profissional_de_base(profissional_id),
    obra_id               INTEGER       NOT NULL REFERENCES obra(obra_id),
    fornecedor_id         INTEGER       NOT NULL REFERENCES fornecedor(fornecedor_id),
    valor_bruto           NUMERIC(15,2),
    taxa_plataforma       NUMERIC(15,2),
    status                VARCHAR(30)   NOT NULL DEFAULT 'PENDENTE',
    data_entrega          DATE,
    created_at            TIMESTAMP     NOT NULL DEFAULT NOW(),
    updated_at            TIMESTAMP     NOT NULL DEFAULT NOW()
);
CREATE INDEX idx_pedido_marketplace_profissional ON pedido_marketplace(profissional_id);
CREATE INDEX idx_pedido_marketplace_obra         ON pedido_marketplace(obra_id);

-- ─── item_pedido_marketplace ──────────────────────────────────────────────────
CREATE TABLE item_pedido_marketplace (
    item_pedido_marketplace_id BIGINT        GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    pedido_marketplace_id      BIGINT        NOT NULL REFERENCES pedido_marketplace(pedido_marketplace_id),
    insumo_id                  INTEGER       NOT NULL REFERENCES insumo(insumo_id),
    quantidade                 INTEGER       NOT NULL DEFAULT 1,
    preco_unitario             NUMERIC(15,2) NOT NULL
);

-- ─── interacao_campo_mor ──────────────────────────────────────────────────────
CREATE TABLE interacao_campo_mor (
    interacao_id          BIGINT    GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    profissional_id       INTEGER   NOT NULL REFERENCES profissional_de_base(profissional_id),
    obra_id               INTEGER   NOT NULL REFERENCES obra(obra_id),
    tipo_midia            VARCHAR(30),
    url_midia             VARCHAR(1024),
    geolocalizacao        VARCHAR(100),
    timestamp_campo       TIMESTAMP,
    exif_valido           BOOLEAN   NOT NULL DEFAULT FALSE,
    hash_integridade      VARCHAR(255),
    status_processamento  VARCHAR(30),
    dados_extraidos       TEXT,
    created_at            TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at            TIMESTAMP NOT NULL DEFAULT NOW()
);
CREATE INDEX idx_interacao_campo_profissional ON interacao_campo_mor(profissional_id);
CREATE INDEX idx_interacao_campo_obra         ON interacao_campo_mor(obra_id);

-- ─── subcontrato ──────────────────────────────────────────────────────────────
CREATE TABLE subcontrato (
    subcontrato_id INTEGER      GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    cliente_cpf    VARCHAR(14)  NOT NULL REFERENCES cliente(cliente_cpf),
    projeto_id     INTEGER      NOT NULL REFERENCES projeto(projeto_id),
    descricao      TEXT,
    valor          NUMERIC(15,2),
    data_inicio    DATE,
    data_fim       DATE,
    created_at     TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at     TIMESTAMP    NOT NULL DEFAULT NOW(),
    ativo          BOOLEAN      NOT NULL DEFAULT TRUE,
    version        BIGINT       NOT NULL DEFAULT 0
);

-- ─── solicitacao_mudanca ──────────────────────────────────────────────────────
CREATE TABLE solicitacao_mudanca (
    solicitacao_id        INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    projeto_id            INTEGER NOT NULL REFERENCES projeto(projeto_id),
    status_solicitacao_id INTEGER REFERENCES status_solicitacao(status_solicitacao_id),
    descricao             TEXT,
    data_solicitacao      DATE    NOT NULL DEFAULT CURRENT_DATE,
    data_resposta         DATE,
    created_at            TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at            TIMESTAMP NOT NULL DEFAULT NOW(),
    ativo                 BOOLEAN   NOT NULL DEFAULT TRUE,
    version               BIGINT    NOT NULL DEFAULT 0
);

-- ─── termo_garantia ───────────────────────────────────────────────────────────
CREATE TABLE termo_garantia (
    termo_id       INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    projeto_id     INTEGER NOT NULL REFERENCES projeto(projeto_id),
    descricao      TEXT,
    data_emissao   DATE    NOT NULL DEFAULT CURRENT_DATE,
    validade_meses INTEGER NOT NULL DEFAULT 12,
    created_at     TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at     TIMESTAMP NOT NULL DEFAULT NOW(),
    version        BIGINT    NOT NULL DEFAULT 0
);

-- ─── auditoria ────────────────────────────────────────────────────────────────
CREATE TABLE auditoria (
    auditoria_id INTEGER   GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    usuario_id   INTEGER   NOT NULL REFERENCES usuario(usuario_id),
    acao         VARCHAR(80) NOT NULL,
    entidade     VARCHAR(80),
    entidade_id  INTEGER,
    detalhe      TEXT,
    created_at   TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at   TIMESTAMP NOT NULL DEFAULT NOW(),
    ativo        BOOLEAN   NOT NULL DEFAULT TRUE,
    version      BIGINT    NOT NULL DEFAULT 0
);
CREATE INDEX idx_auditoria_usuario  ON auditoria(usuario_id);
CREATE INDEX idx_auditoria_entidade ON auditoria(entidade, entidade_id);

-- ─── avaliacao ────────────────────────────────────────────────────────────────
CREATE TABLE avaliacao (
    avaliacao_id INTEGER   GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    projeto_id   INTEGER   NOT NULL REFERENCES projeto(projeto_id),
    avaliador_id INTEGER   NOT NULL REFERENCES usuario(usuario_id),
    avaliado_id  INTEGER   NOT NULL REFERENCES usuario(usuario_id),
    nota         INTEGER   NOT NULL CHECK (nota BETWEEN 0 AND 10),
    comentario   TEXT,
    created_at   TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at   TIMESTAMP NOT NULL DEFAULT NOW(),
    ativo        BOOLEAN   NOT NULL DEFAULT TRUE,
    version      BIGINT    NOT NULL DEFAULT 0
);
CREATE INDEX idx_avaliacao_projeto  ON avaliacao(projeto_id);
CREATE INDEX idx_avaliacao_avaliado ON avaliacao(avaliado_id);

-- ─── feedback ─────────────────────────────────────────────────────────────────
CREATE TABLE feedback (
    feedback_id  INTEGER     GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    projeto_id   INTEGER     NOT NULL REFERENCES projeto(projeto_id),
    cliente_cpf  VARCHAR(14) NOT NULL REFERENCES cliente(cliente_cpf),
    tipo_usuario INTEGER     REFERENCES tipo_usuario(tipo_usuario),
    mensagem     TEXT,
    data_hora    TIMESTAMP   NOT NULL DEFAULT NOW(),
    created_at   TIMESTAMP   NOT NULL DEFAULT NOW(),
    updated_at   TIMESTAMP   NOT NULL DEFAULT NOW(),
    ativo        BOOLEAN     NOT NULL DEFAULT TRUE,
    version      BIGINT      NOT NULL DEFAULT 0
);

-- ─── notificacao ──────────────────────────────────────────────────────────────
CREATE TABLE notificacao (
    notificacao_id INTEGER     GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    cliente_cpf    VARCHAR(14) NOT NULL REFERENCES cliente(cliente_cpf),
    tipo_usuario   INTEGER     REFERENCES tipo_usuario(tipo_usuario),
    mensagem       TEXT        NOT NULL,
    data_hora      TIMESTAMP   NOT NULL DEFAULT NOW(),
    lida           BOOLEAN     NOT NULL DEFAULT FALSE,
    created_at     TIMESTAMP   NOT NULL DEFAULT NOW(),
    updated_at     TIMESTAMP   NOT NULL DEFAULT NOW(),
    ativo          BOOLEAN     NOT NULL DEFAULT TRUE,
    version        BIGINT      NOT NULL DEFAULT 0
);
CREATE INDEX idx_notificacao_cliente ON notificacao(cliente_cpf);
CREATE INDEX idx_notificacao_tipo    ON notificacao(tipo_usuario);
CREATE INDEX idx_notificacao_lida    ON notificacao(lida);

-- ─── lembrete ─────────────────────────────────────────────────────────────────
CREATE TABLE lembrete (
    lembrete_id  INTEGER     GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    cliente_cpf  VARCHAR(14) NOT NULL REFERENCES cliente(cliente_cpf),
    tipo_usuario INTEGER     REFERENCES tipo_usuario(tipo_usuario),
    mensagem     TEXT        NOT NULL,
    data_hora    TIMESTAMP   NOT NULL,
    version      BIGINT      NOT NULL DEFAULT 0,
    created_at   TIMESTAMP   NOT NULL DEFAULT NOW(),
    updated_at   TIMESTAMP   NOT NULL DEFAULT NOW()
);

-- ─── comunicacao ──────────────────────────────────────────────────────────────
CREATE TABLE comunicacao (
    comunicacao_id INTEGER   GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    projeto_id     INTEGER   NOT NULL REFERENCES projeto(projeto_id),
    cliente_id     INTEGER   NOT NULL REFERENCES cliente(cliente_id),
    tipo_remetente VARCHAR(30),
    mensagem       TEXT,
    created_at     TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at     TIMESTAMP NOT NULL DEFAULT NOW(),
    ativo          BOOLEAN   NOT NULL DEFAULT TRUE,
    version        BIGINT    NOT NULL DEFAULT 0
);

-- ─── comunicacao_aux ──────────────────────────────────────────────────────────
CREATE TABLE comunicacao_aux (
    comunicacao_id INTEGER   NOT NULL REFERENCES comunicacao(comunicacao_id),
    tipo_mensagem  VARCHAR(50),
    created_at     TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at     TIMESTAMP NOT NULL DEFAULT NOW(),
    ativo          BOOLEAN   NOT NULL DEFAULT TRUE,
    version        BIGINT    NOT NULL DEFAULT 0,
    PRIMARY KEY (comunicacao_id)
);

-- ─── mensagem_instantanea ─────────────────────────────────────────────────────
CREATE TABLE mensagem_instantanea (
    mensagem_id       INTEGER     GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    cliente_cpf       VARCHAR(14) NOT NULL REFERENCES cliente(cliente_cpf),
    tipo_usuario      INTEGER     REFERENCES tipo_usuario(tipo_usuario),
    destinatario_id   VARCHAR(50),
    tipo_destinatario VARCHAR(30),
    conteudo          TEXT,
    data_hora         TIMESTAMP   NOT NULL DEFAULT NOW(),
    version           BIGINT      NOT NULL DEFAULT 0,
    created_at        TIMESTAMP   NOT NULL DEFAULT NOW(),
    updated_at        TIMESTAMP   NOT NULL DEFAULT NOW()
);

-- ─── mensagem_instantanea_aux ─────────────────────────────────────────────────
CREATE TABLE mensagem_instantanea_aux (
    mensagem_id   INTEGER   NOT NULL REFERENCES mensagem_instantanea(mensagem_id),
    tipo_mensagem VARCHAR(50),
    created_at    TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at    TIMESTAMP NOT NULL DEFAULT NOW(),
    ativo         BOOLEAN   NOT NULL DEFAULT TRUE,
    version       BIGINT    NOT NULL DEFAULT 0,
    PRIMARY KEY (mensagem_id)
);

-- ─── log_alteracao ────────────────────────────────────────────────────────────
CREATE TABLE log_alteracao (
    log_id       INTEGER     GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    projeto_id   INTEGER     REFERENCES projeto(projeto_id),
    cliente_cpf  VARCHAR(14) REFERENCES cliente(cliente_cpf),
    tipo_usuario INTEGER     REFERENCES tipo_usuario(tipo_usuario),
    descricao    TEXT,
    data_hora    TIMESTAMP   NOT NULL DEFAULT NOW()
);
CREATE INDEX idx_log_alteracao_projeto ON log_alteracao(projeto_id);

-- ─── log_alteracao_aux ────────────────────────────────────────────────────────
CREATE TABLE log_alteracao_aux (
    log_id         INTEGER   NOT NULL REFERENCES log_alteracao(log_id),
    tipo_alteracao VARCHAR(50),
    created_at     TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at     TIMESTAMP NOT NULL DEFAULT NOW(),
    ativo          BOOLEAN   NOT NULL DEFAULT TRUE,
    version        BIGINT    NOT NULL DEFAULT 0,
    PRIMARY KEY (log_id)
);
