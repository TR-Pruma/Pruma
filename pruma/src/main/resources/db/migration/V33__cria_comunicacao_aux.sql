-- V33: Cria tabela comunicacao_aux exigida pela entidade ComunicacaoAux.java
-- Relacao 1:1 derivada de comunicacao (PK compartilhada via @MapsId)

CREATE TABLE comunicacao_aux (
    comunicacao_id   INT          NOT NULL,
    tipo_mensagem    VARCHAR(30)  NOT NULL COMMENT 'Enum TipoComunicacao: EMAIL, SMS, PUSH, etc.',
    ativo            TINYINT(1)   NOT NULL DEFAULT 1,
    created_at       DATETIME(6)  NOT NULL,
    updated_at       DATETIME(6),
    version          BIGINT,
    PRIMARY KEY (comunicacao_id),
    CONSTRAINT fk_ca_comunicacao
        FOREIGN KEY (comunicacao_id) REFERENCES comunicacao (comunicacao_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE INDEX idx_comunicacao_aux_tipo ON comunicacao_aux (tipo_mensagem);
