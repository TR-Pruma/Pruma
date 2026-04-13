-- V6: Cria tabela de série temporal de scores TFE por trabalhador

CREATE TABLE IF NOT EXISTS historico_score_tfe (
    historico_score_id       BIGSERIAL    PRIMARY KEY,
    profissional_id          INT          NOT NULL,
    score_total              DECIMAL(5,4) NOT NULL,
    score_desempenho_tecnico DECIMAL(5,4),
    score_comportamental     DECIMAL(5,4),
    score_social             DECIMAL(5,4),
    versao_algoritmo         VARCHAR(20),
    calculado_em             TIMESTAMP    NOT NULL,
    created_at               TIMESTAMP    NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_score_tfe_profissional
        FOREIGN KEY (profissional_id) REFERENCES profissional_de_base(profissional_id)
            ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_score_tfe_profissional ON historico_score_tfe(profissional_id);
CREATE INDEX IF NOT EXISTS idx_score_tfe_calculado_em ON historico_score_tfe(calculado_em);
