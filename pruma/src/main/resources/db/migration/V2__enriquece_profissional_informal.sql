-- V2: Enriquece profissional_de_base com campos para MOR/TFE e grafo social

ALTER TABLE profissional_de_base
    ADD COLUMN IF NOT EXISTS telefone_whatsapp      VARCHAR(20),
    ADD COLUMN IF NOT EXISTS trabalhador_informal    BOOLEAN NOT NULL DEFAULT FALSE,
    ADD COLUMN IF NOT EXISTS score_tfe              DECIMAL(5,4),
    ADD COLUMN IF NOT EXISTS ultimo_login_app       TIMESTAMP,
    ADD COLUMN IF NOT EXISTS total_obras_concluidas INT     NOT NULL DEFAULT 0,
    ADD COLUMN IF NOT EXISTS padrinho_id            INT;

ALTER TABLE profissional_de_base
    ADD CONSTRAINT fk_profissional_padrinho
        FOREIGN KEY (padrinho_id) REFERENCES profissional_de_base(profissional_id)
            ON DELETE SET NULL;

CREATE INDEX IF NOT EXISTS idx_profissional_padrinho ON profissional_de_base(padrinho_id);
