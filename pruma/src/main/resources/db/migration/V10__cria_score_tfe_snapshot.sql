-- V10: score_tfe — snapshot VIGENTE do score TFE por profissional
-- Diferente de historico_score_tfe (série temporal), esta tabela mantém
-- o score ATUAL de cada profissional. É a FK referenciada em
-- profissional_de_base.score_atual_tfe_id (adicionada no V17).
-- UNIQUE(profissional_id) garante exatamente 1 linha ativa por profissional.

CREATE TABLE score_tfe (
    score_tfe_id         BIGINT          NOT NULL AUTO_INCREMENT,
    profissional_id      INT             NOT NULL,
    score_total          DECIMAL(5,4)    NOT NULL COMMENT 'Score consolidado 0.0000–1.0000',
    score_tecnico        DECIMAL(5,4)    NOT NULL COMMENT 'Eixo técnico — peso 0.45',
    score_operacional    DECIMAL(5,4)    NOT NULL COMMENT 'Eixo comportamental — peso 0.30',
    score_social         DECIMAL(5,4)    NOT NULL COMMENT 'Eixo social/apadrinhamento — peso 0.25',
    versao_algoritmo     VARCHAR(20)     NOT NULL COMMENT 'Ex: TFE-v1.0',
    calculado_em         TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at           TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at           TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    ativo                TINYINT(1)      NOT NULL DEFAULT 1,
    version              BIGINT          NOT NULL DEFAULT 0,
    PRIMARY KEY (score_tfe_id),
    CONSTRAINT uq_score_tfe_profissional  UNIQUE (profissional_id),
    CONSTRAINT fk_score_tfe_snap_profissional FOREIGN KEY (profissional_id)
        REFERENCES profissional_de_base (profissional_id)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    INDEX idx_score_tfe_profissional (profissional_id),
    INDEX idx_score_tfe_score_total  (score_total)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
