CREATE TABLE apadrinhamento_rede (
                                     apadrinhamento_id BIGINT      AUTO_INCREMENT PRIMARY KEY,
                                     padrinho_id       INT         NOT NULL,
                                     afilhado_id       INT         NOT NULL,
                                     data_inicio       DATE        NOT NULL,
                                     data_fim          DATE,
                                     status            VARCHAR(20) NOT NULL DEFAULT 'ATIVO',
                                     created_at        TIMESTAMP   NOT NULL DEFAULT NOW(),
                                     updated_at        TIMESTAMP   NOT NULL DEFAULT NOW(),
                                     CONSTRAINT fk_apadrinhamento_padrinho
                                         FOREIGN KEY (padrinho_id) REFERENCES profissional_de_base(profissional_id)
                                             ON DELETE CASCADE,
                                     CONSTRAINT fk_apadrinhamento_afilhado
                                         FOREIGN KEY (afilhado_id) REFERENCES profissional_de_base(profissional_id)
                                             ON DELETE CASCADE,
                                     CONSTRAINT uq_apadrinhamento_ativo
                                         UNIQUE (padrinho_id, afilhado_id, status)
);

CREATE INDEX idx_apadrinhamento_padrinho ON apadrinhamento_rede(padrinho_id);
CREATE INDEX idx_apadrinhamento_afilhado ON apadrinhamento_rede(afilhado_id);
CREATE INDEX idx_apadrinhamento_status   ON apadrinhamento_rede(status);

CREATE TABLE interacao_campo_mor (
                                     interacao_id           BIGINT       AUTO_INCREMENT PRIMARY KEY,
                                     profissional_id        INT          NOT NULL,
                                     obra_id                INT,
                                     tipo_midia             VARCHAR(10)  NOT NULL,
                                     url_midia              VARCHAR(512),
                                     geolocalizacao         VARCHAR(50),
                                     timestamp_campo        TIMESTAMP    NOT NULL,
                                     exif_valido            BOOLEAN,
                                     hash_integridade       VARCHAR(64),
                                     status_processamento   VARCHAR(20)  NOT NULL DEFAULT 'PENDENTE',
                                     dados_extraidos        TEXT,
                                     created_at             TIMESTAMP    NOT NULL DEFAULT NOW(),
                                     updated_at             TIMESTAMP    NOT NULL DEFAULT NOW(),
                                     CONSTRAINT fk_mor_profissional
                                         FOREIGN KEY (profissional_id) REFERENCES profissional_de_base(profissional_id)
                                             ON DELETE CASCADE,
                                     CONSTRAINT fk_mor_obra
                                         FOREIGN KEY (obra_id) REFERENCES obra(obra_id)
                                             ON DELETE SET NULL
);

CREATE INDEX idx_mor_profissional ON interacao_campo_mor(profissional_id);
CREATE INDEX idx_mor_obra         ON interacao_campo_mor(obra_id);
CREATE INDEX idx_mor_status       ON interacao_campo_mor(status_processamento);
CREATE INDEX idx_mor_timestamp    ON interacao_campo_mor(timestamp_campo);